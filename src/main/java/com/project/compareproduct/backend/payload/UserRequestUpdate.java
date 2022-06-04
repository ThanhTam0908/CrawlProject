package com.project.compareproduct.backend.payload;

import lombok.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRequestUpdate {

    @Getter
    @Setter
    private String name;

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
