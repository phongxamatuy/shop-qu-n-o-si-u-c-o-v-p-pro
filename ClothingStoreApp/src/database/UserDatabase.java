package database;

import model.User;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserDatabase {
    private static final String DATA_FILE = "users.dat";
    private static List<User> userList = new ArrayList<>();
    
    // Static block để load dữ liệu khi class được khởi tạo
    static {
        loadUsers();
        // Nếu chưa có dữ liệu, tạo tài khoản mặc định
        if (userList.isEmpty()) {
            initDefaultUsers();
        }
    }
    
    // Khởi tạo tài khoản mặc định
    private static void initDefaultUsers() {
        userList.add(new User("admin", "admin", "Administrator", "ADMIN"));
        userList.add(new User("user", "123456", "Nguyễn Văn A", "USER"));
        userList.add(new User("manager", "manager", "Trần Thị B", "MANAGER"));
        saveUsers();
    }
    
    // Lưu danh sách users vào file .dat
    public static boolean saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(userList);
            return true;
        } catch (IOException e) {
            System.err.println("Lỗi khi lưu dữ liệu: " + e.getMessage());
            return false;
        }
    }
    
    // Load danh sách users từ file .dat
    @SuppressWarnings("unchecked")
    public static boolean loadUsers() {
        File file = new File(DATA_FILE);
        
        // Nếu file chưa tồn tại, return false
        if (!file.exists()) {
            return false;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            userList = (List<User>) ois.readObject();
            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi đọc dữ liệu: " + e.getMessage());
            return false;
        }
    }
    
    // Tìm user theo username
    public static User findUser(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    
    // Kiểm tra username đã tồn tại chưa
    public static boolean userExists(String username) {
        return findUser(username) != null;
    }
    
    // Thêm user mới
    public static boolean addUser(User user) {
        if (userExists(user.getUsername())) {
            return false;
        }
        userList.add(user);
        return saveUsers();
    }
    
    // Thêm user mới (overload với parameters)
    public static boolean addUser(String username, String password, String fullName, String role) {
        User newUser = new User(username, password, fullName, role);
        return addUser(newUser);
    }
    
    // Cập nhật thông tin user
    public static boolean updateUser(User user) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUsername().equals(user.getUsername())) {
                userList.set(i, user);
                return saveUsers();
            }
        }
        return false;
    }
    
    // Xóa user
    public static boolean deleteUser(String username) {
        User user = findUser(username);
        if (user != null) {
            userList.remove(user);
            return saveUsers();
        }
        return false;
    }
    
    // Đổi mật khẩu
    public static boolean changePassword(String username, String oldPassword, String newPassword) {
        User user = findUser(username);
        if (user != null && user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            return updateUser(user);
        }
        return false;
    }
    
    // Lấy tất cả users
    public static List<User> getAllUsers() {
        return new ArrayList<>(userList);
    }
    
    // Đếm số lượng users
    public static int getUserCount() {
        return userList.size();
    }
    
    // Validate đăng nhập
    public static boolean validateLogin(String username, String password) {
        User user = findUser(username);
        return user != null && user.getPassword().equals(password) && user.isActive();
    }
    
    // Lấy user theo role
    public static List<User> getUsersByRole(String role) {
        List<User> result = new ArrayList<>();
        for (User user : userList) {
            if (user.getRole().equalsIgnoreCase(role)) {
                result.add(user);
            }
        }
        return result;
    }
    
    // Kích hoạt/vô hiệu hóa user
    public static boolean toggleUserStatus(String username) {
        User user = findUser(username);
        if (user != null) {
            user.setActive(!user.isActive());
            return updateUser(user);
        }
        return false;
    }
    
    // Reset database (xóa file và tạo lại)
    public static boolean resetDatabase() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            file.delete();
        }
        userList.clear();
        initDefaultUsers();
        return true;
    }
    
    // Kiểm tra file tồn tại
    public static boolean databaseExists() {
        return new File(DATA_FILE).exists();
    }
    
    // Backup database
    public static boolean backupDatabase(String backupFileName) {
        try {
            File source = new File(DATA_FILE);
            File backup = new File(backupFileName);
            
            try (FileInputStream fis = new FileInputStream(source);
                 FileOutputStream fos = new FileOutputStream(backup)) {
                
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("Lỗi khi backup: " + e.getMessage());
            return false;
        }
    }
    
    // Restore database từ backup
    public static boolean restoreDatabase(String backupFileName) {
        try {
            File backup = new File(backupFileName);
            File current = new File(DATA_FILE);
            
            if (!backup.exists()) {
                return false;
            }
            
            try (FileInputStream fis = new FileInputStream(backup);
                 FileOutputStream fos = new FileOutputStream(current)) {
                
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }
            }
            
            // Load lại dữ liệu
            return loadUsers();
        } catch (IOException e) {
            System.err.println("Lỗi khi restore: " + e.getMessage());
            return false;
        }
    }
    
    // In thông tin tất cả users (cho debug)
    public static void printAllUsers() {
        System.out.println("=== DANH SÁCH USERS ===");
        System.out.println("Tổng số: " + userList.size());
        for (User user : userList) {
            System.out.println(user);
        }
        System.out.println("======================");
    }
}