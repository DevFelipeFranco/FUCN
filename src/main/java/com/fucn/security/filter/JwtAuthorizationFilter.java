package com.fucn.security.filter;

import com.fucn.constant.SecurityConstant;
import com.fucn.exception.ApiException;
import com.fucn.provider.JWTTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.fucn.constant.SecurityConstant.TOKEN_PREFIX;
import static com.fucn.utils.ExceptionUtils.processError;
import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JWTTokenProvider jwtTokenProvider;
    protected static final String USERNAME_KEY = "username";
    protected static final String TOKEN_KEY = "token";
//    private HandlerExceptionResolver exceptionResolver;

    @Autowired
    public JwtAuthorizationFilter(JWTTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            Map<String, String> values = getRequestValues(request);
            if (jwtTokenProvider.isTokenValid(values.get(USERNAME_KEY), values.get(TOKEN_KEY))) {
                List<GrantedAuthority> authorities = jwtTokenProvider.getAuthorities(values.get(TOKEN_KEY));
                Authentication authentication = jwtTokenProvider.getAuthentication(values.get(USERNAME_KEY), authorities, request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            processError(request, response, exception);
        }
    }

    private Map<String, String> getRequestValues(HttpServletRequest request) {
        return Map.of(USERNAME_KEY, jwtTokenProvider.getSubject(getToken(request)), TOKEN_KEY, getToken(request));
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getHeader(AUTHORIZATION) == null || !request.getHeader(AUTHORIZATION).startsWith(TOKEN_PREFIX)
                || request.getMethod().equalsIgnoreCase(SecurityConstant.OPTIONS_HTTP_METHOD) || asList(SecurityConstant.PUBLIC_URLS).contains(request.getRequestURI());
    }

    private String getToken(HttpServletRequest request) {
        return ofNullable(request.getHeader(AUTHORIZATION))
                .filter(header -> header.startsWith(TOKEN_PREFIX))
                .map(token -> token.replace(TOKEN_PREFIX, EMPTY)).orElseThrow(() -> new RuntimeException("Not exist token"));
    }
}
