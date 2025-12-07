package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class WarehouseManagementView extends JFrame {
    private JTable warehouseTable;
    private DefaultTableModel tableModel;
    private JTextField txtProductId, txtProductName, txtCategory, txtQuantity, txtMinStock, txtLocation;
    private JComboBox<String> cmbWarehouse;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear, btnSearch, btnImport, btnExport;
    private JTextField txtSearch;
    private JLabel lblTotalProducts, lblLowStock;
    
    public WarehouseManagementView() {
        setTitle("Quản Lý Kho Hàng");
        setSize(1100, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Panel thống kê
        JPanel statsPanel = createStatsPanel();
        add(statsPanel, BorderLayout.NORTH);
        
        // Panel chính
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        
        // Panel nhập liệu
        JPanel inputPanel = createInputPanel();
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        
        // Panel bảng dữ liệu
        JPanel tablePanel = createTablePanel();
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Panel nút chức năng
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Thống Kê"));
        
        lblTotalProducts = new JLabel("Tổng sản phẩm: 0");
        lblTotalProducts.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotalProducts.setForeground(new Color(0, 120, 215));
        
        lblLowStock = new JLabel("Sắp hết hàng: 0");
        lblLowStock.setFont(new Font("Arial", Font.BOLD, 14));
        lblLowStock.setForeground(new Color(255, 69, 0));
        
        panel.add(lblTotalProducts);
        panel.add(new JSeparator(SwingConstants.VERTICAL));
        panel.add(lblLowStock);
        
        return panel;
    }
    
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Thông Tin Sản Phẩm"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Mã sản phẩm
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Mã Sản Phẩm:"), gbc);
        gbc.gridx = 1;
        txtProductId = new JTextField(15);
        panel.add(txtProductId, gbc);
        
        // Tên sản phẩm
        gbc.gridx = 2; gbc.gridy = 0;
        panel.add(new JLabel("Tên Sản Phẩm:"), gbc);
        gbc.gridx = 3;
        txtProductName = new JTextField(15);
        panel.add(txtProductName, gbc);
        
        // Danh mục
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Danh Mục:"), gbc);
        gbc.gridx = 1;
        txtCategory = new JTextField(15);
        panel.add(txtCategory, gbc);
        
        // Số lượng
        gbc.gridx = 2; gbc.gridy = 1;
        panel.add(new JLabel("Số Lượng:"), gbc);
        gbc.gridx = 3;
        txtQuantity = new JTextField(15);
        panel.add(txtQuantity, gbc);
        
        // Tồn kho tối thiểu
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Tồn Kho Tối Thiểu:"), gbc);
        gbc.gridx = 1;
        txtMinStock = new JTextField(15);
        panel.add(txtMinStock, gbc);
        
        // Vị trí
        gbc.gridx = 2; gbc.gridy = 2;
        panel.add(new JLabel("Vị Trí:"), gbc);
        gbc.gridx = 3;
        txtLocation = new JTextField(15);
        panel.add(txtLocation, gbc);
        
        // Kho hàng
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Kho Hàng:"), gbc);
        gbc.gridx = 1;
        String[] warehouses = {"Kho Chính", "Kho Phụ 1", "Kho Phụ 2", "Kho Miền Nam", "Kho Miền Bắc"};
        cmbWarehouse = new JComboBox<>(warehouses);
        panel.add(cmbWarehouse, gbc);
        
        return panel;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Danh Sách Sản Phẩm Trong Kho"));
        
        // Tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Tìm kiếm:"));
        txtSearch = new JTextField(20);
        searchPanel.add(txtSearch);
        btnSearch = new JButton("Tìm");
        searchPanel.add(btnSearch);
        
        JButton btnShowLowStock = new JButton("Hiển thị hàng sắp hết");
        searchPanel.add(btnShowLowStock);
        
        panel.add(searchPanel, BorderLayout.NORTH);
        
        // Bảng
        String[] columns = {"Mã SP", "Tên Sản Phẩm", "Danh Mục", "Số Lượng", "Tồn Tối Thiểu", "Vị Trí", "Kho", "Trạng Thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        warehouseTable = new JTable(tableModel);
        warehouseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        warehouseTable.getTableHeader().setReorderingAllowed(false);
        
        // Màu sắc cho trạng thái
        warehouseTable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    String status = tableModel.getValueAt(row, 7).toString();
                    if (status.equals("Hết hàng")) {
                        c.setBackground(new Color(255, 200, 200));
                    } else if (status.equals("Sắp hết")) {
                        c.setBackground(new Color(255, 255, 200));
                    } else {
                        c.setBackground(Color.WHITE);
                    }
                }
                return c;
            }
        });
        
        // Sự kiện click vào bảng
        warehouseTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = warehouseTable.getSelectedRow();
                if (row >= 0) {
                    txtProductId.setText(tableModel.getValueAt(row, 0).toString());
                    txtProductName.setText(tableModel.getValueAt(row, 1).toString());
                    txtCategory.setText(tableModel.getValueAt(row, 2).toString());
                    txtQuantity.setText(tableModel.getValueAt(row, 3).toString());
                    txtMinStock.setText(tableModel.getValueAt(row, 4).toString());
                    txtLocation.setText(tableModel.getValueAt(row, 5).toString());
                    cmbWarehouse.setSelectedItem(tableModel.getValueAt(row, 6).toString());
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(warehouseTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnAdd = new JButton("Thêm");
        btnUpdate = new JButton("Cập Nhật");
        btnDelete = new JButton("Xóa");
        btnClear = new JButton("Làm Mới");
        btnImport = new JButton("Nhập Kho");
        btnExport = new JButton("Xuất Kho");
        
        Dimension btnSize = new Dimension(100, 30);
        btnAdd.setPreferredSize(btnSize);
        btnUpdate.setPreferredSize(btnSize);
        btnDelete.setPreferredSize(btnSize);
        btnClear.setPreferredSize(btnSize);
        btnImport.setPreferredSize(btnSize);
        btnExport.setPreferredSize(btnSize);
        
        panel.add(btnAdd);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnClear);
        panel.add(btnImport);
        panel.add(btnExport);
        
        // Sự kiện nút
        btnClear.addActionListener(e -> clearFields());
        
        return panel;
    }
    
    private void clearFields() {
        txtProductId.setText("");
        txtProductName.setText("");
        txtCategory.setText("");
        txtQuantity.setText("");
        txtMinStock.setText("");
        txtLocation.setText("");
        cmbWarehouse.setSelectedIndex(0);
        warehouseTable.clearSelection();
    }
    
    // Getters cho các components
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnUpdate() { return btnUpdate; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnSearch() { return btnSearch; }
    public JButton getBtnImport() { return btnImport; }
    public JButton getBtnExport() { return btnExport; }
    
    public JTextField getTxtProductId() { return txtProductId; }
    public JTextField getTxtProductName() { return txtProductName; }
    public JTextField getTxtCategory() { return txtCategory; }
    public JTextField getTxtQuantity() { return txtQuantity; }
    public JTextField getTxtMinStock() { return txtMinStock; }
    public JTextField getTxtLocation() { return txtLocation; }
    public JTextField getTxtSearch() { return txtSearch; }
    public JComboBox<String> getCmbWarehouse() { return cmbWarehouse; }
    
    public JTable getWarehouseTable() { return warehouseTable; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JLabel getLblTotalProducts() { return lblTotalProducts; }
    public JLabel getLblLowStock() { return lblLowStock; }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new WarehouseManagementView().setVisible(true);
        });
    }
}