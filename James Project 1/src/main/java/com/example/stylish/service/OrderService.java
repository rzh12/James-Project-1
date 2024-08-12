package com.example.stylish.service;

import com.example.stylish.model.Order;
import com.example.stylish.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TapPayService tapPayService;

    // Method to save the order initially
    public int saveInitialOrder(Order order) {
        return orderRepository.saveOrder(order);
    }

    // Method to update order status
    public void updateOrderStatus(int orderId, int status) {
        orderRepository.updateOrderStatus(orderId, status);
    }

    /**
     * This method processes an order by verifying the payment using TapPay and updating the order status.
     * The @Transactional annotation ensures that all operations within the method are executed within a single transaction.
     * If any operation fails, the entire transaction will be rolled back, maintaining data consistency.
     */
    @Transactional
    public String processOrder(Order order, int orderId) {
        // Prepare cardholder information
        Map<String, Object> cardholder = new HashMap<>();
        cardholder.put("phone_number", order.getRecipient().getPhone());
        cardholder.put("name", order.getRecipient().getName());
        cardholder.put("email", order.getRecipient().getEmail());
        cardholder.put("address", order.getRecipient().getAddress());

        // Verify prime with TapPay
        Map<String, Object> tapPayResponse = tapPayService.verifyPrime(
                order.getPrime(),
                order.getTotal(),
                "Order Payment",
                cardholder
        );

        if (tapPayResponse.get("status").equals(0)) {
            // Update the order status to "Paid"
            orderRepository.updateOrderStatus(orderId, 1);
            // Save the order item
            order.getList().forEach(item -> item.setidAsInteger(Integer.parseInt(item.getId())));
            orderRepository.saveOrderItems(orderId, order.getList());
            return String.valueOf(orderId);
        } else {
            throw new RuntimeException("Payment verification failed with status: " + tapPayResponse.get("status") + ", message: " + tapPayResponse.get("msg"));
        }
    }
}
