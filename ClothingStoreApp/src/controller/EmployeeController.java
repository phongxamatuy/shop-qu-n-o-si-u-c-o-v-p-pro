package controller;

import view.EmployeeManagementView;
import database.EmployeeDatabase;
import model.Employee;
import javax.swing.table.DefaultTableModel;

/**
 * EmployeeController - Quản lý nhân viên riêng theo username
 * Mỗi user có file employees riêng: username_employees.dat
 */
public class EmployeeController {
    private EmployeeManagementView view;
    private EmployeeDatabase db;
    private String username;
    
    public EmployeeController(EmployeeManagementView view, String username) {
        this.view = view;
        this.username = username;
        this.db = new EmployeeDatabase(username);
        initController();
        loadTable();
    }
    
    /**
     * Gắn các event listener cho các button
     */
    private void initController() {
        view.btn_Add.addActionListener(e -> addEmployee());
        view.btn_Remove.addActionListener(e -> removeEmployee());
        view.btn_Update.addActionListener(e -> updateEmployee());
        view.btn_Show.addActionListener(e -> loadTable());
    }
    
    /**
     * Thêm nhân viên mới
     */
    private void addEmployee() {
        String id = view.txt_ID.getText().trim();
        String fullName = view.txt_fullName.getText().trim();
        String email = view.txt_Email.getText().trim();
        String gender = view.txt_Gender.getText().trim();
        String address = view.txt_Address.getText().trim();
        String phoneNumber = view.txt_PhoneNumber.getText().trim();
        
        if (id.isEmpty() || fullName.isEmpty()) {
            view.showError("ID và Tên không được để trống!");
            return;
        }
        
        Employee emp = new Employee(id, fullName, email, gender, address, phoneNumber);
        db.addEmployee(emp);
        
        loadTable();
        clearForm();
        view.showMessage("Thêm nhân viên thành công!");
    }
    
    /**
     * Xóa nhân viên
     */
    private void removeEmployee() {
        String id = view.txt_ID.getText().trim();
        
        if (id.isEmpty()) {
            view.showError("Vui lòng chọn nhân viên để xóa!");
            return;
        }
        
        db.removeEmployee(id);
        loadTable();
        clearForm();
        view.showMessage("Xóa nhân viên thành công!");
    }
    
    /**
     * Cập nhật thông tin nhân viên
     */
    private void updateEmployee() {
        String id = view.txt_ID.getText().trim();
        String fullName = view.txt_fullName.getText().trim();
        String email = view.txt_Email.getText().trim();
        String gender = view.txt_Gender.getText().trim();
        String address = view.txt_Address.getText().trim();
        String phoneNumber = view.txt_PhoneNumber.getText().trim();
        
        if (id.isEmpty() || fullName.isEmpty()) {
            view.showError("ID và Tên không được để trống!");
            return;
        }
        
        Employee emp = new Employee(id, fullName, email, gender, address, phoneNumber);
        db.updateEmployee(emp);
        
        loadTable();
        clearForm();
        view.showMessage("Cập nhật nhân viên thành công!");
    }
    
    /**
     * Load dữ liệu vào bảng
     */
    private void loadTable() {
        DefaultTableModel model = view.tableModel;
        model.setRowCount(0);
        
        for (Employee emp : db.getAllEmployees()) {
            model.addRow(new Object[]{
                emp.getId(),
                emp.getFullName(),
                emp.getEmail(),
                emp.getPhoneNumber(),
                emp.getGender(),
                emp.getAddress()
            });
        }
    }
    
    /**
     * Xóa dữ liệu từ form
     */
    private void clearForm() {
        view.txt_ID.setText("");
        view.txt_fullName.setText("");
        view.txt_Email.setText("");
        view.txt_Gender.setText("");
        view.txt_Address.setText("");
        view.txt_PhoneNumber.setText("");
        view.ShowTable.clearSelection();
    }
}