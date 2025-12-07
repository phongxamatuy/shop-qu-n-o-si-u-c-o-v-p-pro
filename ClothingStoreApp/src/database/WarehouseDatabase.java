package database;

import model.Warehouse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * WarehouseDatabase - Lưu dữ liệu kho riêng theo username
 * File name format: username_warehouse.dat
 */
public class WarehouseDatabase {
    private String username;
    private String filePath;
    
    public WarehouseDatabase(String username) {
        this.username = username;
        this.filePath = username + "_warehouse.dat";
    }
    
    // Lưu danh sách kho vào file
    public boolean saveWarehouses(List<Warehouse> warehouses) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(warehouses);
            oos.flush();
            System.out.println("✓ Lưu kho (" + username + ") thành công!");
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
        File file = new File(filePath);
        
        if (!file.exists()) {
            System.out.println("ℹ File không tồn tại. Khởi tạo danh sách mới.");
            return new ArrayList<>();
        }
        
        try (FileInputStream fis = new FileInputStream(filePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            List<Warehouse> warehouses = (List<Warehouse>) ois.readObject();
            System.out.println("✓ Tải kho (" + username + ") thành công!");
            return warehouses;
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