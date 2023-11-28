package com.productservice.productservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductDto {
    // DTO -> Data Transfer Objects.
    private Long id;
    private String title;
    private String description;
    private int price;
    private String image;
    private String category;
}
