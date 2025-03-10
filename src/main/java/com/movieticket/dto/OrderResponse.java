package com.movieticket.dto;

public class OrderResponse {
    private String orderId;
    private String bookId;
    private int quantity;
    private double totalAmount;

    // Constructor
    public OrderResponse(String orderId, String bookId, int quantity, double totalAmount) {
        this.orderId = orderId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}