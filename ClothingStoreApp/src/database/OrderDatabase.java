package database;

import model.Order;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * OrderDatabase - Lưu dữ liệu đơn hàng riêng theo username
 * File name format: username_orders.dat
 */
public class OrderDatabase {
    private String username;
    private String filePath;
    
    public OrderDatabase(String username) {
        this.username = username;
        this.filePath = username + "_orders.dat";
    }
    
    // Lưu danh sách đơn hàng vào file
    public boolean saveOrders(List<Order> orders) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(orders);
            oos.flush();
            System.out.println("✓ Lưu đơn hàng (" + username + ") thành công!");
            return true;
        } catch (IOException e) {
            System.err.println("✗ Lỗi khi lưu file: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Tải danh sách đơn hàng từ file
    @SuppressWarnings("unchecked")
    public List<Order> loadOrders() {
        File file = new File(filePath);
        
        if (!file.exists()) {
            System.out.println("ℹ File không tồn tại. Khởi tạo danh sách mới.");
            return new ArrayList<>();
        }
        
        try (FileInputStream fis = new FileInputStream(filePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            List<Order> orders = (List<Order>) ois.readObject();
            System.out.println("✓ Tải đơn hàng (" + username + ") thành công!");
            return orders;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("✗ Lỗi khi tải file: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    // Kiểm tra file có tồn tại không
    public boolean fileExists() {
        return new File(filePath).exists();
    }
    
    // Xóa file
    public boolean deleteFile() {
        File file = new File(filePath);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }
    
    // Lấy đường dẫn file
    public String getFilePath() {
        return new File(filePath).getAbsolutePath();
    }
}