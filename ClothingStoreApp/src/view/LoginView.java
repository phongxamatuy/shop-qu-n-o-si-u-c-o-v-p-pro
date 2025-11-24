package view;

import javax.swing.*;
import java.awt.*;
import controller.LoginController;



public class LoginView extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JTabbedPane tabbedPane;
    private LoginController controller;
    
    // Màu nâu giống ảnh
    private static final Color BROWN_HEADER = new Color(139, 90, 60);
    private static final Color LIGHT_BROWN = new Color(222, 204, 190);
    private static final Color DARKER_BROWN = new Color(160, 120, 90);
    private static final Color WHITE = Color.WHITE;
    
    public LoginView() {
        controller = new LoginController(this);
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Fashion Store - Đăng Nhập / Đăng Ký");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Main container
        JPanel mainContainer = new JPanel(new BorderLayout());
        
        // Header với logo và title
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BROWN_HEADER);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        
        // Logo (icon áo)
        JLabel lblLogo = new JLabel("👕", SwingConstants.CENTER);
        lblLogo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
        lblLogo.setForeground(WHITE);
        
        JLabel lblTitle = new JLabel("FASHION STORE", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitle.setForeground(WHITE);
        
        JPanel logoTitlePanel = new JPanel(new GridLayout(2, 1, 0, 10));
        logoTitlePanel.setBackground(BROWN_HEADER);
        logoTitlePanel.add(lblLogo);
        logoTitlePanel.add(lblTitle);
        
        headerPanel.add(logoTitlePanel, BorderLayout.CENTER);
        
        // Tabbed Pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        tabbedPane.setBackground(WHITE);
        
        // Tab Đăng Nhập
        JPanel loginPanel = createLoginPanel();
        tabbedPane.addTab("ĐĂNG NHẬP", loginPanel);
        
        // Tab Đăng Ký
        JPanel registerPanel = createRegisterPanel();
        tabbedPane.addTab("ĐĂNG KÝ", registerPanel);
        
        mainContainer.add(headerPanel, BorderLayout.NORTH);
        mainContainer.add(tabbedPane, BorderLayout.CENTER);
        
        add(mainContainer);
    }
    
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(50, 80, 50, 80));
        
        // Chào mừng
        JLabel lblWelcome = new JLabel("Chào mừng trở lại!");
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 26));
        lblWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblSubtitle = new JLabel("Đăng nhập để tiếp tục");
        lblSubtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitle.setForeground(DARKER_BROWN);
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(lblWelcome);
        panel.add(Box.createVerticalStrut(10));
        panel.add(lblSubtitle);
        panel.add(Box.createVerticalStrut(50));
        
        // Username
        JLabel lblUsername = new JLabel("Tên đăng nhập");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        lblUsername.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        txtUsername = new JTextField();
        txtUsername.setMaximumSize(new Dimension(400, 45));
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 16));
        txtUsername.setBackground(LIGHT_BROWN);
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(DARKER_BROWN, 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        panel.add(lblUsername);
        panel.add(Box.createVerticalStrut(10));
        panel.add(txtUsername);
        panel.add(Box.createVerticalStrut(25));
        
        // Password
        JLabel lblPassword = new JLabel("Mật khẩu");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        lblPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        txtPassword = new JPasswordField();
        txtPassword.setMaximumSize(new Dimension(400, 45));
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 16));
        txtPassword.setBackground(LIGHT_BROWN);
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(DARKER_BROWN, 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        panel.add(lblPassword);
        panel.add(Box.createVerticalStrut(10));
        panel.add(txtPassword);
        panel.add(Box.createVerticalStrut(40));
        
        // Button Đăng Nhập
        btnLogin = new JButton("ĐĂNG NHẬP");
        btnLogin.setMaximumSize(new Dimension(400, 50));
        btnLogin.setFont(new Font("Arial", Font.BOLD, 16));
        btnLogin.setBackground(DARKER_BROWN);
        btnLogin.setForeground(WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.addActionListener(e -> controller.handleLogin(
            txtUsername.getText(), 
            new String(txtPassword.getPassword())
        ));
        
        panel.add(btnLogin);
        panel.add(Box.createVerticalStrut(30));
        
        // Tài khoản mặc định
        JLabel lblDefaultAccount = new JLabel("Tài khoản mặc định: admin / admin");
        lblDefaultAccount.setFont(new Font("Arial", Font.ITALIC, 12));
        lblDefaultAccount.setForeground(DARKER_BROWN);
        lblDefaultAccount.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblDefaultAccount);
        
        return panel;
    }
    
    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(50, 80, 50, 80));
        
        // Tiêu đề
        JLabel lblTitle = new JLabel("Tạo tài khoản mới");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblSubtitle = new JLabel("Đăng ký để bắt đầu");
        lblSubtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitle.setForeground(DARKER_BROWN);
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(lblTitle);
        panel.add(Box.createVerticalStrut(10));
        panel.add(lblSubtitle);
        panel.add(Box.createVerticalStrut(40));
        
        // Username
        JLabel lblUsername = new JLabel("Tên đăng nhập");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        lblUsername.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JTextField txtRegUsername = new JTextField();
        txtRegUsername.setMaximumSize(new Dimension(400, 45));
        txtRegUsername.setFont(new Font("Arial", Font.PLAIN, 16));
        txtRegUsername.setBackground(LIGHT_BROWN);
        txtRegUsername.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(DARKER_BROWN, 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        panel.add(lblUsername);
        panel.add(Box.createVerticalStrut(10));
        panel.add(txtRegUsername);
        panel.add(Box.createVerticalStrut(20));
        
        // Password
        JLabel lblPassword = new JLabel("Mật khẩu");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        lblPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPasswordField txtRegPassword = new JPasswordField();
        txtRegPassword.setMaximumSize(new Dimension(400, 45));
        txtRegPassword.setFont(new Font("Arial", Font.PLAIN, 16));
        txtRegPassword.setBackground(LIGHT_BROWN);
        txtRegPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(DARKER_BROWN, 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        panel.add(lblPassword);
        panel.add(Box.createVerticalStrut(10));
        panel.add(txtRegPassword);
        panel.add(Box.createVerticalStrut(20));
        
        // Confirm Password
        JLabel lblConfirm = new JLabel("Xác nhận mật khẩu");
        lblConfirm.setFont(new Font("Arial", Font.PLAIN, 14));
        lblConfirm.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPasswordField txtConfirmPassword = new JPasswordField();
        txtConfirmPassword.setMaximumSize(new Dimension(400, 45));
        txtConfirmPassword.setFont(new Font("Arial", Font.PLAIN, 16));
        txtConfirmPassword.setBackground(LIGHT_BROWN);
        txtConfirmPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(DARKER_BROWN, 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        panel.add(lblConfirm);
        panel.add(Box.createVerticalStrut(10));
        panel.add(txtConfirmPassword);
        panel.add(Box.createVerticalStrut(40));
        
        // Button Đăng Ký
        JButton btnRegister = new JButton("ĐĂNG KÝ");
        btnRegister.setMaximumSize(new Dimension(400, 50));
        btnRegister.setFont(new Font("Arial", Font.BOLD, 16));
        btnRegister.setBackground(DARKER_BROWN);
        btnRegister.setForeground(WHITE);
        btnRegister.setFocusPainted(false);
        btnRegister.setBorderPainted(false);
        btnRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegister.addActionListener(e -> controller.handleRegister(
            txtRegUsername.getText(),
            new String(txtRegPassword.getPassword()),
            new String(txtConfirmPassword.getPassword())
        ));
        
        panel.add(btnRegister);
        
        return panel;
    }
    
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
