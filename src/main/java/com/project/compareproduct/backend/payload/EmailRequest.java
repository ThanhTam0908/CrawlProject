package com.project.compareproduct.backend.payload;

import lombok.*;

import javax.validation.constraints.NotBlank;

@ToString
public class EmailRequest {
    @Getter
    @Setter
    @NotBlank
    private String email;
}
