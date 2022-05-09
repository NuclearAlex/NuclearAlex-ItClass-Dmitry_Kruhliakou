package classes;

import java.util.LinkedList;
import java.util.Queue;

public class Stock {
    private final Queue<String> products;

    public Stock() {
        this.products = new LinkedList<>();
    }

    public synchronized void addProduct(String product) {
        String[] dummy = product.split("; ");
        products.add(dummy[0]);
    }

    public synchronized String getProduct() {
        String product = null;
        if (products.size() > 0) {
            product = products.poll();
        }
        return product;
    }
}