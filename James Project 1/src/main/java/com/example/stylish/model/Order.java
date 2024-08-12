package com.example.stylish.model;

import com.example.stylish.dto.OrderItemDTO;
import com.example.stylish.dto.RecipientDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Integer userId;
    private String prime;
    private String shipping;
    private String payment;
    private Double subtotal;
    private Double freight;
    private Double total;
    private RecipientDTO recipient;
    private List<OrderItemDTO> list;
    private Integer paymentStatus;
}
