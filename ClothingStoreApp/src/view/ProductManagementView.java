package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import controller.ProductController;
import model.Product;

public class ProductManagementView extends JFrame {
    private static final Color BROWN_HEADER = new Color(139, 90, 60);
    private static final Color LIGHT_BROWN = new Color(222, 204, 190);
    private static final Color BUTTON_COLOR = new Color(139, 90, 60);
    
    private final ProductController controller = new ProductController();
    private JTable productTable;
    private DefaultTableModel tableModel;
    
    private JTextField txtId, txtName, txtCategory, txtPrice, txtQuantity, txtSearch;
    
    public ProductManagementView() {
        initComponents();
        loadProducts();
    }
    
    private void initComponents() {
        setTitle("Quản Lý Sản Phẩm");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = createHeader();
        add(headerPanel, BorderLayout.NORTH);
        
        // Main Content
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(LIGHT_BROWN);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Left Panel - Form nhập liệu
        JPanel formPanel = createFormPanel();
        
        // Right Panel - Bảng danh sách
        JPanel tablePanel = createTablePanel();
        
        mainPanel.add(formPanel, BorderLayout.WEST);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BROWN_HEADER);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel lblTitle = new JLabel("👕 QUẢN LÝ SẢN PHẨM");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        
        header.add(lblTitle, BorderLayout.WEST);
        
