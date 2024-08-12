package com.example.stylish.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private String id;
    private String name;
    private double price;
    private List<Color> colors;
    private String size;
    private int qty;

    public void setidAsInteger(Integer id) {
        this.id = id.toString();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Color {
        private String code;
        private String name;
    }

}
