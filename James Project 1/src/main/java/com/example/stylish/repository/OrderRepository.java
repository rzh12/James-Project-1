package com.example.stylish.repository;

import com.example.stylish.dto.OrderItemDTO;
import com.example.stylish.dto.RecipientDTO;
import com.example.stylish.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
public class OrderRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Method to save an order and return the generated order ID
    public int saveOrder(Order order) {
        String orderSql = "INSERT INTO orders (user_id, prime, shipping, payment, subtotal, freight, total, recipient_id, payment_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Save recipient first and get recipient ID
        int recipientId = saveRecipient(order.getRecipient());

        // Use KeyHolder to get the generated order ID
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, order.getUserId());
            ps.setString(2, order.getPrime());
            ps.setString(3, order.getShipping());
            ps.setString(4, order.getPayment());
            ps.setDouble(5, order.getSubtotal());
            ps.setDouble(6, order.getFreight());
            ps.setDouble(7, order.getTotal());
            ps.setInt(8, recipientId);
            ps.setInt(9, 0); // initialize payment_status
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    // Method to update the status of an order
    public void updateOrderStatus(int orderId, int status) {
        String sql = "UPDATE orders SET payment_status = ? WHERE id = ?";
        jdbcTemplate.update(sql, status, orderId);
    }

    // Method to save recipient and return the generated recipient ID
    private int saveRecipient(RecipientDTO recipient) {
        String sql = "INSERT INTO recipient (name, phone, email, address, time) VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, recipient.getName());
            ps.setString(2, recipient.getPhone());
            ps.setString(3, recipient.getEmail());
            ps.setString(4, recipient.getAddress());
            ps.setString(5, recipient.getTime());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    // Method to save order items
    public void saveOrderItems(int orderId, List<OrderItemDTO> items) {
        String sql = "INSERT INTO order_item (order_id, product_id, product_name, product_price, color_code, size, qty) VALUES (?, ?, ?, ?, ?, ?, ?)";

        for (OrderItemDTO item : items) {
            // Convert the item id from String to Integer
            int productId = Integer.parseInt(item.getId());
            jdbcTemplate.update(sql, orderId, productId, item.getName(), item.getPrice(), item.getColors().get(0).getCode(), item.getSize(), item.getQty());
        }
    }
}
