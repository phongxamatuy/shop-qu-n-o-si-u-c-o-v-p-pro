package database;

import java.io.*;
import java.util.ArrayList;
import model.Customer;

/**
 * CustomerDatabase - Lưu dữ liệu khách hàng riêng theo username
 * File name format: username_customers.dat
 */
public class CustomerDatabase {
    private ArrayList<Customer> customers = new ArrayList<>();
    private String username;
    private String filePath;
    
    public CustomerDatabase(String username) {
        this.username = username;
        this.filePath = username + "_customers.dat";
        load();
    }
    
    // CREATE - Thêm khách hàng mới
    public boolean add(Customer c) {
        // Kiểm tra ID trùng lặp
        for (Customer x : customers) {
            if (x.getId().equals(c.getId())) {
                return false;
            }
        }
        customers.add(c);
        save();
        return true;
    }
    
    // READ - Lấy tất cả khách hàng
    public ArrayList<Customer> getAll() {
        return customers;
    }
    
    // UPDATE - Cập nhật khách hàng
    public boolean update(Customer c) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId().equals(c.getId())) {
                customers.set(i, c);
                save();
                return true;
            }
        }
        return false;
    }
    
    // DELETE - Xóa khách hàng
    public boolean delete(String id) {
        boolean removed = customers.removeIf(c -> c.getId().equals(id));
        if (removed) {
            save();
        }
        return removed;
    }
    
    // Lưu dữ liệu vào file
    private void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(customers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Tải dữ liệu từ file
    private void load() {
        File f = new File(filePath);
        if (!f.exists()) {
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            customers = (ArrayList<Customer>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}