package controller;

import java.util.ArrayList;
import database.ProductDatabase;
import model.Product;

/**
 * ProductController - Quản lý sản phẩm riêng theo username
 * Mỗi user có file products riêng: username_products.dat
 */
public class ProductController {
    private ArrayList<Product> products;
    private String username;

    public ProductController(String username) {
        this.username = username;
        products = ProductDatabase.load(username);
    }

    // Thêm sản phẩm
    public void addProduct(Product p) {
        products.add(p);
        ProductDatabase.save(username, products);
    }

    // Cập nhật sản phẩm
    public void updateProduct(String id, String name, String category, double price, int quantity) {
        for (Product p : products) {
            if (p.getId().equals(id)) {
                p.setName(name);
                p.setCategory(category);
                p.setPrice(price);
                p.setQuantity(quantity);
                ProductDatabase.save(username, products);
                return;
            }
        }
    }

    // Xóa sản phẩm
    public void deleteProduct(String id) {
        products.removeIf(p -> p.getId().equals(id));
        ProductDatabase.save(username, products);
    }

    // Lấy tất cả sản phẩm
    public ArrayList<Product> getAllProducts() {
        return products;
    }

    // Tìm theo danh mục
    public ArrayList<Product> filterByCategory(String category) {
        ArrayList<Product> list = new ArrayList<>();
        for (Product p : products) {
            if (p.getCategory().equalsIgnoreCase(category)) {
                list.add(p);
            }
        }
        return list;
    }
}