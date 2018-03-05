package com.lnet.frame.security;

import java.security.Principal;
import java.util.Map;

public interface UserPrincipal extends Principal {

    String getUserId();
    String getDisplayName();
    String getEmail();
    String getMobile();
    String getCurrentBranchCode();
    String getCurrentSiteCode();

    boolean isActive();

    Map<String, String> getBindings();

    default String getBinding(String key){
        return getBindings().get(key);
    }
}
