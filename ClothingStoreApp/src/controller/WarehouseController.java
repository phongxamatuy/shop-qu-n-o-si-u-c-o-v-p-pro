package controller;

import model.Warehouse;
import view.WarehouseManagementView;
import database.WarehouseDatabase;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * WarehouseController - Quản lý kho riêng theo username
 * Mỗi user có file warehouse riêng: username_warehouse.dat
 */
public class WarehouseController {
    private WarehouseManagementView view;
    private List<Warehouse> warehouseList;
    private WarehouseDatabase warehouseDatabase;
    private String username;
    
    public WarehouseController(WarehouseManagementView view, String username) {
        this.view = view;
        this.username = username;
        this.warehouseDatabase = new WarehouseDatabase(username);
        this.warehouseList = new ArrayList<>();
        
        initController();
        loadWarehousesFromDatabase();
    }
    
    private void initController() {
        view.getBtnAdd().addActionListener(e -> addProduct());
        view.getBtnUpdate().addActionListener(e -> updateProduct());
        view.getBtnDelete().addActionListener(e -> deleteProduct());
        view.getBtnSearch().addActionListener(e -> searchProduct());
        view.getBtnImport().addActionListener(e -> importStock());
        view.getBtnExport().addActionListener(e -> exportStock());
        view.getTxtSearch().addActionListener(e -> searchProduct());
    }
    
    // Tải dữ liệu từ database
    private void loadWarehousesFromDatabase() {
        if (!warehouseDatabase.fileExists()) {
            loadSampleData();
            saveWarehousesToDatabase();
        } else {
            List<Warehouse> loadedWarehouses = warehouseDatabase.loadWarehouses();
            if (!loadedWarehouses.isEmpty()) {
                warehouseList = loadedWarehouses;
            } else {
                loadSampleData();
                saveWarehousesToDatabase();
            }
        }
        
        refreshTable();
        updateStatistics();
    }
    
    // Lưu danh sách kho vào database
    private void saveWarehousesToDatabase() {
        if (warehouseDatabase.saveWarehouses(warehouseList)) {
            System.out.println("✓ Dữ liệu kho (" + username + ") đã được lưu!");
        } else {
            System.err.println("✗ Lỗi lưu dữ liệu kho!");
        }
    }
    
