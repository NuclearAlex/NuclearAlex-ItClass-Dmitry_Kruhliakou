package classes;

import java.util.ArrayList;
import java.util.List;

public class Consumer extends Thread {
    static final String BLUE = "\u001b[34m";
    static final String DEFAULT = "\u001b[0m";

    private final List<String> products;
    private final Stock stock;

    public Consumer(String name, Stock stock) {
        super(name);
        this.stock = stock;
        this.products = new ArrayList<>();
    }

    public void purchase(String product) {
        String[] temp = product.split("; ");
        products.add(temp[0]);
    }

    @Override
    public String toString() {
        return "Consumer " + getName() + " bought products: " + BLUE + products + DEFAULT + ';';
    }

    @Override
    public void run() {
        boolean isBought = true;
        while (isBought) {
            String product = stock.getProduct();
            if (product != null) {
                purchase(product);
                System.out.println(getName() + " purchase " + product);
            }
            isBought = products.size() < 3;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}