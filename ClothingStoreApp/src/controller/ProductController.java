package controller;

import java.util.ArrayList;
import database.ProductDatabase;
import model.Product;

public class ProductController {
    private ArrayList<Product> products;

    public ProductController() {
        products = ProductDatabase.load();
    }

    public void addProduct(Product p) {
        products.add(p);
        ProductDatabase.save(products);
    }

    public void updateProduct(String id, String name, String category, double price, int quantity) {
        for (Product p : products) {
            if (p.getId().equals(id)) {
                p.setName(name);
                p.setCategory(category);
                p.setPrice(price);
                p.setQuantity(quantity);
                ProductDatabase.save(products);
                return;
            }
        }
    }

    public void deleteProduct(String id) {
        products.removeIf(p -> p.getId().equals(id));
        ProductDatabase.save(products);
    }

    public ArrayList<Product> getAllProducts() {
        return products;
    }

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

