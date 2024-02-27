package com.fucn.constant;

public class SecurityConstant {
    public static final Long EXPIRATION_TIME = 432_000_000L; // 5 days expressed in milliseconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token Cannot be verified";
    public static final String FUCN_LLC = "FUCN, LLC";
    public static final String FUCN_ADMINISTRATION = "User Management Portal";
    public static final String AUTHORITIES = "Authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this resource";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String[] PUBLIC_URLS = {"/api/v1/auth/login", "/api/v1/auth/register", "/api/v1/auth/reset-password/**", "/api/v1/auth/image/**"};


}
