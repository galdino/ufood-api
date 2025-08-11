package com.galdino.ufood.core.validation.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public @interface CheckSecurity {

    public @interface Kitchen {

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface CanCheck {}

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_KITCHEN')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface CanEdit {}

    }

    public @interface Restaurant {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_RESTAURANT')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface CanManageRegister {}

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('EDIT_RESTAURANT') or @ufoodSecurity.manageRestaurants(#id))")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface CanManageOpenClose {}

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface CanCheck {}

    }

}
