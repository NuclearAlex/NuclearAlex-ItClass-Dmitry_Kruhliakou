package classes;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Producer extends Thread {
    private final Queue<String> products;
    private final Stock stock;

    public Producer(String name, Stock stock, List<String> productList) {
        super(name);
        this.stock = stock;
        this.products = new LinkedList<>();
        products.addAll(productList);
    }

    public String getProductToStock() {
        return products.poll();
    }

    public boolean isPutProduct() {
        return products.size() > 0;
    }

    @Override
    public void run() {
        while (isPutProduct()) {
            String product1 = getProductToStock();
            String product2 = getProductToStock();
            String product3 = getProductToStock();
            String[] temp1 = product1.split("; ");
            String[] temp2 = product2.split("; ");
            String[] temp3 = product3.split("; ");
            stock.addProduct(product1);
            stock.addProduct(product2);
            stock.addProduct(product3);
            System.out.println(getName() + " added product " + temp1[0] + " to stock");
            System.out.println(getName() + " added product " + temp2[0] + " to stock");
            System.out.println(getName() + " added product " + temp3[0] + " to stock");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}