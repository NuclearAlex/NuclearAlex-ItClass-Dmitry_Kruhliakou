package classes;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Stock {
    private final BlockingQueue<String> products;

    public int getMaxStock() {
        return maxStock;
    }

    public int getCurrentProductsCount() {
        return products.size();
    }

    private final int maxStock;

    public Stock(List<String> productList, int maxStock) {
        this.products = new ArrayBlockingQueue<>(productList.size());
        this.maxStock = maxStock;
    }

    public synchronized void addProduct(String product) {
        String[] dummy = product.split("; ");
        products.offer(dummy[0]);
    }

    public synchronized String getProduct() {
        String product = null;
        if (products.size() > 0) {
            product = products.poll();
        }
        return product;
    }
}