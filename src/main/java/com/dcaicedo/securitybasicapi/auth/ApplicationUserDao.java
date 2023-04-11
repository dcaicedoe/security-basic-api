package com.dcaicedo.securitybasicapi.auth;

import java.util.Optional;

public interface ApplicationUserDao {

    Optional<ApplicationUser> findByUsername(String username);
}
