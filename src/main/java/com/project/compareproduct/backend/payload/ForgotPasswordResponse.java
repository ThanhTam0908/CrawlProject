package com.project.compareproduct.backend.payload;

import com.project.compareproduct.backend.model.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ForgotPasswordResponse {
    private String passwordResetToken;

    public ForgotPasswordResponse(User user) {
        passwordResetToken = user.getPasswordResetToken();
    }
}