        return header;
    }
    
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 180, 160), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        formPanel.setPreferredSize(new Dimension(350, 0));
        
        JLabel lblFormTitle = new JLabel("Thông Tin Sản Phẩm");
        lblFormTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblFormTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(lblFormTitle);
        formPanel.add(Box.createVerticalStrut(20));
        
        // ID
        formPanel.add(createLabel("Mã Sản Phẩm:"));
        txtId = createTextField();
        formPanel.add(txtId);
        formPanel.add(Box.createVerticalStrut(10));
        
        // Tên
        formPanel.add(createLabel("Tên Sản Phẩm:"));
        txtName = createTextField();
        formPanel.add(txtName);
        formPanel.add(Box.createVerticalStrut(10));
        
        // Loại
        formPanel.add(createLabel("Loại Quần Áo:"));
        txtCategory = createTextField();
        formPanel.add(txtCategory);
        formPanel.add(Box.createVerticalStrut(10));
        
        // Giá
        formPanel.add(createLabel("Giá:"));
        txtPrice = createTextField();
        formPanel.add(txtPrice);
        formPanel.add(Box.createVerticalStrut(10));
        
        // Số lượng
        formPanel.add(createLabel("Số Lượng:"));
        txtQuantity = createTextField();
        formPanel.add(txtQuantity);
        formPanel.add(Box.createVerticalStrut(20));
        
        // Buttons
        JPanel btnPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.setMaximumSize(new Dimension(350, 100));
        
        JButton btnAdd = createButton("Thêm", "add");
        JButton btnUpdate = createButton("Sửa", "update");
        JButton btnDelete = createButton("Xóa", "delete");
        JButton btnClear = createButton("Làm Mới", "clear");
        
        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnClear);
        
        formPanel.add(btnPanel);
        
        return formPanel;
    }
    
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout(10, 10));
        tablePanel.setBackground(LIGHT_BROWN);
        
        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchPanel.setBackground(LIGHT_BROWN);
        
        JLabel lblSearch = new JLabel("Tìm kiếm theo loại:");
        lblSearch.setFont(new Font("Arial", Font.PLAIN, 14));
        
        txtSearch = new JTextField(20);
        txtSearch.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JButton btnSearch = createButton("Tìm Kiếm", "search");
        JButton btnShowAll = createButton("Hiển Thị Tất Cả", "showall");
        
        searchPanel.add(lblSearch);
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnShowAll);
        
        // Table
        String[] columnNames = {"Mã SP", "Tên Sản Phẩm", "Loại", "Giá", "Số Lượng"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        productTable = new JTable(tableModel);
        productTable.setFont(new Font("Arial", Font.PLAIN, 14));
        productTable.setRowHeight(30);
        productTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        productTable.getTableHeader().setBackground(BROWN_HEADER);
        productTable.getTableHeader().setForeground(Color.WHITE);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Khi click vào row, hiển thị thông tin lên form
        productTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && productTable.getSelectedRow() != -1) {
                int row = productTable.getSelectedRow();
                txtId.setText(tableModel.getValueAt(row, 0).toString());
                txtName.setText(tableModel.getValueAt(row, 1).toString());
                txtCategory.setText(tableModel.getValueAt(row, 2).toString());
                txtPrice.setText(tableModel.getValueAt(row, 3).toString());
                txtQuantity.setText(tableModel.getValueAt(row, 4).toString());
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBackground(Color.WHITE);
        
        tablePanel.add(searchPanel, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        return tablePanel;
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }
    
    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        return textField;
    }
    
    private JButton createButton(String text, String action) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBackground(BUTTON_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        switch (action) {
            case "add" -> btn.addActionListener(e -> addProduct());
            case "update" -> btn.addActionListener(e -> updateProduct());
            case "delete" -> btn.addActionListener(e -> deleteProduct());
            case "clear" -> btn.addActionListener(e -> clearForm());
            case "search" -> btn.addActionListener(e -> searchByCategory());
            case "showall" -> btn.addActionListener(e -> loadProducts());
        }
        
        return btn;
    }
    
    private void addProduct() {
        try {
            String id = txtId.getText().trim();
            String name = txtName.getText().trim();
            String category = txtCategory.getText().trim();
            double price = Double.parseDouble(txtPrice.getText().trim());
            int quantity = Integer.parseInt(txtQuantity.getText().trim());
            
            if (id.isEmpty() || name.isEmpty() || category.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Product product = new Product(id, name, category, price, quantity);
            controller.addProduct(product);
            
            JOptionPane.showMessageDialog(this, "✔ Đã thêm sản phẩm thành công!", 
                "Thành Công", JOptionPane.INFORMATION_MESSAGE);
            
            clearForm();
            loadProducts();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Giá và số lượng phải là số!", 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateProduct() {
        try {
            String id = txtId.getText().trim();
            String name = txtName.getText().trim();
            String category = txtCategory.getText().trim();
            double price = Double.parseDouble(txtPrice.getText().trim());
            int quantity = Integer.parseInt(txtQuantity.getText().trim());
            
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần sửa!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            controller.updateProduct(id, name, category, price, quantity);
            
            JOptionPane.showMessageDialog(this, "✔ Đã cập nhật sản phẩm!", 
                "Thành Công", JOptionPane.INFORMATION_MESSAGE);
            
            clearForm();
            loadProducts();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Giá và số lượng phải là số!", 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteProduct() {
        String id = txtId.getText().trim();
        
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa!", 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa sản phẩm này?", 
            "Xác Nhận", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            controller.deleteProduct(id);
            JOptionPane.showMessageDialog(this, "✔ Đã xóa sản phẩm!", 
                "Thành Công", JOptionPane.INFORMATION_MESSAGE);
            
            clearForm();
            loadProducts();
        }
    }
    
    private void searchByCategory() {
        String category = txtSearch.getText().trim();
        
        if (category.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập loại quần áo cần tìm!", 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        tableModel.setRowCount(0);
        controller.filterByCategory(category).forEach(product -> {
            tableModel.addRow(new Object[] {
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getQuantity()
            });
        });
    }
    
    private void loadProducts() {
        tableModel.setRowCount(0);
        controller.getAllProducts().forEach(product -> {
            tableModel.addRow(new Object[] {
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getQuantity()
            });
        });
    }
    
    private void clearForm() {
        txtId.setText("");
        txtName.setText("");
        txtCategory.setText("");
        txtPrice.setText("");
        txtQuantity.setText("");
        txtSearch.setText("");
        productTable.clearSelection();
    }
}