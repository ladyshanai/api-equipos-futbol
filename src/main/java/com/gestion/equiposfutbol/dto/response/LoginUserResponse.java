package com.gestion.equiposfutbol.dto.response;

public class LoginUserResponse {
    private LoggedUser username;
    private String token;

    public LoggedUser getUsername() {
        return username;
    }

    public void setUsername(LoggedUser username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}


