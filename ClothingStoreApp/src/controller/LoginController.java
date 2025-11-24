package controller;

import view.LoginView;
import view.MainView;
import view.RegisterView;
import database.UserDatabase;
import model.User;



public class LoginController {
    private LoginView view;
    
    public LoginController(LoginView view) {
        this.view = view;
    }
    public void handleRegister(String username, String password, String confirmPassword) {
    if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
        view.showMessage("Không được để trống!");
        return;
    }
    if (!password.equals(confirmPassword)) {
        view.showMessage("Mật khẩu không trùng khớp!");
        return;
    }
    view.showMessage("Đăng ký thành công!");
    view.dispose();
    new LoginView().setVisible(true);
}

    
    public void handleLogin(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            view.showMessage("Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        
        User user = UserDatabase.findUser(username);
        
        if (user != null && user.getPassword().equals(password)) {
            view.dispose();
            MainView mainView = new MainView(username,"admin");
            mainView.setVisible(true);
        } else {
            view.showMessage("Sai tên đăng nhập hoặc mật khẩu!");
        }
    }
    
    public void openRegister() {
        RegisterView registerView = new RegisterView();
        registerView.setVisible(true);
    }

}
