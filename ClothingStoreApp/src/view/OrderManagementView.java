package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class OrderManagementView extends JFrame {
    private JTable orderTable;
    private DefaultTableModel tableModel;
    private JTextField txtOrderId, txtCustomerName, txtProductName, txtQuantity, txtPrice;
    private JComboBox<String> cmbStatus;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear, btnSearch;
    private JTextField txtSearch;
    
    public OrderManagementView() {
        setTitle("Quản Lý Đơn Hàng");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Panel nhập liệu
        JPanel inputPanel = createInputPanel();
        add(inputPanel, BorderLayout.NORTH);
        
        // Panel bảng dữ liệu
        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);
        
        // Panel nút chức năng
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Thông Tin Đơn Hàng"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Mã đơn hàng
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Mã Đơn Hàng:"), gbc);
        gbc.gridx = 1;
        txtOrderId = new JTextField(15);
        panel.add(txtOrderId, gbc);
        
        // Tên khách hàng
        gbc.gridx = 2; gbc.gridy = 0;
        panel.add(new JLabel("Tên Khách Hàng:"), gbc);
        gbc.gridx = 3;
        txtCustomerName = new JTextField(15);
        panel.add(txtCustomerName, gbc);
        
        // Tên sản phẩm
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Tên Sản Phẩm:"), gbc);
        gbc.gridx = 1;
        txtProductName = new JTextField(15);
        panel.add(txtProductName, gbc);
        
        // Số lượng
        gbc.gridx = 2; gbc.gridy = 1;
        panel.add(new JLabel("Số Lượng:"), gbc);
        gbc.gridx = 3;
        txtQuantity = new JTextField(15);
        panel.add(txtQuantity, gbc);
        
        // Giá
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Giá:"), gbc);
        gbc.gridx = 1;
        txtPrice = new JTextField(15);
        panel.add(txtPrice, gbc);
        
        // Trạng thái
        gbc.gridx = 2; gbc.gridy = 2;
        panel.add(new JLabel("Trạng Thái:"), gbc);
        gbc.gridx = 3;
        String[] status = {"Chờ xử lý", "Đang giao", "Đã giao", "Đã hủy"};
        cmbStatus = new JComboBox<>(status);
        panel.add(cmbStatus, gbc);
        
        return panel;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Danh Sách Đơn Hàng"));
        
        // Tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Tìm kiếm:"));
        txtSearch = new JTextField(20);
        searchPanel.add(txtSearch);
        btnSearch = new JButton("Tìm");
        searchPanel.add(btnSearch);
        panel.add(searchPanel, BorderLayout.NORTH);
        
        // Bảng
        String[] columns = {"Mã ĐH", "Khách Hàng", "Sản Phẩm", "Số Lượng", "Giá", "Tổng Tiền", "Trạng Thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        orderTable = new JTable(tableModel);
        orderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        orderTable.getTableHeader().setReorderingAllowed(false);
        
        // Sự kiện click vào bảng
        orderTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = orderTable.getSelectedRow();
                if (row >= 0) {
                    txtOrderId.setText(tableModel.getValueAt(row, 0).toString());
                    txtCustomerName.setText(tableModel.getValueAt(row, 1).toString());
                    txtProductName.setText(tableModel.getValueAt(row, 2).toString());
                    txtQuantity.setText(tableModel.getValueAt(row, 3).toString());
                    txtPrice.setText(tableModel.getValueAt(row, 4).toString());
                    cmbStatus.setSelectedItem(tableModel.getValueAt(row, 6).toString());
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(orderTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnAdd = new JButton("Thêm");
        btnUpdate = new JButton("Cập Nhật");
        btnDelete = new JButton("Xóa");
        btnClear = new JButton("Làm Mới");
        
        btnAdd.setPreferredSize(new Dimension(100, 30));
        btnUpdate.setPreferredSize(new Dimension(100, 30));
        btnDelete.setPreferredSize(new Dimension(100, 30));
        btnClear.setPreferredSize(new Dimension(100, 30));
        
        panel.add(btnAdd);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnClear);
        
        // Sự kiện nút
        btnClear.addActionListener(e -> clearFields());
        
        return panel;
    }
    
    private void clearFields() {
        txtOrderId.setText("");
        txtCustomerName.setText("");
        txtProductName.setText("");
        txtQuantity.setText("");
        txtPrice.setText("");
        cmbStatus.setSelectedIndex(0);
        orderTable.clearSelection();
    }
    
    // Getters cho các components
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnUpdate() { return btnUpdate; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnSearch() { return btnSearch; }
    
    public JTextField getTxtOrderId() { return txtOrderId; }
    public JTextField getTxtCustomerName() { return txtCustomerName; }
    public JTextField getTxtProductName() { return txtProductName; }
    public JTextField getTxtQuantity() { return txtQuantity; }
    public JTextField getTxtPrice() { return txtPrice; }
    public JTextField getTxtSearch() { return txtSearch; }
    public JComboBox<String> getCmbStatus() { return cmbStatus; }
    
    public JTable getOrderTable() { return orderTable; }
    public DefaultTableModel getTableModel() { return tableModel; }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new OrderManagementView().setVisible(true);
        });
    }
}