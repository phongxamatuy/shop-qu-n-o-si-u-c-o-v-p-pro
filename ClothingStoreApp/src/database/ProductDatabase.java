package database;

import model.Product;
import java.io.*;
import java.util.ArrayList;

/**
 * ProductDatabase - Lưu dữ liệu sản phẩm riêng theo username
 * File name format: username_products.dat
 */
public class ProductDatabase {
    
    /**
     * Tải sản phẩm của user từ file
     * Filename: username_products.dat
     */
    public static ArrayList<Product> load(String username) {
        String fileName = username + "_products.dat";
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
            ArrayList<Product> products = (ArrayList<Product>) ois.readObject();
            ois.close();
            return products;
        } catch (Exception e) {
            // File chưa tồn tại - trả về list rỗng
            return new ArrayList<>();
        }
    }
    
    /**
     * Lưu sản phẩm của user vào file
     * Filename: username_products.dat
     */
    public static void save(String username, ArrayList<Product> products) {
        String fileName = username + "_products.dat";
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(products);
            oos.close();
        } catch (Exception e) {
            System.out.println("Lỗi lưu file: " + e.getMessage());
        }
    }
}