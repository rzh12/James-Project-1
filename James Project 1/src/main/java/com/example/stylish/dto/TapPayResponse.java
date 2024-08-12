package com.example.stylish.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TapPayResponse {
    private DataContainer data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataContainer {
        private String number;
    }
}
