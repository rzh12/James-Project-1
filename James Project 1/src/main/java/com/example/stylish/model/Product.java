package com.example.stylish.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Integer id;
    private String category;
    private String title;
    private String description;
    private Double price;
    private String texture;
    private String wash;
    private String place;
    private String note;
    private String story;
    private List<Color> colors;
    private List<String> sizes;
    private List<Variant> variants;

    @JsonProperty("main_image")
    private String mainImage;

    @JsonProperty("images")
    private List<String> images;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Color {
        private String code;
        private String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Variant {
        @JsonProperty("color_code")
        private String colorCode;
        private String size;
        private Integer stock;
    }
}
