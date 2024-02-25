package com.example.trellobackend.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String email;
    private String currentPassword;
    @NotBlank
    private String newPassword;
}
