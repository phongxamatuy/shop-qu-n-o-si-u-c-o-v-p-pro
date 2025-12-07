package view;

import javax.swing.*;
import java.awt.*;
import controller.OrderController;
import controller.WarehouseController;
import controller.ProductController;
import controller.CustomerController;
import controller.EmployeeController;

public class MainView extends JFrame {
    // Màu sắc chính
    private static final Color BROWN_HEADER = new Color(139, 90, 60);
    private static final Color LIGHT_BROWN = new Color(222, 204, 190);
    private static final Color SIDEBAR_COLOR = new Color(245, 240, 235);
    private static final Color CARD_COLOR = new Color(222, 204, 190);
    
    private JPanel contentArea;
    private String username;
    private String role;
    
    public MainView(String username, String role) {
        this.username = username;
        this.role = role;
        initComponents(username, role);
    }
    
    private void initComponents(String username, String role) {
        setTitle("Hệ Thống Quản Lý Shop Quần Áo");
        setSize(1400, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainContainer = new JPanel(new BorderLayout());
        
        // Tạo các panel chính
        JPanel headerPanel = createHeader(username);
        JPanel sidebarPanel = createSidebar();
        contentArea = createDashboard();
        
        // Thêm các panel vào container
        mainContainer.add(headerPanel, BorderLayout.NORTH);
        mainContainer.add(sidebarPanel, BorderLayout.WEST);
        mainContainer.add(contentArea, BorderLayout.CENTER);
        
        add(mainContainer);
    }
    
    /**
     * Tạo header với logo, title và user info
     */
    private JPanel createHeader(String username) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BROWN_HEADER);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        header.setPreferredSize(new Dimension(0, 80));
        
        // Panel trái: Logo + Title
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        leftPanel.setBackground(BROWN_HEADER);
        
        JLabel lblLogo = new JLabel("👕");
        lblLogo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        
        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setBackground(BROWN_HEADER);
        
        JLabel lblTitle = new JLabel("FASHION STORE");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        
        JLabel lblSubtitle = new JLabel("Hệ Thống Quản Lý Shop Quần Áo");
        lblSubtitle.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSubtitle.setForeground(new Color(220, 220, 220));
        
        titlePanel.add(lblTitle);
        titlePanel.add(lblSubtitle);
        
        leftPanel.add(lblLogo);
        leftPanel.add(titlePanel);
        
        // Panel phải: Time + User info + Logout
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        rightPanel.setBackground(BROWN_HEADER);
        
        JLabel lblTime = new JLabel("22:36:22 - 24/11/2025");
        lblTime.setFont(new Font("Arial", Font.PLAIN, 12));
        lblTime.setForeground(Color.WHITE);
        
        JLabel lblUserIcon = new JLabel("👤");
        lblUserIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        
        JLabel lblUsername = new JLabel(username);
        lblUsername.setFont(new Font("Arial", Font.BOLD, 14));
        lblUsername.setForeground(Color.WHITE);
        
