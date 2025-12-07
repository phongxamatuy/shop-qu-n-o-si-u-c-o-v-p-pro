package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import model.Customer;
import controller.CustomerController;
import java.util.ArrayList;

public class CustomerManagementView extends JFrame {
    private CustomerController controller;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtId, txtName, txtPhone, txtEmail;
    
    public CustomerManagementView() {
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Quản Lý Khách Hàng");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);
        
        JPanel formPanel = createFormPanel();
        JPanel tablePanel = createTablePanel();
        JPanel buttonPanel = createButtonPanel();
        
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // Khởi tạo controller SAU khi UI tạo xong
        this.controller = new CustomerController(this);
    }
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Thông Tin Khách Hàng"));
        panel.setBackground(Color.WHITE);
        
        panel.add(new JLabel("ID:"));
        txtId = new JTextField();
        panel.add(txtId);
        
        panel.add(new JLabel("Tên:"));
        txtName = new JTextField();
        panel.add(txtName);
        
        panel.add(new JLabel("Số Điện Thoại:"));
        txtPhone = new JTextField();
        panel.add(txtPhone);
        
        panel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panel.add(txtEmail);
        
        return panel;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Danh Sách Khách Hàng"));
        panel.setBackground(Color.WHITE);
        
        String[] columnNames = {"ID", "Tên", "Số Điện Thoại", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(25);
        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                txtId.setText(tableModel.getValueAt(selectedRow, 0).toString());
                txtName.setText(tableModel.getValueAt(selectedRow, 1).toString());
                txtPhone.setText(tableModel.getValueAt(selectedRow, 2).toString());
                txtEmail.setText(tableModel.getValueAt(selectedRow, 3).toString());
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(Color.WHITE);
        
        JButton btnAdd = new JButton("Thêm");
        btnAdd.addActionListener(e -> handleAdd());
        panel.add(btnAdd);
        
        JButton btnUpdate = new JButton("Sửa");
        btnUpdate.addActionListener(e -> handleUpdate());
        panel.add(btnUpdate);
        
        JButton btnDelete = new JButton("Xóa");
        btnDelete.addActionListener(e -> handleDelete());
        panel.add(btnDelete);
        
        JButton btnRefresh = new JButton("Làm mới");
        btnRefresh.addActionListener(e -> refreshTable());
        panel.add(btnRefresh);
        
        JButton btnClear = new JButton("Xóa Form");
        btnClear.addActionListener(e -> clearForm());
        panel.add(btnClear);
        
        return panel;
    }
    
    private void handleAdd() {
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();
        
        if (id.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "ID và Tên không được để trống!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Kiểm tra SDT chỉ là số
        if (!phone.isEmpty() && !phone.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, 
                "Số điện thoại chỉ được chứa các chữ số!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Kiểm tra Email hợp lệ
        if (!email.isEmpty() && !email.contains("@")) {
            JOptionPane.showMessageDialog(this, 
                "Email không hợp lệ!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        boolean ok = controller.add(new Customer(id, name, phone, email));
        if (ok) {
            JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
            clearForm();
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Lỗi: ID đã tồn tại, hoặc Số điện thoại/Email đã được sử dụng!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleUpdate() {
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();
        
        if (id.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "ID và Tên không được để trống!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Kiểm tra SDT chỉ là số
        if (!phone.isEmpty() && !phone.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, 
                "Số điện thoại chỉ được chứa các chữ số!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Kiểm tra Email hợp lệ
        if (!email.isEmpty() && !email.contains("@")) {
            JOptionPane.showMessageDialog(this, 
                "Email không hợp lệ!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (controller.update(new Customer(id, name, phone, email))) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            clearForm();
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Lỗi: Khách hàng không tồn tại, hoặc Số điện thoại/Email đã được sử dụng!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleDelete() {
        String id = txtId.getText().trim();
        
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn khách hàng để xóa!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa?", 
            "Xác nhận", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (controller.delete(id)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                clearForm();
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Khách hàng không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void refreshTable() {
        if (controller == null) {
            return;
        }
        tableModel.setRowCount(0);
        ArrayList<Customer> customers = controller.getAll();
        for (Customer c : customers) {
            tableModel.addRow(new Object[]{c.getId(), c.getName(), c.getPhone(), c.getEmail()});
        }
    }
    
    private void clearForm() {
        txtId.setText("");
        txtName.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
    }
}