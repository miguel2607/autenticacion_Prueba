package org.arle.proyectorolesauth.controller;

import lombok.Data;

@Data
class LoginRequest {
    private String username;
    private String password;
}
