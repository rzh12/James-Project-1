package com.example.stylish.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Campaign {
    @JsonProperty("product_id")
    private Integer productId;
    private String picture;
    private String story;
}
