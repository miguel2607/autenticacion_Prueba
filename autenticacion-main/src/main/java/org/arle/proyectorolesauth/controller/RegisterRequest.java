package org.arle.proyectorolesauth.controller;

import lombok.Data;

@Data
class RegisterRequest {
    private String username;
    private String password;
    private String role;
}
