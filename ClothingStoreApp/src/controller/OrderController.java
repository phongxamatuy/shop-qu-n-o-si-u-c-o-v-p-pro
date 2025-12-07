package controller;

import model.Order;
import view.OrderManagementView;
import database.OrderDatabase;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * OrderController - Quản lý đơn hàng riêng theo username
 * Mỗi user có file orders riêng: username_orders.dat
 */
public class OrderController {
    private OrderManagementView view;
    private List<Order> orderList;
    private OrderDatabase orderDatabase;
    private String username;
    
    public OrderController(OrderManagementView view, String username) {
        this.view = view;
        this.username = username;
        this.orderDatabase = new OrderDatabase(username);
        this.orderList = new ArrayList<>();
        
        initController();
        loadOrdersFromDatabase();
    }
    
    private void initController() {
        view.getBtnAdd().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOrder();
            }
        });
        
        view.getBtnUpdate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateOrder();
            }
        });
        
        view.getBtnDelete().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteOrder();
            }
        });
        
        view.getBtnSearch().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchOrder();
            }
        });
        
        view.getTxtSearch().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchOrder();
            }
        });
    }
    
    // Tải dữ liệu từ database
    private void loadOrdersFromDatabase() {
        if (!orderDatabase.fileExists()) {
            loadSampleData();
            saveOrdersToDatabase();
        } else {
            List<Order> loadedOrders = orderDatabase.loadOrders();
            if (!loadedOrders.isEmpty()) {
                orderList = loadedOrders;
            } else {
                loadSampleData();
                saveOrdersToDatabase();
            }
        }
        
        refreshTable();
    }
    
    // Lưu danh sách đơn hàng vào database
    private void saveOrdersToDatabase() {
        if (orderDatabase.saveOrders(orderList)) {
            System.out.println("✓ Dữ liệu đã được lưu: " + orderDatabase.getFilePath());
        } else {
            System.err.println("✗ Lỗi lưu dữ liệu!");
        }
    }
    
    // Thêm đơn hàng mới
    private void addOrder() {
        try {
            String orderId = view.getTxtOrderId().getText().trim();
            String customerName = view.getTxtCustomerName().getText().trim();
            String productName = view.getTxtProductName().getText().trim();
            String quantityStr = view.getTxtQuantity().getText().trim();
            String priceStr = view.getTxtPrice().getText().trim();
            String status = (String) view.getCmbStatus().getSelectedItem();
            
            if (orderId.isEmpty() || customerName.isEmpty() || productName.isEmpty() 
                    || quantityStr.isEmpty() || priceStr.isEmpty()) {
                JOptionPane.showMessageDialog(view, 
                    "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (isOrderIdExists(orderId)) {
                JOptionPane.showMessageDialog(view, 
                    "Mã đơn hàng đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            quantityStr = quantityStr.replace(",", "");
            priceStr = priceStr.replace(",", "");
            
            int quantity = Integer.parseInt(quantityStr);
            double price = Double.parseDouble(priceStr);
            
            if (quantity <= 0 || price <= 0) {
                JOptionPane.showMessageDialog(view, 
                    "Số lượng và giá phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Order order = new Order(orderId, customerName, productName, quantity, price, status);
            
            if (!order.isValid()) {
                JOptionPane.showMessageDialog(view, 
                    "Dữ liệu đơn hàng không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            orderList.add(order);
            saveOrdersToDatabase();
            refreshTable();
            clearForm();
            
            JOptionPane.showMessageDialog(view, 
                "Thêm đơn hàng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, 
                "Số lượng và giá phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, 
                "Có lỗi xảy ra: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Cập nhật đơn hàng
    private void updateOrder() {
        try {
            String orderId = view.getTxtOrderId().getText().trim();
            
            if (orderId.isEmpty()) {
                JOptionPane.showMessageDialog(view, 
                    "Vui lòng chọn đơn hàng cần cập nhật!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Order order = findOrderById(orderId);
            
            if (order == null) {
                JOptionPane.showMessageDialog(view, 
                    "Không tìm thấy đơn hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String customerName = view.getTxtCustomerName().getText().trim();
            String productName = view.getTxtProductName().getText().trim();
            String quantityStr = view.getTxtQuantity().getText().trim();
            String priceStr = view.getTxtPrice().getText().trim();
            String status = (String) view.getCmbStatus().getSelectedItem();
            
            if (customerName.isEmpty() || productName.isEmpty() 
                    || quantityStr.isEmpty() || priceStr.isEmpty()) {
                JOptionPane.showMessageDialog(view, 
                    "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            quantityStr = quantityStr.replace(",", "");
            priceStr = priceStr.replace(",", "");
            
            int quantity = Integer.parseInt(quantityStr);
            double price = Double.parseDouble(priceStr);
            
            if (quantity <= 0 || price <= 0) {
                JOptionPane.showMessageDialog(view, 
                    "Số lượng và giá phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            order.setCustomerName(customerName);
            order.setProductName(productName);
            order.setQuantity(quantity);
            order.setPrice(price);
            order.setStatus(status);
            
            saveOrdersToDatabase();
            refreshTable();
            clearForm();
            
            JOptionPane.showMessageDialog(view, 
                "Cập nhật đơn hàng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, 
                "Số lượng và giá phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, 
                "Có lỗi xảy ra: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Xóa đơn hàng
    private void deleteOrder() {
        int selectedRow = view.getOrderTable().getSelectedRow();
        
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(view, 
                "Vui lòng chọn đơn hàng cần xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(view, 
            "Bạn có chắc chắn muốn xóa đơn hàng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            String orderId = view.getTableModel().getValueAt(selectedRow, 0).toString();
            Order order = findOrderById(orderId);
            
            if (order != null) {
                orderList.remove(order);
                saveOrdersToDatabase();
                refreshTable();
                clearForm();
                
                JOptionPane.showMessageDialog(view, 
                    "Xóa đơn hàng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    // Tìm kiếm đơn hàng
    private void searchOrder() {
        String keyword = view.getTxtSearch().getText().trim().toLowerCase();
        
        if (keyword.isEmpty()) {
            refreshTable();
            return;
        }
        
        List<Order> filteredOrders = orderList.stream()
            .filter(order -> 
                order.getOrderId().toLowerCase().contains(keyword) ||
                order.getCustomerName().toLowerCase().contains(keyword) ||
                order.getProductName().toLowerCase().contains(keyword) ||
                order.getStatus().toLowerCase().contains(keyword)
            )
            .collect(Collectors.toList());
        
        updateTable(filteredOrders);
        
        if (filteredOrders.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Không tìm thấy kết quả!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // Làm mới bảng
    private void refreshTable() {
        updateTable(orderList);
    }
    
    // Cập nhật bảng
    private void updateTable(List<Order> orders) {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);
        
        for (Order order : orders) {
            model.addRow(order.toTableRow());
        }
    }
    
    // Xóa form
    private void clearForm() {
        view.getTxtOrderId().setText("");
        view.getTxtCustomerName().setText("");
        view.getTxtProductName().setText("");
        view.getTxtQuantity().setText("");
        view.getTxtPrice().setText("");
        view.getCmbStatus().setSelectedIndex(0);
        view.getOrderTable().clearSelection();
    }
    
    // Kiểm tra mã đơn hàng
    private boolean isOrderIdExists(String orderId) {
        return orderList.stream()
            .anyMatch(order -> order.getOrderId().equals(orderId));
    }
    
    // Tìm đơn hàng theo ID
    private Order findOrderById(String orderId) {
        return orderList.stream()
            .filter(order -> order.getOrderId().equals(orderId))
            .findFirst()
            .orElse(null);
    }
    
    // Load dữ liệu mẫu
    private void loadSampleData() {
        orderList.add(new Order("ORD001", "Nguyễn Văn A", "Áo Niketech", 1, 250000, "Chờ xử lý"));
        orderList.add(new Order("ORD002", "Trần Thị B", "Quần jean ống rộng", 2, 290000, "Đang giao"));
        orderList.add(new Order("ORD003", "Lê Văn C", "Áo khoác gió", 1, 220000, "Đã giao"));
    }
    
    public List<Order> getOrderList() {
        return orderList;
    }
}