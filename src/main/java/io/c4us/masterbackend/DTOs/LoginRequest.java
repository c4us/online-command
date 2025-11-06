package io.c4us.masterbackend.DTOs;

public class LoginRequest {

    private String identifier; // peut être un email ou un téléphone
    private String password;

    // Constructeurs
    public LoginRequest() {}

    public LoginRequest(String identifier, String password) {
        this.identifier = identifier;
        this.password = password;
    }

    // Getters et Setters
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
