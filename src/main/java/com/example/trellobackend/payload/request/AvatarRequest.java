package com.example.trellobackend.payload.request;

public class AvatarRequest {
    private String image;
    private String email;
    private String password;

    public AvatarRequest(String image, String email, String password) {
        this.image = image;
        this.email = email;
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
