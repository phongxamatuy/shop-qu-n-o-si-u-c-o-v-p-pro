package controller;

import java.util.ArrayList;
import database.CustomerDatabase;
import model.Customer;
import view.CustomerManagementView;

public class CustomerController {
    private CustomerDatabase db;
    private CustomerManagementView view;
    
    public CustomerController(CustomerManagementView view) {
        this.view = view;
        this.db = new CustomerDatabase();
        view.refreshTable();
    }
    
    public boolean add(Customer c) {
        // Kiểm tra SDT không trùng
        if (!isPhoneUnique(c.getPhone())) {
            return false;
        }
        // Kiểm tra Email không trùng
        if (!isEmailUnique(c.getEmail())) {
            return false;
        }
        return db.add(c);
    }
    
    public boolean update(Customer c) {
        // Kiểm tra SDT không trùng (ngoại trừ chính nó)
        if (!isPhoneUniqueForUpdate(c.getId(), c.getPhone())) {
            return false;
        }
        // Kiểm tra Email không trùng (ngoại trừ chính nó)
        if (!isEmailUniqueForUpdate(c.getId(), c.getEmail())) {
            return false;
        }
        return db.update(c);
    }
    
    public boolean delete(String id) {
        return db.delete(id);
    }
    
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
    
    // Kiểm tra Email không trùng khi UPDATE (bỏ qua khách hàng hiện tại)
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