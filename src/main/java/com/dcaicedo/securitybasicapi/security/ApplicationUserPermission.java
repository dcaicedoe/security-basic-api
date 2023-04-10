package com.dcaicedo.securitybasicapi.security;

public enum ApplicationUserPermission {
    USER_GET("user:get"),
    USER_INSERT("user:insert"),
    ADMIN_GET("admin:get"),
    ADMIN_INSERT("admin:insert");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
