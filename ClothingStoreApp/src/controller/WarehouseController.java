package controller;

import model.Warehouse;
import view.WarehouseManagementView;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WarehouseController {
    private WarehouseManagementView view;
    private List<Warehouse> warehouseList;
    
    public WarehouseController(WarehouseManagementView view) {
        this.view = view;
        this.warehouseList = new ArrayList<>();
        
        // Khởi tạo event listeners
        initController();
        
        // Load dữ liệu mẫu
        loadSampleData();
    }
    
    private void initController() {
        // Sự kiện nút Thêm
        view.getBtnAdd().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });
        
        // Sự kiện nút Cập nhật
        view.getBtnUpdate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProduct();
            }
        });
        
        // Sự kiện nút Xóa
        view.getBtnDelete().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProduct();
            }
        });
        
        // Sự kiện nút Tìm kiếm
        view.getBtnSearch().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchProduct();
            }
        });
        
        // Sự kiện nút Nhập kho
        view.getBtnImport().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importStock();
            }
        });
        
        // Sự kiện nút Xuất kho
        view.getBtnExport().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportStock();
            }
        });
        
        // Sự kiện Enter trong ô tìm kiếm
        view.getTxtSearch().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchProduct();
            }
        });
    }
    
    // Thêm sản phẩm mới
    private void addProduct() {
        try {
            // Lấy dữ liệu từ view
            String productId = view.getTxtProductId().getText().trim();
            String productName = view.getTxtProductName().getText().trim();
            String category = view.getTxtCategory().getText().trim();
            String quantityStr = view.getTxtQuantity().getText().trim();
            String minStockStr = view.getTxtMinStock().getText().trim();
            String location = view.getTxtLocation().getText().trim();
            String warehouseName = (String) view.getCmbWarehouse().getSelectedItem();
            
            // Validate dữ liệu
            if (productId.isEmpty() || productName.isEmpty() || category.isEmpty() 
                    || quantityStr.isEmpty() || minStockStr.isEmpty() || location.isEmpty()) {
                JOptionPane.showMessageDialog(view, 
                    "Vui lòng nhập đầy đủ thông tin!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Kiểm tra mã sản phẩm đã tồn tại
            if (isProductIdExists(productId)) {
                JOptionPane.showMessageDialog(view, 
                    "Mã sản phẩm đã tồn tại!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Parse số
            int quantity = Integer.parseInt(quantityStr);
            int minStock = Integer.parseInt(minStockStr);
            
            if (quantity < 0 || minStock < 0) {
                JOptionPane.showMessageDialog(view, 
                    "Số lượng không được âm!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Tạo sản phẩm mới
            Warehouse product = new Warehouse(productId, productName, category, 
                                             quantity, minStock, location, warehouseName);
            
            // Thêm vào danh sách
            warehouseList.add(product);
            
            // Cập nhật bảng và thống kê
            refreshTable();
            updateStatistics();
            
            // Xóa form
            clearForm();
            
            JOptionPane.showMessageDialog(view, 
                "Thêm sản phẩm thành công!", 
                "Thành công", 
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, 
                "Số lượng phải là số hợp lệ!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, 
                "Có lỗi xảy ra: " + e.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Cập nhật sản phẩm
    private void updateProduct() {
        try {
            String productId = view.getTxtProductId().getText().trim();
            
            if (productId.isEmpty()) {
                JOptionPane.showMessageDialog(view, 
                    "Vui lòng chọn sản phẩm cần cập nhật!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Tìm sản phẩm
            Warehouse product = findProductById(productId);
            
            if (product == null) {
                JOptionPane.showMessageDialog(view, 
                    "Không tìm thấy sản phẩm!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Cập nhật thông tin
            String productName = view.getTxtProductName().getText().trim();
            String category = view.getTxtCategory().getText().trim();
            String quantityStr = view.getTxtQuantity().getText().trim();
            String minStockStr = view.getTxtMinStock().getText().trim();
            String location = view.getTxtLocation().getText().trim();
            String warehouseName = (String) view.getCmbWarehouse().getSelectedItem();
            
            if (productName.isEmpty() || category.isEmpty() 
                    || quantityStr.isEmpty() || minStockStr.isEmpty() || location.isEmpty()) {
                JOptionPane.showMessageDialog(view, 
                    "Vui lòng nhập đầy đủ thông tin!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int quantity = Integer.parseInt(quantityStr);
            int minStock = Integer.parseInt(minStockStr);
            
            if (quantity < 0 || minStock < 0) {
                JOptionPane.showMessageDialog(view, 
                    "Số lượng không được âm!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Cập nhật
            product.setProductName(productName);
            product.setCategory(category);
            product.setQuantity(quantity);
            product.setMinStock(minStock);
            product.setLocation(location);
            product.setWarehouseName(warehouseName);
            
            // Refresh bảng và thống kê
            refreshTable();
            updateStatistics();
            
            JOptionPane.showMessageDialog(view, 
                "Cập nhật sản phẩm thành công!", 
                "Thành công", 
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, 
                "Số lượng phải là số hợp lệ!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, 
                "Có lỗi xảy ra: " + e.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Xóa sản phẩm
    private void deleteProduct() {
        int selectedRow = view.getWarehouseTable().getSelectedRow();
        
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(view, 
                "Vui lòng chọn sản phẩm cần xóa!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(view, 
            "Bạn có chắc chắn muốn xóa sản phẩm này?", 
            "Xác nhận", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            String productId = view.getTableModel().getValueAt(selectedRow, 0).toString();
            Warehouse product = findProductById(productId);
            
            if (product != null) {
                warehouseList.remove(product);
                refreshTable();
                updateStatistics();
                clearForm();
                
                JOptionPane.showMessageDialog(view, 
                    "Xóa sản phẩm thành công!", 
                    "Thành công", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    // Nhập kho
    private void importStock() {
        int selectedRow = view.getWarehouseTable().getSelectedRow();
        
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(view, 
                "Vui lòng chọn sản phẩm cần nhập kho!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String productId = view.getTableModel().getValueAt(selectedRow, 0).toString();
        Warehouse product = findProductById(productId);
        
        if (product != null) {
            String input = JOptionPane.showInputDialog(view, 
                "Nhập số lượng cần nhập kho:", 
                "Nhập Kho", 
                JOptionPane.QUESTION_MESSAGE);
            
            if (input != null && !input.trim().isEmpty()) {
                try {
                    int amount = Integer.parseInt(input.trim());
                    
                    if (amount <= 0) {
                        JOptionPane.showMessageDialog(view, 
                            "Số lượng phải lớn hơn 0!", 
                            "Lỗi", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    product.importStock(amount);
                    refreshTable();
                    updateStatistics();
                    
                    JOptionPane.showMessageDialog(view, 
                        String.format("Nhập kho thành công!\nSản phẩm: %s\nSố lượng nhập: %d\nTồn kho mới: %d", 
                            product.getProductName(), amount, product.getQuantity()),
                        "Thành công", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(view, 
                        "Số lượng không hợp lệ!", 
                        "Lỗi", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    // Xuất kho
    private void exportStock() {
        int selectedRow = view.getWarehouseTable().getSelectedRow();
        
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(view, 
                "Vui lòng chọn sản phẩm cần xuất kho!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String productId = view.getTableModel().getValueAt(selectedRow, 0).toString();
        Warehouse product = findProductById(productId);
        
        if (product != null) {
            String input = JOptionPane.showInputDialog(view, 
                String.format("Tồn kho hiện tại: %d\nNhập số lượng cần xuất kho:", 
                    product.getQuantity()),
                "Xuất Kho", 
                JOptionPane.QUESTION_MESSAGE);
            
            if (input != null && !input.trim().isEmpty()) {
                try {
                    int amount = Integer.parseInt(input.trim());
                    
                    if (amount <= 0) {
                        JOptionPane.showMessageDialog(view, 
                            "Số lượng phải lớn hơn 0!", 
                            "Lỗi", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    if (product.exportStock(amount)) {
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
                            "Lỗi", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                    
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(view, 
                        "Số lượng không hợp lệ!", 
                        "Lỗi", 
                        JOptionPane.ERROR_MESSAGE);
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
                "Không tìm thấy kết quả!", 
                "Thông báo", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // Làm mới bảng
    private void refreshTable() {
        updateTable(warehouseList);
    }
    
    // Cập nhật bảng với danh sách
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
    
    // Kiểm tra mã sản phẩm đã tồn tại
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
    // Load dữ liệu mẫu
    private void loadSampleData() {
        warehouseList.add(new Warehouse("SP001", "Áo khoác Gió", "Áo", 15, 10, "A-01-01", "Kho Chính"));
        warehouseList.add(new Warehouse("SP002", "Áo phông mixigaming", "Áo", 8, 10, "A-01-02", "Kho Chính"));
        warehouseList.add(new Warehouse("SP003", "Quần jean ", "Quần", 25, 15, "A-02-01", "Kho Chính"));
        warehouseList.add(new Warehouse("SP004", "Niketech", "Bộ", 5, 8, "A-02-02", "Kho Phụ 1"));
        warehouseList.add(new Warehouse("SP005", "Quần âu ", "Quần", 0, 10, "B-01-01", "Kho Phụ 2"));
        warehouseList.add(new Warehouse("SP006", "Áo sơ mi", "Áo", 50, 20, "B-01-02", "Kho Chính"));
        warehouseList.add(new Warehouse("SP007", "Váy chân ngắn", "Váy", 12, 15, "B-02-01", "Kho Miền Nam"));
        warehouseList.add(new Warehouse("SP008", "Quần bò xuông", "Quần", 100, 50, "C-01-01", "Kho Miền Bắc"));
        refreshTable();
        updateStatistics();
    }
    
    // Getters
    public List<Warehouse> getWarehouseList() {
        return warehouseList;
    }
}