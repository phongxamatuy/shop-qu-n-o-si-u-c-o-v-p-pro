package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Warehouse {
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
        this.minStock = 10; // Giá trị mặc định
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
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.lastUpdated = LocalDateTime.now();
    }
    
    public void setMinStock(int minStock) {
        this.minStock = minStock;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
    
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
    // Phương thức nghiệp vụ
    
    // Nhập hàng vào kho
    public void importStock(int amount) {
        if (amount > 0) {
            this.quantity += amount;
            this.lastUpdated = LocalDateTime.now();
        }
    }
    
    // Xuất hàng ra khỏi kho
    public boolean exportStock(int amount) {
        if (amount > 0 && amount <= this.quantity) {
            this.quantity -= amount;
            this.lastUpdated = LocalDateTime.now();
            return true;
        }
        return false;
    }
    
    // Kiểm tra trạng thái kho
    public String getStockStatus() {
        if (quantity == 0) {
            return "Hết hàng";
        } else if (quantity <= minStock) {
            return "Sắp hết";
        } else {
            return "Còn hàng";
        }
    }
    
    // Kiểm tra có phải hàng sắp hết không
    public boolean isLowStock() {
        return quantity > 0 && quantity <= minStock;
    }
    
    // Kiểm tra có hết hàng không
    public boolean isOutOfStock() {
        return quantity == 0;
    }
    
    // Kiểm tra có đủ hàng để xuất không
    public boolean hasEnoughStock(int requestedAmount) {
        return quantity >= requestedAmount;
    }
    
    // Tính phần trăm còn lại so với mức tối thiểu
    public double getStockPercentage() {
        if (minStock == 0) return 100.0;
        return (double) quantity / minStock * 100.0;
    }
    
    // Validate dữ liệu
    public boolean isValid() {
        return productId != null && !productId.trim().isEmpty()
                && productName != null && !productName.trim().isEmpty()
                && category != null && !category.trim().isEmpty()
                && quantity >= 0
                && minStock >= 0
                && location != null && !location.trim().isEmpty()
                && warehouseName != null && !warehouseName.trim().isEmpty();
    }
    
    // Chuyển đổi sang mảng Object cho JTable
    public Object[] toTableRow() {
        return new Object[] {
            productId,
            productName,
            category,
            quantity,
            minStock,
            location,
            warehouseName,
            getStockStatus()
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
                ", status='" + getStockStatus() + '\'' +
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