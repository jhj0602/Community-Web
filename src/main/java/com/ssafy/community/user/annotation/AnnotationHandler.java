package com.ssafy.community.user.annotation;


import com.ssafy.community.user.exception.FailToAuthenticationException;

public class AnnotationHandler {
    public static boolean hasAnyRole(String[] authorities, PreAuthorize preAuthorize) {
        for (String authority : authorities) {
            if(isAuthorityIncludedInRoles(authority, preAuthorize.roles())){
                return true;
            }
        }
        throw new FailToAuthenticationException();
    }

    private static boolean isAuthorityIncludedInRoles(String authority, String[] roles){
        for (String role : roles) {
            if (authority.equals(role)) {
                return true;
            }
        }
        return false;
    }
}