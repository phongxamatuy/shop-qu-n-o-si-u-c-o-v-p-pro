package controller;

import java.util.ArrayList;
import database.CustomerDatabase;
import model.Customer;
import view.CustomerManagementView;

/**
 * CustomerController - Quản lý khách hàng riêng theo username
 * Mỗi user có file customers riêng: username_customers.dat
 */
public class CustomerController {
    private CustomerDatabase db;
    private CustomerManagementView view;
    private String username;
    
    public CustomerController(CustomerManagementView view, String username) {
        this.view = view;
        this.username = username;
        this.db = new CustomerDatabase(username);
        view.refreshTable();
    }
    
    // Thêm khách hàng - check SDT và email không trùng
    public boolean add(Customer c) {
        if (!isPhoneUnique(c.getPhone())) {
            return false;
        }
        if (!isEmailUnique(c.getEmail())) {
            return false;
        }
        return db.add(c);
    }
    
    // Cập nhật khách hàng
    public boolean update(Customer c) {
        if (!isPhoneUniqueForUpdate(c.getId(), c.getPhone())) {
            return false;
        }
        if (!isEmailUniqueForUpdate(c.getId(), c.getEmail())) {
            return false;
        }
        return db.update(c);
    }
    
    // Xóa khách hàng
    public boolean delete(String id) {
        return db.delete(id);
    }
    
    // Lấy tất cả khách hàng
    public ArrayList<Customer> getAll() {
        return db.getAll();
    }
    
    // Kiểm tra SDT có trùng không
    private boolean isPhoneUnique(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return true;
        }
        for (Customer c : db.getAll()) {
            if (c.getPhone().equals(phone)) {
                return false;
            }
        }
        return true;
    }
    
    // Kiểm tra SDT không trùng khi UPDATE (bỏ qua khách hàng hiện tại)
    private boolean isPhoneUniqueForUpdate(String customerId, String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return true;
        }
        for (Customer c : db.getAll()) {
            if (!c.getId().equals(customerId) && c.getPhone().equals(phone)) {
                return false;
            }
        }
        return true;
    }
    
    // Kiểm tra Email có trùng không
    private boolean isEmailUnique(String email) {
        if (email == null || email.trim().isEmpty()) {
            return true;
        }
        for (Customer c : db.getAll()) {
            if (c.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }
    
    // Kiểm tra Email không trùng khi UPDATE
    private boolean isEmailUniqueForUpdate(String customerId, String email) {
        if (email == null || email.trim().isEmpty()) {
            return true;
        }
        for (Customer c : db.getAll()) {
            if (!c.getId().equals(customerId) && c.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }
}