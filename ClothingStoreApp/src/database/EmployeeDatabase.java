package database;

import model.Employee;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * EmployeeDatabase - Lưu dữ liệu nhân viên riêng theo username
 * File name format: username_employees.dat
 */
public class EmployeeDatabase {
    private List<Employee> employees;
    private String username;
    private String filePath;
    
    public EmployeeDatabase(String username) {
        this.username = username;
        this.filePath = username + "_employees.dat";
        employees = loadEmployees();
    }
    
    /**
     * Tải danh sách nhân viên từ file
     */
    private List<Employee> loadEmployees() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<Employee>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    /**
     * Lưu danh sách nhân viên vào file
     */
    private void saveEmployees() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(employees);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Thêm nhân viên mới
     */
    public void addEmployee(Employee emp) {
        employees.add(emp);
        saveEmployees();
    }
    
    /**
     * Xóa nhân viên theo ID
     */
    public void removeEmployee(String id) {
        employees.removeIf(emp -> emp.getId().equals(id));
        saveEmployees();
    }
    
    /**
     * Cập nhật thông tin nhân viên
     */
    public void updateEmployee(Employee emp) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId().equals(emp.getId())) {
                employees.set(i, emp);
                break;
            }
        }
        saveEmployees();
    }
    
    /**
     * Lấy tất cả nhân viên
     */
    public List<Employee> getAllEmployees() {
        return employees;
    }
}