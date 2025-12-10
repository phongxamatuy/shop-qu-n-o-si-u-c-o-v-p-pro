package view;

import controller.LoginController;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * LoginView - Giao diện đăng nhập
 * Truyền username tới MainView để lưu data riêng per user
 */
public class LoginView extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JTabbedPane tabbedPane;
    private LoginController controller;
    private JLabel loadingLabel;
    
    // Màu sắc
    private static final Color BROWN_HEADER = new Color(139, 90, 60);
    private static final Color LIGHT_BROWN = new Color(222, 204, 190);
    private static final Color DARKER_BROWN = new Color(160, 120, 90);
    private static final Color ACCENT_COLOR = new Color(184, 134, 100);
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
        
        // Main container với gradient background
        JPanel mainContainer = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth(), h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, LIGHT_BROWN, 0, h, WHITE);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        
        // Header
        JPanel headerPanel = createHeader();
        
        // Tabbed Pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 15));
        tabbedPane.setBackground(WHITE);
        tabbedPane.setForeground(DARKER_BROWN);
        
        // Tab Đăng Nhập
        JPanel loginPanel = createLoginPanel();
        tabbedPane.addTab("  ĐĂNG NHẬP  ", loginPanel);
        
        // Tab Đăng Ký
        JPanel registerPanel = createRegisterPanel();
        tabbedPane.addTab("  ĐĂNG KÝ  ", registerPanel);
        
        mainContainer.add(headerPanel, BorderLayout.NORTH);
        mainContainer.add(tabbedPane, BorderLayout.CENTER);
        
        // Loading label
        loadingLabel = new JLabel("", SwingConstants.CENTER);
        loadingLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        loadingLabel.setForeground(DARKER_BROWN);
        loadingLabel.setVisible(false);
        mainContainer.add(loadingLabel, BorderLayout.SOUTH);
        
        add(mainContainer);
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(20, 20, 15, 20));
        
        // Logo
        JLabel lblLogo = new JLabel("👕", SwingConstants.CENTER);
        lblLogo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        lblLogo.setForeground(BROWN_HEADER);
        
        JLabel lblTitle = new JLabel("FASHION STORE", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitle.setForeground(BROWN_HEADER);
        
        JLabel lblSubtitle = new JLabel("Hệ Thống Quản Lý Shop Quần Áo", SwingConstants.CENTER);
        lblSubtitle.setFont(new Font("Arial", Font.PLAIN, 11));
        lblSubtitle.setForeground(DARKER_BROWN);
        
        JPanel logoTitlePanel = new JPanel(new GridLayout(3, 1, 0, 8));
        logoTitlePanel.setOpaque(false);
        logoTitlePanel.add(lblLogo);
        logoTitlePanel.add(lblTitle);
        logoTitlePanel.add(lblSubtitle);
        
        header.add(logoTitlePanel, BorderLayout.CENTER);
        
        return header;
    }
    
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        
        // Icon và tiêu đề
        JLabel lblIcon = new JLabel("🔐", SwingConstants.CENTER);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblWelcome = new JLabel("Chào mừng trở lại!");
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 26));
        lblWelcome.setForeground(BROWN_HEADER);
        lblWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblSubtitle = new JLabel("Đăng nhập để tiếp tục quản lý");
        lblSubtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitle.setForeground(ACCENT_COLOR);
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(lblIcon);
        panel.add(Box.createVerticalStrut(12));
        panel.add(lblWelcome);
        panel.add(Box.createVerticalStrut(6));
        panel.add(lblSubtitle);
        panel.add(Box.createVerticalStrut(35));
        
        // Username
        txtUsername = new JTextField(20);
        JPanel usernamePanel = createInputPanel("👤", txtUsername, "Tên đăng nhập", false);
        panel.add(usernamePanel);
        panel.add(Box.createVerticalStrut(18));
        
        // Password
        txtPassword = new JPasswordField(20);
        JPanel passwordPanel = createInputPanel("🔒", txtPassword, "Mật khẩu", true);
        panel.add(passwordPanel);
        panel.add(Box.createVerticalStrut(30));
        
        // Button Đăng Nhập
        JButton btnLogin = createStyledButton("ĐĂNG NHẬP");
        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword());
            controller.handleLogin(username, password);
        });
        panel.add(btnLogin);
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }
    
    private JPanel createRegisterPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        
        // Icon và tiêu đề
        JLabel lblIcon = new JLabel("📝", SwingConstants.CENTER);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblTitle = new JLabel("Tạo tài khoản mới");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitle.setForeground(BROWN_HEADER);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblSubtitle = new JLabel("Đăng ký để bắt đầu sử dụng");
        lblSubtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitle.setForeground(ACCENT_COLOR);
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        contentPanel.add(lblIcon);
        contentPanel.add(Box.createVerticalStrut(12));
        contentPanel.add(lblTitle);
        contentPanel.add(Box.createVerticalStrut(6));
        contentPanel.add(lblSubtitle);
        contentPanel.add(Box.createVerticalStrut(35));
        
        // Username
        JTextField txtRegUsername = new JTextField(20);
        JPanel usernamePanel = createInputPanel("👤", txtRegUsername, "Tên đăng nhập", false);
        contentPanel.add(usernamePanel);
        contentPanel.add(Box.createVerticalStrut(18));
        
        // Password
        JPasswordField txtRegPassword = new JPasswordField(20);
        JPanel passwordPanel = createInputPanel("🔒", txtRegPassword, "Mật khẩu", true);
        contentPanel.add(passwordPanel);
        contentPanel.add(Box.createVerticalStrut(18));
        
        // Confirm Password
        JPasswordField txtConfirmPassword = new JPasswordField(20);
        JPanel confirmPanel = createInputPanel("🔑", txtConfirmPassword, "Xác nhận mật khẩu", true);
        contentPanel.add(confirmPanel);
        contentPanel.add(Box.createVerticalStrut(30));
        
        // Button Đăng Ký
        JButton btnRegister = createStyledButton("ĐĂNG KÝ");
        btnRegister.addActionListener(e -> {
            String username = txtRegUsername.getText().trim();
            String password = new String(txtRegPassword.getPassword());
            String confirmPassword = new String(txtConfirmPassword.getPassword());
            controller.handleRegister(username, password, confirmPassword);
        });
        contentPanel.add(btnRegister);
        contentPanel.add(Box.createVerticalGlue());
        
        // Thêm vào ScrollPane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBackground(WHITE);
        scrollPane.getViewport().setBackground(WHITE);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createInputPanel(String icon, JTextField textField, String placeholder, boolean isPassword) {
        JPanel panel = new JPanel(new BorderLayout(12, 0));
        panel.setMaximumSize(new Dimension(450, 55));
        panel.setBackground(LIGHT_BROWN);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR, 2),
            BorderFactory.createEmptyBorder(12, 18, 12, 18)
        ));
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));
        
        textField.setFont(new Font("Arial", Font.PLAIN, 15));
        textField.setBackground(LIGHT_BROWN);
        textField.setBorder(null);
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);
        
        // Placeholder effect
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                    if (isPassword) {
                        ((JPasswordField) textField).setEchoChar('●');
                    }
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                    if (isPassword) {
                        ((JPasswordField) textField).setEchoChar((char) 0);
                    }
                }
            }
        });
        
        if (isPassword) {
            ((JPasswordField) textField).setEchoChar((char) 0);
        }
        
        panel.add(iconLabel, BorderLayout.WEST);
        panel.add(textField, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(450, 55));
        button.setFont(new Font("Arial", Font.BOLD, 17));
        button.setBackground(DARKER_BROWN);
        button.setForeground(WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(BROWN_HEADER);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(DARKER_BROWN);
            }
        });
        
        return button;
    }
    
    public void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
    
    public void showMessage(String message) {
        showMessage(message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void switchToLoginTab() {
        tabbedPane.setSelectedIndex(0);
        txtUsername.setText("Tên đăng nhập");
        txtUsername.setForeground(Color.GRAY);
        txtPassword.setText("Mật khẩu");
        txtPassword.setForeground(Color.GRAY);
        ((JPasswordField) txtPassword).setEchoChar((char) 0);
    }
    
    public void showLoadingMessage(String message) {
        loadingLabel.setText(message);
        loadingLabel.setVisible(true);
    }
    
    public void hideLoadingMessage() {
        loadingLabel.setVisible(false);
    }
}