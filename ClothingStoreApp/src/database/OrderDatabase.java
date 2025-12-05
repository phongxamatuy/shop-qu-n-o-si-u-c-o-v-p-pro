package database;

import model.Order;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDatabase {
    private static final String FILE_PATH = "orders.dat";
    
    public OrderDatabase() {
        // Không cần tạo thư mục vì file nằm ngoài project
    }
    
    // Lưu danh sách đơn hàng vào file
    public boolean saveOrders(List<Order> orders) {
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(orders);
            oos.flush();
            System.out.println("✓ Lưu đơn hàng thành công tại: " + new File(FILE_PATH).getAbsolutePath());
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
        File file = new File(FILE_PATH);
        
        // Nếu file không tồn tại, trả về danh sách rỗng
        if (!file.exists()) {
            System.out.println("ℹ File không tồn tại. Khởi tạo danh sách mới.");
            return new ArrayList<>();
        }
        
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            List<Order> orders = (List<Order>) ois.readObject();
            System.out.println("✓ Tải đơn hàng thành công! Tổng cộng: " + orders.size() + " đơn hàng");
            return orders;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("✗ Lỗi khi tải file: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    // Kiểm tra file có tồn tại không
    public boolean fileExists() {
        return new File(FILE_PATH).exists();
    }
    
    // Xóa file
    public boolean deleteFile() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }
    
    // Lấy đường dẫn file
    public String getFilePath() {
        return new File(FILE_PATH).getAbsolutePath();
    }
}