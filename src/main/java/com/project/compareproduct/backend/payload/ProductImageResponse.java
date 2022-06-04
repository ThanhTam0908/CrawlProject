package com.project.compareproduct.backend.payload;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductImageResponse {
    @NonNull
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String image;
}
