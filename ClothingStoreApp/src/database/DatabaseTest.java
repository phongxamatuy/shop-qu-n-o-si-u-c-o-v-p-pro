package database;

import database.UserDatabase;
import model.User;
import java.util.List;

/**
 * Class để test các chức năng của UserDatabase
 */
public class DatabaseTest {
    
    public static void main(String[] args) {
        System.out.println("=== BẮT ĐẦU TEST USER DATABASE ===\n");
        
        // Test 1: Kiểm tra file tồn tại
        testDatabaseExists();
        
        // Test 2: Load và hiển thị tất cả users
        testLoadUsers();
        
        // Test 3: Tìm user
        testFindUser();
        
        // Test 4: Thêm user mới
        testAddUser();
        
        // Test 5: Cập nhật user
        testUpdateUser();
        
        // Test 6: Đổi mật khẩu
        testChangePassword();
        
        // Test 7: Validate đăng nhập
        testValidateLogin();
        
        // Test 8: Lấy users theo role
        testGetUsersByRole();
        
        // Test 9: Backup và restore
        testBackupRestore();
        
        System.out.println("\n=== KẾT THÚC TEST ===");
    }
    
    private static void testDatabaseExists() {
        System.out.println("TEST 1: Kiểm tra file database");
        boolean exists = UserDatabase.databaseExists();
        System.out.println("File users.dat tồn tại: " + exists);
        System.out.println("Số lượng users: " + UserDatabase.getUserCount());
        System.out.println();
    }
    
    private static void testLoadUsers() {
        System.out.println("TEST 2: Load và hiển thị tất cả users");
        UserDatabase.printAllUsers();
        System.out.println();
    }
    
    private static void testFindUser() {
        System.out.println("TEST 3: Tìm user");
        
        // Tìm user tồn tại
        User admin = UserDatabase.findUser("admin");
        if (admin != null) {
            System.out.println("✓ Tìm thấy admin: " + admin.getFullName());
        } else {
            System.out.println("✗ Không tìm thấy admin");
        }
        
        // Tìm user không tồn tại
        User notFound = UserDatabase.findUser("khongtontai");
        if (notFound == null) {
            System.out.println("✓ Không tìm thấy user 'khongtontai' (đúng)");
        }
        System.out.println();
    }
    
    private static void testAddUser() {
        System.out.println("TEST 4: Thêm user mới");
        
        String testUsername = "testuser";
        
        // Xóa nếu đã tồn tại
        if (UserDatabase.userExists(testUsername)) {
            UserDatabase.deleteUser(testUsername);
        }
        
        // Thêm user mới
        boolean added = UserDatabase.addUser(testUsername, "password123", "Test User", "USER");
        if (added) {
            System.out.println("✓ Thêm user thành công");
            User newUser = UserDatabase.findUser(testUsername);
            System.out.println("  Username: " + newUser.getUsername());
            System.out.println("  Full Name: " + newUser.getFullName());
            System.out.println("  Role: " + newUser.getRole());
        } else {
            System.out.println("✗ Thêm user thất bại");
        }
        
        // Thử thêm user trùng
        boolean duplicated = UserDatabase.addUser(testUsername, "pass", "Test", "USER");
        if (!duplicated) {
            System.out.println("✓ Không thể thêm user trùng (đúng)");
        }
        System.out.println();
    }
    
    private static void testUpdateUser() {
        System.out.println("TEST 5: Cập nhật user");
        
        User user = UserDatabase.findUser("testuser");
        if (user != null) {
            String oldName = user.getFullName();
            user.setFullName("Updated Test User");
            
            boolean updated = UserDatabase.updateUser(user);
            if (updated) {
                System.out.println("✓ Cập nhật thành công");
                System.out.println("  Tên cũ: " + oldName);
                System.out.println("  Tên mới: " + user.getFullName());
            } else {
                System.out.println("✗ Cập nhật thất bại");
            }
        }
        System.out.println();
    }
    
    private static void testChangePassword() {
        System.out.println("TEST 6: Đổi mật khẩu");
        
        // Đổi mật khẩu đúng
        boolean changed = UserDatabase.changePassword("testuser", "password123", "newpassword");
        if (changed) {
            System.out.println("✓ Đổi mật khẩu thành công");
        } else {
            System.out.println("✗ Đổi mật khẩu thất bại");
        }
        
        // Đổi mật khẩu sai mật khẩu cũ
        boolean wrongOld = UserDatabase.changePassword("testuser", "wrongpassword", "anotherpass");
        if (!wrongOld) {
            System.out.println("✓ Không thể đổi với mật khẩu cũ sai (đúng)");
        }
        System.out.println();
    }
    
    private static void testValidateLogin() {
        System.out.println("TEST 7: Validate đăng nhập");
        
        // Đăng nhập đúng
        boolean validLogin = UserDatabase.validateLogin("admin", "admin");
        System.out.println("✓ admin/admin: " + (validLogin ? "Hợp lệ" : "Không hợp lệ"));
        
        // Đăng nhập sai
        boolean invalidLogin = UserDatabase.validateLogin("admin", "wrongpass");
        System.out.println("✓ admin/wrongpass: " + (invalidLogin ? "Hợp lệ" : "Không hợp lệ (đúng)"));
        
        // User không tồn tại
        boolean notExist = UserDatabase.validateLogin("notexist", "pass");
        System.out.println("✓ notexist/pass: " + (notExist ? "Hợp lệ" : "Không hợp lệ (đúng)"));
        System.out.println();
    }
    
    private static void testGetUsersByRole() {
        System.out.println("TEST 8: Lấy users theo role");
        
        List<User> admins = UserDatabase.getUsersByRole("ADMIN");
        System.out.println("Số lượng ADMIN: " + admins.size());
        for (User admin : admins) {
            System.out.println("  - " + admin.getUsername() + " (" + admin.getFullName() + ")");
        }
        
        List<User> users = UserDatabase.getUsersByRole("USER");
        System.out.println("Số lượng USER: " + users.size());
        for (User user : users) {
            System.out.println("  - " + user.getUsername() + " (" + user.getFullName() + ")");
        }
        System.out.println();
    }
    
    private static void testBackupRestore() {
        System.out.println("TEST 9: Backup và Restore");
        
        // Backup
        boolean backed = UserDatabase.backupDatabase("users_backup.dat");
        if (backed) {
            System.out.println("✓ Backup thành công vào users_backup.dat");
        } else {
            System.out.println("✗ Backup thất bại");
        }
        
        // Restore (test chỉ kiểm tra, không thực sự restore)
        System.out.println("  (Bỏ qua test restore để không mất dữ liệu)");
        System.out.println();
    }
    
    // Phương thức helper để reset database về mặc định
    public static void resetToDefault() {
        System.out.println("RESET: Khôi phục database về mặc định");
        UserDatabase.resetDatabase();
        System.out.println("✓ Reset hoàn tất");
        UserDatabase.printAllUsers();
    }
}