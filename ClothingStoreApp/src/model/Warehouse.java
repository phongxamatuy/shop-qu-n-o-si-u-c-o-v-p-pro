package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Warehouse implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String productId;
    private String productName;
    private String category;
    private int quantity;
    private int minStock;
    private String location;
    private String warehouseName;
    private LocalDateTime lastUpdated;
    
    // Constructor mặc định
    public Warehouse() {
        this.lastUpdated = LocalDateTime.now();
    }
    
    // Constructor đầy đủ
    public Warehouse(String productId, String productName, String category, 
                     int quantity, int minStock, String location, String warehouseName) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.quantity = quantity;
        this.minStock = minStock;
        this.location = location;
        this.warehouseName = warehouseName;
        this.lastUpdated = LocalDateTime.now();
    }
    
    // Getters
    public String getProductId() {
        return productId;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public String getCategory() {
        return category;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public int getMinStock() {
        return minStock;
    }
    
    public String getLocation() {
        return location;
    }
    
    public String getWarehouseName() {
        return warehouseName;
    }
    
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }
    
    public String getFormattedLastUpdated() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return lastUpdated.format(formatter);
    }
    
    // Setters
    public void setProductId(String productId) {
        this.productId = productId;
        updateLastUpdated();
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
        updateLastUpdated();
    }
    
    public void setCategory(String category) {
        this.category = category;
        updateLastUpdated();
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        updateLastUpdated();
    }
    
    public void setMinStock(int minStock) {
        this.minStock = minStock;
        updateLastUpdated();
    }
    
    public void setLocation(String location) {
        this.location = location;
        updateLastUpdated();
    }
    
    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
        updateLastUpdated();
    }
    
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
    // Phương thức cập nhật thời gian
    private void updateLastUpdated() {
        this.lastUpdated = LocalDateTime.now();
    }
    
    // Nhập kho
    public void importStock(int amount) {
        if (amount > 0) {
            this.quantity += amount;
            updateLastUpdated();
        }
    }
    
    // Xuất kho
    public boolean exportStock(int amount) {
        if (amount > 0 && amount <= this.quantity) {
            this.quantity -= amount;
            updateLastUpdated();
            return true;
        }
        return false;
    }
    
    // Kiểm tra hàng sắp hết
    public boolean isLowStock() {
        return quantity < minStock && quantity > 0;
    }
    
    // Kiểm tra hàng hết
    public boolean isOutOfStock() {
        return quantity == 0;
    }
    
    // Phương thức validate
    public boolean isValid() {
        return productId != null && !productId.trim().isEmpty()
                && productName != null && !productName.trim().isEmpty()
                && category != null && !category.trim().isEmpty()
                && location != null && !location.trim().isEmpty()
                && warehouseName != null && !warehouseName.trim().isEmpty()
                && quantity >= 0
                && minStock >= 0;
    }
    
    // Chuyển đổi sang mảng Object cho JTable
    public Object[] toTableRow() {
        String status = isOutOfStock() ? "Hết hàng" : (isLowStock() ? "Sắp hết" : "Bình thường");
        return new Object[] {
            productId,
            productName,
            category,
            quantity,
            minStock,
            status,
            location,
            warehouseName
        };
    }
    
    @Override
    public String toString() {
        return "Warehouse{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                ", quantity=" + quantity +
                ", minStock=" + minStock +
                ", location='" + location + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                ", lastUpdated=" + getFormattedLastUpdated() +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warehouse warehouse = (Warehouse) o;
        return productId != null ? productId.equals(warehouse.productId) : warehouse.productId == null;
    }
    
    @Override
    public int hashCode() {
        return productId != null ? productId.hashCode() : 0;
    }
}