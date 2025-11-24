package database;

import model.User;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserDatabase {
    private static final String FILE_PATH = "users.dat";
    
    // Lưu danh sách user vào file
    public static void saveUsers(List<User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.out.println("Lỗi khi lưu file: " + e.getMessage());
        }
    }
    
    // Đọc danh sách user từ file
    @SuppressWarnings("unchecked")
    public static List<User> loadUsers() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            // Tạo admin mặc định nếu file chưa tồn tại
            List<User> defaultUsers = new ArrayList<>();
            defaultUsers.add(new User(1, "admin", "admin", "admin"));
            saveUsers(defaultUsers);
            return defaultUsers;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    // Tìm user theo username
    public static User findUser(String username) {
        List<User> users = loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    
    // Thêm user mới
    public static boolean addUser(User newUser) {
        List<User> users = loadUsers();
        
        // Kiểm tra username đã tồn tại chưa
        for (User user : users) {
            if (user.getUsername().equals(newUser.getUsername())) {
                return false; // Username đã tồn tại
            }
        }
        
        users.add(newUser);
        saveUsers(users);
        return true;
    }
}
