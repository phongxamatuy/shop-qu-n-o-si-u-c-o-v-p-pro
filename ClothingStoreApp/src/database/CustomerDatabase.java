package database;

import java.io.*;
import java.util.ArrayList;
import model.Customer;

public class CustomerDatabase {
    private ArrayList<Customer> customers = new ArrayList<>();
    private final String FILE = "customers.dat";
    
    public CustomerDatabase() {
        load();
    }
    
    // CREATE
    public boolean add(Customer c) {
        for (Customer x : customers) {
            if (x.getId().equals(c.getId())) {
                return false;
            }
        }
        customers.add(c);
        save();
        return true;
    }
    
    // READ
    public ArrayList<Customer> getAll() {
        return customers;
    }
    
    // UPDATE
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
    
    // DELETE
    public boolean delete(String id) {
        boolean removed = customers.removeIf(c -> c.getId().equals(id));
        if (removed) {
            save();
        }
        return removed;
    }
    
    // SAVE FILE
    private void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE))) {
            oos.writeObject(customers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // LOAD FILE
    private void load() {
        File f = new File(FILE);
        if (!f.exists()) {
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE))) {
            customers = (ArrayList<Customer>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}