package com.project.compareproduct.backend.payload;

import lombok.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserInformationResponse {
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String gender;

    @Getter
    @Setter
    private String address;

    @Getter
    @Setter
    private String cellPhone;
}
