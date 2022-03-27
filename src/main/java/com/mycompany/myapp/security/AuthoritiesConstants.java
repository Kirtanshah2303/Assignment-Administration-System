package com.mycompany.myapp.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String STUDENT = "ROLE_STUDENT";
    public static final String FACULTY = "ROLE_FACULTY";
    public static final String REVIEWER = "ROLE_REVIEWER";

    //    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {}
}
