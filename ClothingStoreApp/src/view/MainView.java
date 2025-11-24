package view;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private static final Color BROWN_HEADER = new Color(139, 90, 60);
    private static final Color LIGHT_BROWN = new Color(222, 204, 190);
    private static final Color SIDEBAR_COLOR = new Color(245, 240, 235);
    private static final Color CARD_COLOR = new Color(222, 204, 190);
    
    public MainView(String username, String role) {
        initComponents(username, role);
    }
    
    private void initComponents(String username, String role) {
        setTitle("Hệ Thống Quản Lý Shop Quần Áo");
        setSize(1400, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main container
        JPanel mainContainer = new JPanel(new BorderLayout());
        
        // Header
        JPanel headerPanel = createHeader(username);
        
        // Sidebar
        JPanel sidebarPanel = createSidebar();
        
        // Content (Dashboard)
        JPanel contentPanel = createDashboard();
        
        mainContainer.add(headerPanel, BorderLayout.NORTH);
        mainContainer.add(sidebarPanel, BorderLayout.WEST);
        mainContainer.add(contentPanel, BorderLayout.CENTER);
        
        add(mainContainer);
    }
    
    private JPanel createHeader(String username) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BROWN_HEADER);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        header.setPreferredSize(new Dimension(0, 80));
        
        // Logo và Title
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
        
        // User info và nút Đăng Xuất
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        rightPanel.setBackground(BROWN_HEADER);
        
        // Thời gian
        JLabel lblTime = new JLabel("22:36:22 - 24/11/2025");
        lblTime.setFont(new Font("Arial", Font.PLAIN, 12));
        lblTime.setForeground(Color.WHITE);
        
        // User icon
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
            dispose();
            new LoginView().setVisible(true);
        });
        
        rightPanel.add(lblTime);
        rightPanel.add(lblUserIcon);
        rightPanel.add(lblUsername);
        rightPanel.add(btnLogout);
        
        header.add(leftPanel, BorderLayout.WEST);
        header.add(rightPanel, BorderLayout.EAST);
        
        return header;
    }
    
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(SIDEBAR_COLOR);
        sidebar.setPreferredSize(new Dimension(280, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        String[] menuItems = {
            "📊 Tổng Quan",
            "👕 Quản Lý Sản Phẩm",
            "📦 Quản Lý Kho",
            "🛒 Bán Hàng",
            "📄 Hóa Đơn",
            "👥 Khách Hàng",
            "👤 Nhân Viên",
            "📈 Thống Kê"
        };
        
        for (int i = 0; i < menuItems.length; i++) {
            JButton menuBtn = createMenuButton(menuItems[i], i == 0);
            sidebar.add(menuBtn);
            sidebar.add(Box.createVerticalStrut(5));
        }
        
        // Phiên bản ở dưới cùng
        sidebar.add(Box.createVerticalGlue());
        JLabel lblVersion = new JLabel("Version 1.0.0");
        lblVersion.setFont(new Font("Arial", Font.ITALIC, 10));
        lblVersion.setForeground(Color.GRAY);
        lblVersion.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        sidebar.add(lblVersion);
        
        return sidebar;
    }
    
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
    
    private JPanel createDashboard() {
        JPanel dashboard = new JPanel(new BorderLayout());
        dashboard.setBackground(LIGHT_BROWN);
        dashboard.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // Title
        JLabel lblTitle = new JLabel("☑ Tổng Quan Hệ Thống");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        // Cards Grid
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