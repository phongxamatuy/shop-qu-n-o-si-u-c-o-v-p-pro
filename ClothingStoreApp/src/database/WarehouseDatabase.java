package database;

import model.Warehouse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WarehouseDatabase {
    private static final String FILE_PATH = "warehouse.dat";
    
    public WarehouseDatabase() {
        // Không cần tạo thư mục vì file nằm ngoài project
    }
    
    // Lưu danh sách kho vào file
    public boolean saveWarehouses(List<Warehouse> warehouses) {
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(warehouses);
            oos.flush();
            System.out.println("✓ Lưu kho thành công tại: " + new File(FILE_PATH).getAbsolutePath());
            return true;
        } catch (IOException e) {
            System.err.println("✗ Lỗi khi lưu file: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Tải danh sách kho từ file
    @SuppressWarnings("unchecked")
    public List<Warehouse> loadWarehouses() {
        File file = new File(FILE_PATH);
        
        // Nếu file không tồn tại, trả về danh sách rỗng
        if (!file.exists()) {
            System.out.println("ℹ File không tồn tại. Khởi tạo danh sách mới.");
            return new ArrayList<>();
        }
        
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            List<Warehouse> warehouses = (List<Warehouse>) ois.readObject();
            System.out.println("✓ Tải kho thành công! Tổng cộng: " + warehouses.size() + " sản phẩm");
            return warehouses;
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