        JButton btnLogout = new JButton("Đăng Xuất");
        btnLogout.setFont(new Font("Arial", Font.PLAIN, 12));
        btnLogout.setBackground(Color.WHITE);
        btnLogout.setForeground(BROWN_HEADER);
        btnLogout.setFocusPainted(false);
        btnLogout.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn đăng xuất?",
                "Xác Nhận",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new LoginView().setVisible(true);
            }
        });
        
        rightPanel.add(lblTime);
        rightPanel.add(lblUserIcon);
        rightPanel.add(lblUsername);
        rightPanel.add(btnLogout);
        
        header.add(leftPanel, BorderLayout.WEST);
        header.add(rightPanel, BorderLayout.EAST);
        
        return header;
    }
    
    /**
     * Tạo sidebar menu
     */
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(SIDEBAR_COLOR);
        sidebar.setPreferredSize(new Dimension(280, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        String[] menuItems = {
            "Tổng Quan",
            "Quản Lý Sản Phẩm",
            "Quản Lý Kho",
            "Quản lý Đơn Hàng",
            "Quản lý Khách Hàng",
            "Quản lý Nhân Viên",
            "Thống Kê"
        };
        
        // Tạo button menu cho từng item
        for (int i = 0; i < menuItems.length; i++) {
            final int index = i;
            JButton menuBtn = createMenuButton(menuItems[i], i == 0);
            menuBtn.addActionListener(e -> handleMenuClick(index, menuItems[index]));
            sidebar.add(menuBtn);
            sidebar.add(Box.createVerticalStrut(5));
        }
        
        // Version ở cuối
        sidebar.add(Box.createVerticalGlue());
        JLabel lblVersion = new JLabel("Version 1.0.0");
        lblVersion.setFont(new Font("Arial", Font.ITALIC, 10));
        lblVersion.setForeground(Color.GRAY);
        lblVersion.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        sidebar.add(lblVersion);
        
        return sidebar;
    }
    
    /**
     * Tạo button menu với style
     */
    private JButton createMenuButton(String text, boolean selected) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        btn.setMaximumSize(new Dimension(280, 50));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        if (selected) {
            btn.setBackground(Color.WHITE);
            btn.setForeground(BROWN_HEADER);
        } else {
            btn.setBackground(SIDEBAR_COLOR);
            btn.setForeground(Color.BLACK);
            btn.setBorderPainted(false);
        }
        
        return btn;
    }
    
    /**
     * Xử lý click menu
     */
    private void handleMenuClick(int index, String menuName) {
        switch (index) {
            case 0: // Tổng Quan
                showDashboard();
                break;
            case 1: // Quản Lý Sản Phẩm
                openProductManagement();
                break;
            case 2: // Quản Lý Kho
                openWarehouseManagement();
                break;
            case 3: // Quản lý Đơn Hàng
                openOrderManagement();
                break;
            case 4: // Quản lý Khách Hàng
                openCustomerManagement();
                break;
            case 5: // Quản lý Nhân Viên
                openEmployeeManagement();
                break;
            case 6: // Thống Kê
                JOptionPane.showMessageDialog(this, 
                    "Chức năng đang phát triển", 
                    "Thông báo", 
                    JOptionPane.INFORMATION_MESSAGE);
                break;
        }
    }
    
    /**
     * Hiển thị dashboard
     */
    private void showDashboard() {
        contentArea.removeAll();
        JPanel dashboard = createDashboard();
        contentArea.setLayout(new BorderLayout());
        contentArea.add(dashboard, BorderLayout.CENTER);
        contentArea.revalidate();
        contentArea.repaint();
    }
    
    /**
     * Mở cửa sổ Quản Lý Sản Phẩm
     */
    private void openProductManagement() {
        ProductManagementView productView = new ProductManagementView();
        ProductController productController = new ProductController();
        productView.setVisible(true);
    }
    
    /**
     * Mở cửa sổ Quản Lý Đơn Hàng
     */
    private void openOrderManagement() {
        OrderManagementView orderView = new OrderManagementView();
        OrderController orderController = new OrderController(orderView);
        orderView.setVisible(true);
    }
    
    /**
     * Mở cửa sổ Quản Lý Kho
     */
    private void openWarehouseManagement() {
        WarehouseManagementView warehouseView = new WarehouseManagementView();
        WarehouseController warehouseController = new WarehouseController(warehouseView);
        warehouseView.setVisible(true);
    }
    
    /**
     * Mở cửa sổ Quản Lý Khách Hàng
     */
    private void openCustomerManagement() {
        try {
            CustomerManagementView customerView = new CustomerManagementView();
            customerView.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi: " + ex.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    /**
     * Mở cửa sổ Quản Lý Nhân Viên
     */
    private void openEmployeeManagement() {
        try {
            EmployeeManagementView employeeView = new EmployeeManagementView();
            EmployeeController employeeController = new EmployeeController(employeeView);
            employeeView.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi: " + ex.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    /**
     * Tạo dashboard với các card thống kê
     */
    private JPanel createDashboard() {
        JPanel dashboard = new JPanel(new BorderLayout());
        dashboard.setBackground(LIGHT_BROWN);
        dashboard.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JLabel lblTitle = new JLabel("☑ Tổng Quan Hệ Thống");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        JPanel cardsPanel = new JPanel(new GridLayout(2, 2, 30, 30));
        cardsPanel.setBackground(LIGHT_BROWN);
        
        cardsPanel.add(createStatCard("💰", "Doanh Thu Hôm Nay", "15,750,000đ"));
        cardsPanel.add(createStatCard("🛍", "Đơn Hàng", "45"));
        cardsPanel.add(createStatCard("👕", "Sản Phẩm", "1,234"));
        cardsPanel.add(createStatCard("👥", "Khách Hàng", "856"));
        
        dashboard.add(lblTitle, BorderLayout.NORTH);
        dashboard.add(cardsPanel, BorderLayout.CENTER);
        
        return dashboard;
    }
    
    /**
     * Tạo card thống kê
     */
    private JPanel createStatCard(String icon, String title, String value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 180, 160), 1),
            BorderFactory.createEmptyBorder(40, 30, 40, 30)
        ));
        
        JLabel lblIcon = new JLabel(icon);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
        lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Arial", Font.PLAIN, 16));
        lblTitle.setForeground(new Color(100, 80, 60));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Arial", Font.BOLD, 32));
        lblValue.setForeground(new Color(120, 80, 50));
        lblValue.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel contentPanel = new JPanel(new GridLayout(3, 1, 0, 15));
        contentPanel.setBackground(CARD_COLOR);
        contentPanel.add(lblTitle);
        contentPanel.add(lblIcon);
        contentPanel.add(lblValue);
        
        card.add(contentPanel, BorderLayout.CENTER);
        
        return card;
    }
}