package com.lnet.frame.security;

import java.security.AccessControlException;
import java.util.Collection;
import java.util.List;


public interface AuthorizationService {

    boolean isPermitted(String permission);
    boolean isPermittedAny(Collection<String> permissions);
    boolean isPermittedAll(String... permissions);
    void checkPermission(String permission) throws AccessControlException;
    void checkPermissions(String... permissions) throws AccessControlException;

    boolean hasRole(String roleIdentifier);
    boolean[] hasRoles(List<String> roleIdentifiers);
    boolean hasAllRoles(Collection<String> roleNames);

    void checkRole(String roleIdentifier) throws AccessControlException;
    void checkRoles(Collection<String> roleIdentifiers) throws AccessControlException;
    void checkRoles(String... roleIdentifiers) throws AccessControlException;
    boolean isAuthenticated();

    void login(Object token) throws SecurityException;
    UserPrincipal getPrincipal();
}
