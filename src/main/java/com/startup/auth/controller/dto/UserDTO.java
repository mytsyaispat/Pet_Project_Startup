package com.startup.auth.controller.dto;

public final class UserDTO {
    public String username;
    public String password;

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserDTO(){
    }

}
