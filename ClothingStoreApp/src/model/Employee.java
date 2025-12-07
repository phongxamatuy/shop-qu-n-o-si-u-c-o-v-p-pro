package model;

import java.io.Serializable;

/**
 * Employee - Model lớp đại diện cho một nhân viên
 */
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String fullName;
    private String email;
    private String gender;
    private String address;
    private String phoneNumber;
    
    // Constructor mặc định
    public Employee() {}
    
    // Constructor đầy đủ
    public Employee(String id, String fullName, String email, String gender, String address, String phoneNumber) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
    
    // ==================== GETTERS ====================
    
    public String getId() { 
        return id; 
    }
    
    public String getFullName() { 
        return fullName; 
    }
    
    public String getEmail() {
        return email; 
    }
    
    public String getGender() { 
        return gender;
    }
    
    public String getAddress() {
        return address;
    }
    
    public String getPhoneNumber() {
        return phoneNumber; 
    }
    
    // ==================== SETTERS ====================
    
    public void setId(String id) {
        this.id = id; 
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    public void setGender(String gender) { 
        this.gender = gender; 
    }
    
    public void setAddress(String address) { 
        this.address = address; 
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber; 
    }
}