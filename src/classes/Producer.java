package classes;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Producer implements Custom {
    private final BlockingQueue<String> products;
    private final Stock stock;
    private final int countProd;
    private final String name;

    public Producer(String name, Stock stock, List<String> productList, int countProd) throws Exception {
        this.name = name;
        if (countProd > stock.getMaxStock()) {
            throw new Exception("You are overload max limit of stock");
        }
        this.stock = stock;
        this.products = new ArrayBlockingQueue<>(productList.size());
        this.countProd = countProd;
        products.addAll(productList);
    }

    public String getProductToStock() {
        return products.poll();
    }

    public void addProduct(int countProd) {
        for (int i = 0; i < countProd; i++) {
            String product = getProductToStock();
            try {
                String[] temp = product.split("; ");
                stock.addProduct(product);
                System.out.println(name + " added product " + temp[0] + " to stock. Return "
                        + stock.getCurrentProductsCount());
            } catch (NullPointerException e) {
                break;
            }
        }
    }

    public boolean isPutProduct() {
        return products.size() > 0;
    }

    public Producer produced() {
        while (isPutProduct()) {
            if ((stock.getMaxStock() - stock.getCurrentProductsCount() >= countProd)) {
                addProduct(countProd);
            } else {
                try {
                    System.out.println(name + " is sleep (zzz)");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return this;
    }

    @Override
    public String toString() {
        return "Producer: " + "name = " + name + ", products remained = " + products.size() + ';';
    }
}