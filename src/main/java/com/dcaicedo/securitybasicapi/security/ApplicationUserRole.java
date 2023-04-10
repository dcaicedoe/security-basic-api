package com.dcaicedo.securitybasicapi.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * Contiene los roles de usuario que seran usados para permitir o restringir el acceso a los recursos.
 * </p>
 *
 * @author Daniel Caicedo
 * @version 1.0, 07/04/2023
 */
public enum ApplicationUserRole {

    USER(Arrays.asList(ApplicationUserPermission.USER_GET, ApplicationUserPermission.USER_INSERT)),
    ADMIN(Arrays.asList(ApplicationUserPermission.ADMIN_GET));

    private final List<ApplicationUserPermission> permissions;

    ApplicationUserRole(List<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public List<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public List<SimpleGrantedAuthority> getAuthorities(){
        List<SimpleGrantedAuthority> permissions =
                getPermissions()
                        .stream()
                        .map(m -> new SimpleGrantedAuthority(m.getPermission())).collect(Collectors.toList());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
