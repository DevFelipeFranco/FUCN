package com.fucn.provider;

import com.fucn.domain.UserPrincipal;
import com.fucn.exception.ApiException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

import static com.fucn.constant.SecurityConstant.*;
import static com.fucn.constant.SecurityConstant.FUCN_ADMINISTRATION;
import static java.util.Arrays.stream;

@Component
@Slf4j
public class JWTTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    public String generateJwtToken(UserPrincipal userPrincipal) {
        Claims claimsFromUser = getClaimsFromUser(userPrincipal);
        return Jwts.builder()
                .claims(claimsFromUser)
                .subject(userPrincipal.getUsername())
                .signWith(getSigningKey())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .issuer(FUCN_LLC)
                .issuedAt(new Date(System.currentTimeMillis()))
                .audience().add(FUCN_ADMINISTRATION)
                .and()
                .compact();

    }

    private Claims getClaimsFromUser(UserPrincipal userPrincipal) {
        ClaimsBuilder subject = Jwts.claims().subject(userPrincipal.getUsername());
        HashMap<String, List<String>> collect1 = userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.groupingBy(authority -> AUTHORITIES, HashMap::new, Collectors.toList()));
        subject.add(collect1);
        return subject.build();
    }

    private Key getSigningKey() {
        byte[] keyBytes = this.secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims parseJwtClaims(String token) {
        JwtParser build = Jwts.parser().verifyWith((SecretKey) getSigningKey()).build();
        return build.parseSignedClaims(token).getPayload();
    }

    public String getSubject(String token) {
        return getJWTVerifier().parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean isTokenValid(String username, String token) {
        JwtParser verifier = getJWTVerifier();
        return StringUtils.isNoneEmpty(username) && !isTokenExpired(verifier, token);
    }

    private boolean isTokenExpired(JwtParser verifier, String token) {
        Date expiration = verifier.parseSignedClaims(token).getPayload().getExpiration();
        return expiration.before(new Date());
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        List<String> claimsFromToken = getClaimsFromToken(token);
        return AuthorityUtils.createAuthorityList(claimsFromToken);
    }

    private List getClaimsFromToken(String token) {
        JwtParser build = getJWTVerifier();
        return build.parseSignedClaims(token).getPayload().get(AUTHORITIES, List.class);
    }

    public Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return usernamePasswordAuthenticationToken;
    }

    private JwtParser getJWTVerifier() {
        try {
            return Jwts.parser().verifyWith((SecretKey) getSigningKey()).requireIssuer(FUCN_LLC).build();
        } catch (InvalidClaimException ex) {
            log.error("Siii primer error por aca");
            throw new ApiException(ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("Siii segundo error por aca");
            throw new ApiException(ex.getMessage());
        } catch (UnsupportedJwtException | SignatureException ex) {
            log.error("Siii tercero error por aca");
            throw new ApiException(ex.getMessage());
        } catch (AuthenticationException ex) {
            log.error("Siii tercero error por aca");
            throw new ApiException(ex.getMessage());
        }
    }

    /* REVISAR DESPUES
    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }  */
}
