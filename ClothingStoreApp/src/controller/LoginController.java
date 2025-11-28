package controller;

import view.LoginView;
import view.MainView;
import database.UserDatabase;
import model.User;
import javax.swing.*;

public class LoginController {
    private LoginView view;
    
    public LoginController(LoginView view) {
        this.view = view;
    }
    
    // Xử lý đăng ký
    public void handleRegister(String username, String password, String confirmPassword) {
        // Validate input
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            view.showMessage("Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        
        // Validate username
        if (username.length() < 3) {
            view.showMessage("Tên đăng nhập phải có ít nhất 3 ký tự!");
            return;
        }
        
        if (username.contains(" ")) {
            view.showMessage("Tên đăng nhập không được chứa khoảng trắng!");
            return;
        }
        
        // Kiểm tra username đã tồn tại
        if (UserDatabase.userExists(username)) {
            view.showMessage("Tên đăng nhập đã tồn tại!\nVui lòng chọn tên khác.");
            return;
        }
        
        // Validate password
        if (password.length() < 6) {
            view.showMessage("Mật khẩu phải có ít nhất 6 ký tự!");
            return;
        }
        
        // Kiểm tra mật khẩu khớp
        if (!password.equals(confirmPassword)) {
            view.showMessage("Mật khẩu xác nhận không khớp!");
            return;
        }
        
        // Tạo user mới với role USER
        boolean success = UserDatabase.addUser(username, password, username, "USER");
        
        if (success) {
            view.showMessage("Đăng ký thành công!\nVui lòng đăng nhập để tiếp tục.");
            // Có thể chuyển sang tab đăng nhập nếu cần
        } else {
            view.showMessage("Đăng ký thất bại!\nVui lòng thử lại.");
        }
    }
    
    // Xử lý đăng nhập
    public void handleLogin(String username, String password) {
        // Validate input
        if (username.isEmpty() || password.isEmpty()) {
            view.showMessage("Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        
        // Tìm user trong database
        User user = UserDatabase.findUser(username);
        
        if (user == null) {
            view.showMessage("Tài khoản không tồn tại!");
            return;
        }
        
        // Kiểm tra mật khẩu
        if (!user.getPassword().equals(password)) {
            view.showMessage("Mật khẩu không chính xác!");
            return;
        }
        
        // Kiểm tra tài khoản có bị khóa không
        if (!user.isActive()) {
            view.showMessage("Tài khoản đã bị khóa!\nVui lòng liên hệ quản trị viên.");
            return;
        }
        
        // Cập nhật thời gian đăng nhập
        user.updateLastLogin();
        UserDatabase.updateUser(user);
        
        // Đăng nhập thành công
        view.showMessage("Đăng nhập thành công!\nChào mừng " + user.getFullName());
        
        // Đóng màn hình đăng nhập
        view.dispose();
        
        // Mở màn hình chính
        openMainView(user);
    }
    
    // Mở màn hình chính
    private void openMainView(User user) {
        SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView(user.getFullName(), user.getRole());
            mainView.setVisible(true);
        });
    }
    
    // Getter cho view (nếu cần)
    public LoginView getView() {
        return view;
    }
}