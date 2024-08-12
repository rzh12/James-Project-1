package com.example.stylish.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String provider;
    private String name;
    private String email;
    private String password;
    private String passwordHash;
    private String picture;
}
