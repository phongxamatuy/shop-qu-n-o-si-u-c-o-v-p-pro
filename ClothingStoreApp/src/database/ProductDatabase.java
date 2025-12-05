package database;
import model.Product;
import java.io.*;
import java.util.ArrayList;

public class ProductDatabase {
    private static final String FILE_NAME = "products.dat";

    public static ArrayList<Product> load() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME));
            return (ArrayList<Product>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void save(ArrayList<Product> products) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            oos.writeObject(products);
            oos.close();
        } catch (Exception e) {
            System.out.println("Lỗi lưu file: " + e.getMessage());
        }
    }
}
