package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * EmployeeManagementView - Giao diện quản lý nhân viên
 * Cung cấp form nhập liệu và bảng hiển thị danh sách nhân viên
 */
public class EmployeeManagementView extends JFrame {
    // Table
    public JTable ShowTable;
    public DefaultTableModel tableModel;
    
    // Form fields
    public JTextField txt_ID;
    public JTextField txt_fullName;
    public JTextField txt_Email;
    public JTextField txt_Gender;
    public JTextField txt_Address;
    public JTextField txt_PhoneNumber;
    
    // Buttons
    public JButton btn_Add;
    public JButton btn_Remove;
    public JButton btn_Update;
    public JButton btn_Show;
    
    public EmployeeManagementView() {
        initComponents();
    }
    
    /**
     * Khởi tạo các thành phần giao diện
     */
    private void initComponents() {
        setTitle("Quản Lý Nhân Viên");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        // Header panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Content panel
        JPanel contentPanel = createContentPanel();
        add(contentPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.EAST);
    }
    
    /**
     * Tạo header panel với tiêu đề
     */
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(142, 96, 51));
        panel.setPreferredSize(new Dimension(0, 80));
        
        JLabel lblTitle = new JLabel("QUẢN LÝ NHÂN VIÊN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        
        panel.add(lblTitle);
        return panel;
    }
    
    /**
     * Tạo content panel chứa form input và bảng
     */
    private JPanel createContentPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Form panel
        JPanel formPanel = createFormPanel();
        panel.add(formPanel, BorderLayout.NORTH);
        
        // Table panel
        JPanel tablePanel = createTablePanel();
        panel.add(tablePanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Tạo form input nhân viên
     */
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Thông Tin Nhân Viên"));
        
        // ID
        panel.add(new JLabel("ID:"));
        txt_ID = new JTextField();
        panel.add(txt_ID);
        
        // Full Name
        panel.add(new JLabel("Họ và tên:"));
        txt_fullName = new JTextField();
        panel.add(txt_fullName);
        
        // Email
        panel.add(new JLabel("Email:"));
        txt_Email = new JTextField();
        panel.add(txt_Email);
        
        // Gender
        panel.add(new JLabel("Giới tính:"));
        txt_Gender = new JTextField();
        panel.add(txt_Gender);
        
        // Address
        panel.add(new JLabel("Địa chỉ:"));
        txt_Address = new JTextField();
        panel.add(txt_Address);
        
        // Phone Number
        panel.add(new JLabel("Số điện thoại:"));
        txt_PhoneNumber = new JTextField();
        panel.add(txt_PhoneNumber);
        
        return panel;
    }
    
    /**
     * Tạo bảng hiển thị danh sách nhân viên
     */
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Danh Sách Nhân Viên"));
        
        // Tạo model bảng
        String[] columns = {"ID", "Họ tên", "Email", "Số điện thoại", "Giới tính", "Địa chỉ"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        ShowTable = new JTable(tableModel);
        ShowTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ShowTable.setRowHeight(25);
        
        // Center align các cell
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        for (int i = 0; i < ShowTable.getColumnCount(); i++) {
            ShowTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        JScrollPane scrollPane = new JScrollPane(ShowTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Tạo button panel
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 50));
        panel.setBackground(Color.WHITE);
        
        btn_Add = new JButton("Thêm");
        btn_Remove = new JButton("Xóa");
        btn_Update = new JButton("Cập nhật");
        btn_Show = new JButton("Hiển thị");
        
        // Style buttons
        for (JButton btn : new JButton[]{btn_Add, btn_Remove, btn_Update, btn_Show}) {
            btn.setBackground(new Color(185, 162, 117));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
            btn.setPreferredSize(new Dimension(150, 50));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        
        panel.add(btn_Add);
        panel.add(btn_Remove);
        panel.add(btn_Update);
        panel.add(btn_Show);
        
        return panel;
    }
    
    /**
     * Hiển thị thông báo lỗi
     */
    public void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Hiển thị thông báo thành công
     */
    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
}