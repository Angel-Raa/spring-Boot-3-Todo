package com.caja.ideal.request;

import com.caja.ideal.user.Role;

public record RegistrationRequest(String username, String email, String password, Role role) {
}