    // Thêm sản phẩm mới
    private void addProduct() {
        try {
            String productId = view.getTxtProductId().getText().trim();
            String productName = view.getTxtProductName().getText().trim();
            String category = view.getTxtCategory().getText().trim();
            String quantityStr = view.getTxtQuantity().getText().trim();
            String minStockStr = view.getTxtMinStock().getText().trim();
            String location = view.getTxtLocation().getText().trim();
            String warehouseName = (String) view.getCmbWarehouse().getSelectedItem();
            
            if (productId.isEmpty() || productName.isEmpty() || category.isEmpty() 
                    || quantityStr.isEmpty() || minStockStr.isEmpty() || location.isEmpty()) {
                JOptionPane.showMessageDialog(view, 
                    "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (isProductIdExists(productId)) {
                JOptionPane.showMessageDialog(view, 
                    "Mã sản phẩm đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int quantity = Integer.parseInt(quantityStr);
            int minStock = Integer.parseInt(minStockStr);
            
            if (quantity < 0 || minStock < 0) {
                JOptionPane.showMessageDialog(view, 
                    "Số lượng không được âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Warehouse product = new Warehouse(productId, productName, category, 
                                             quantity, minStock, location, warehouseName);
            
            warehouseList.add(product);
            saveWarehousesToDatabase();
            refreshTable();
            updateStatistics();
            clearForm();
            
            JOptionPane.showMessageDialog(view, 
                "Thêm sản phẩm thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, 
                "Số lượng phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, 
                "Có lỗi xảy ra: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Cập nhật sản phẩm
    private void updateProduct() {
        try {
            String productId = view.getTxtProductId().getText().trim();
            
            if (productId.isEmpty()) {
                JOptionPane.showMessageDialog(view, 
                    "Vui lòng chọn sản phẩm cần cập nhật!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Warehouse product = findProductById(productId);
            
            if (product == null) {
                JOptionPane.showMessageDialog(view, 
                    "Không tìm thấy sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String productName = view.getTxtProductName().getText().trim();
            String category = view.getTxtCategory().getText().trim();
            String quantityStr = view.getTxtQuantity().getText().trim();
            String minStockStr = view.getTxtMinStock().getText().trim();
            String location = view.getTxtLocation().getText().trim();
            String warehouseName = (String) view.getCmbWarehouse().getSelectedItem();
            
            if (productName.isEmpty() || category.isEmpty() 
                    || quantityStr.isEmpty() || minStockStr.isEmpty() || location.isEmpty()) {
                JOptionPane.showMessageDialog(view, 
                    "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int quantity = Integer.parseInt(quantityStr);
            int minStock = Integer.parseInt(minStockStr);
            
            if (quantity < 0 || minStock < 0) {
                JOptionPane.showMessageDialog(view, 
                    "Số lượng không được âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            product.setProductName(productName);
            product.setCategory(category);
            product.setQuantity(quantity);
            product.setMinStock(minStock);
            product.setLocation(location);
            product.setWarehouseName(warehouseName);
            
            saveWarehousesToDatabase();
            refreshTable();
            updateStatistics();
            clearForm();
            
            JOptionPane.showMessageDialog(view, 
                "Cập nhật sản phẩm thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, 
                "Số lượng phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, 
                "Có lỗi xảy ra: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Xóa sản phẩm
    private void deleteProduct() {
        int selectedRow = view.getWarehouseTable().getSelectedRow();
        
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(view, 
                "Vui lòng chọn sản phẩm cần xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(view, 
            "Bạn có chắc chắn muốn xóa sản phẩm này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            String productId = view.getTableModel().getValueAt(selectedRow, 0).toString();
            Warehouse product = findProductById(productId);
            
            if (product != null) {
                warehouseList.remove(product);
                saveWarehousesToDatabase();
                refreshTable();
                updateStatistics();
                clearForm();
                
                JOptionPane.showMessageDialog(view, 
                    "Xóa sản phẩm thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    // Nhập kho
    private void importStock() {
        int selectedRow = view.getWarehouseTable().getSelectedRow();
        
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(view, 
                "Vui lòng chọn sản phẩm cần nhập kho!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String productId = view.getTableModel().getValueAt(selectedRow, 0).toString();
        Warehouse product = findProductById(productId);
        
        if (product != null) {
            String input = JOptionPane.showInputDialog(view, 
                "Nhập số lượng cần nhập kho:", "Nhập Kho", JOptionPane.QUESTION_MESSAGE);
            
            if (input != null && !input.trim().isEmpty()) {
                try {
                    int amount = Integer.parseInt(input.trim());
                    
                    if (amount <= 0) {
                        JOptionPane.showMessageDialog(view, 
                            "Số lượng phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    product.importStock(amount);
                    saveWarehousesToDatabase();
                    refreshTable();
                    updateStatistics();
                    
                    JOptionPane.showMessageDialog(view, 
                        String.format("Nhập kho thành công!\nSản phẩm: %s\nSố lượng nhập: %d\nTồn kho mới: %d", 
                            product.getProductName(), amount, product.getQuantity()),
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(view, 
                        "Số lượng không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    // Xuất kho
    private void exportStock() {
        int selectedRow = view.getWarehouseTable().getSelectedRow();
        
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(view, 
                "Vui lòng chọn sản phẩm cần xuất kho!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String productId = view.getTableModel().getValueAt(selectedRow, 0).toString();
        Warehouse product = findProductById(productId);
        
        if (product != null) {
            String input = JOptionPane.showInputDialog(view, 
                String.format("Tồn kho hiện tại: %d\nNhập số lượng cần xuất kho:", 
                    product.getQuantity()),
                "Xuất Kho", JOptionPane.QUESTION_MESSAGE);
            
            if (input != null && !input.trim().isEmpty()) {
                try {
                    int amount = Integer.parseInt(input.trim());
                    
                    if (amount <= 0) {
                        JOptionPane.showMessageDialog(view, 
                            "Số lượng phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    if (product.exportStock(amount)) {
                        saveWarehousesToDatabase();
                        refreshTable();
                        updateStatistics();
                        
                        String message = String.format("Xuất kho thành công!\nSản phẩm: %s\nSố lượng xuất: %d\nTồn kho còn lại: %d", 
                            product.getProductName(), amount, product.getQuantity());
                        
                        if (product.isLowStock()) {
                            message += "\n\n⚠️ CẢNH BÁO: Sản phẩm sắp hết hàng!";
                        }
                        
                        JOptionPane.showMessageDialog(view, message, "Thành công", 
                            JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(view, 
                            String.format("Không đủ hàng để xuất!\nTồn kho hiện tại: %d\nYêu cầu xuất: %d", 
                                product.getQuantity(), amount),
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                    
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(view, 
                        "Số lượng không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    // Tìm kiếm sản phẩm
    private void searchProduct() {
        String keyword = view.getTxtSearch().getText().trim().toLowerCase();
        
        if (keyword.isEmpty()) {
            refreshTable();
            return;
        }
        
        List<Warehouse> filteredProducts = warehouseList.stream()
            .filter(product -> 
                product.getProductId().toLowerCase().contains(keyword) ||
                product.getProductName().toLowerCase().contains(keyword) ||
                product.getCategory().toLowerCase().contains(keyword) ||
                product.getLocation().toLowerCase().contains(keyword) ||
                product.getWarehouseName().toLowerCase().contains(keyword)
            )
            .collect(Collectors.toList());
        
        updateTable(filteredProducts);
        
        if (filteredProducts.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Không tìm thấy kết quả!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // Làm mới bảng
    private void refreshTable() {
        updateTable(warehouseList);
    }
    
    // Cập nhật bảng
    private void updateTable(List<Warehouse> products) {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);
        
        for (Warehouse product : products) {
            model.addRow(product.toTableRow());
        }
    }
    
    // Cập nhật thống kê
    private void updateStatistics() {
        int totalProducts = warehouseList.size();
        long lowStockCount = warehouseList.stream()
            .filter(p -> p.isLowStock() || p.isOutOfStock())
            .count();
        
        view.getLblTotalProducts().setText("Tổng sản phẩm: " + totalProducts);
        view.getLblLowStock().setText("Sắp hết hàng: " + lowStockCount);
    }
    
    // Xóa form
    private void clearForm() {
        view.getTxtProductId().setText("");
        view.getTxtProductName().setText("");
        view.getTxtCategory().setText("");
        view.getTxtQuantity().setText("");
        view.getTxtMinStock().setText("");
        view.getTxtLocation().setText("");
        view.getCmbWarehouse().setSelectedIndex(0);
        view.getWarehouseTable().clearSelection();
    }
    
    // Kiểm tra mã sản phẩm
    private boolean isProductIdExists(String productId) {
        return warehouseList.stream()
            .anyMatch(product -> product.getProductId().equals(productId));
    }
    
    // Tìm sản phẩm theo ID
    private Warehouse findProductById(String productId) {
        return warehouseList.stream()
            .filter(product -> product.getProductId().equals(productId))
            .findFirst()
            .orElse(null);
    }
    
    // Load dữ liệu mẫu
    private void loadSampleData() {
        warehouseList.add(new Warehouse("SP001", "Áo khoác Gió", "Áo", 15, 10, "A-01-01", "Kho Chính"));
        warehouseList.add(new Warehouse("SP002", "Áo phông mixigaming", "Áo", 8, 10, "A-01-02", "Kho Chính"));
        warehouseList.add(new Warehouse("SP003", "Quần jean ", "Quần", 25, 15, "A-02-01", "Kho Chính"));
    }
    
    public List<Warehouse> getWarehouseList() {
        return warehouseList;
    }
}