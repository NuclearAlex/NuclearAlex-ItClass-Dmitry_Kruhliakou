package classes;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Custom {
    static final String BLUE = "\u001b[34m";
    static final String DEFAULT = "\u001b[0m";

    private final BlockingQueue<String> products;
    private final Stock stock;
    private final int wantToBuy;
    private final String name;

    public Consumer(String name, Stock stock, List<String> products, int wantToBuy) {
        this.name = name;
        this.stock = stock;
        this.products = new ArrayBlockingQueue<>(products.size());
        this.wantToBuy = wantToBuy;
        products.addAll(products);
    }

    public void purchase(String product) {
        String[] temp = product.split("; ");
        products.add(temp[0]);
    }

    @Override
    public String toString() {
        return "Consumer " + name + " bought products: " + BLUE + products + DEFAULT + ';';
    }

    public Consumer buy() {
        int countTrying = 0;
        while (products.size() < wantToBuy) {
            String product = stock.getProduct();
            if (product != null) {
                purchase(product);
                System.out.println(name + " purchase " + product);
                countTrying = 0;
            } else {
                try {
                    System.out.println(name + " is sleep (zzz)");
                    Thread.sleep(2000);
                    countTrying++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (countTrying > 5) {
                System.err.println(name + " hasn't bought enough");
                break;
            }
        }
        return this;
    }
}