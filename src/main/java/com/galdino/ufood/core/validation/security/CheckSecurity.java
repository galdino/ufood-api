package com.galdino.ufood.core.validation.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public @interface CheckSecurity {

    public @interface Kitchen {

        @PreAuthorize("isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface CanCheck {}

        @PreAuthorize("hasAuthority('EDIT_KITCHEN')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface CanEdit {}

    }

}
