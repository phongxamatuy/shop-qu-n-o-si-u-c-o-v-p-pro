package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Order {
    private String orderId;
    private String customerName;
    private String productName;
    private int quantity;
    private double price;
    private double totalAmount;
    private String status;
    private LocalDateTime orderDate;
    
    // Constructor mặc định
    public Order() {
        this.orderDate = LocalDateTime.now();
        this.status = "Chờ xử lý";
    }
    
    // Constructor đầy đủ
    public Order(String orderId, String customerName, String productName, 
                 int quantity, double price, String status) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.totalAmount = calculateTotalAmount();
        this.status = status;
        this.orderDate = LocalDateTime.now();
    }
    
    // Constructor với ngày tùy chỉnh
    public Order(String orderId, String customerName, String productName, 
                 int quantity, double price, String status, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.totalAmount = calculateTotalAmount();
        this.status = status;
        this.orderDate = orderDate;
    }
    
    // Tính tổng tiền
    private double calculateTotalAmount() {
        return quantity * price;
    }
    
    // Cập nhật tổng tiền khi thay đổi số lượng hoặc giá
    public void updateTotalAmount() {
        this.totalAmount = calculateTotalAmount();
    }
    
    // Getters
    public String getOrderId() {
        return orderId;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public double getPrice() {
        return price;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public String getStatus() {
        return status;
    }
    
    public LocalDateTime getOrderDate() {
        return orderDate;
    }
    
    public String getFormattedOrderDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return orderDate.format(formatter);
    }
    
    // Setters
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        updateTotalAmount();
    }
    
    public void setPrice(double price) {
        this.price = price;
        updateTotalAmount();
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
    
    // Kiểm tra trạng thái
    public boolean isPending() {
        return "Chờ xử lý".equals(status);
    }
    
    public boolean isShipping() {
        return "Đang giao".equals(status);
    }
    
    public boolean isDelivered() {
        return "Đã giao".equals(status);
    }
    
    public boolean isCancelled() {
        return "Đã hủy".equals(status);
    }
    
    // Phương thức validate
    public boolean isValid() {
        return orderId != null && !orderId.trim().isEmpty()
                && customerName != null && !customerName.trim().isEmpty()
                && productName != null && !productName.trim().isEmpty()
                && quantity > 0
                && price > 0;
    }
    
    // Chuyển đổi sang mảng Object cho JTable
    public Object[] toTableRow() {
        return new Object[] {
            orderId,
            customerName,
            productName,
            quantity,
            price,
            String.format("%.2f", totalAmount),
            status
        };
    }
    
    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                ", orderDate=" + getFormattedOrderDate() +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId != null ? orderId.equals(order.orderId) : order.orderId == null;
    }
    
    @Override
    public int hashCode() {
        return orderId != null ? orderId.hashCode() : 0;
    }
    
}