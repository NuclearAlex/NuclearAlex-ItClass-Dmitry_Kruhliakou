import classes.Consumer;
import classes.Producer;
import classes.Stock;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Runner {
    static final String GREEN = "\u001b[32m";
    static final String DEFAULT = "\u001b[0m";

    public static void main(String[] args) {
        List<String> products = createList();
        System.out.println("Products which will be created and added by producer:");
        for (String str : products) {
            System.out.println(GREEN + str + DEFAULT);
        }
        System.out.println();

        Stock stock = new Stock();
        Thread producer = new Producer("\"OSCORP\"", stock, products);
        Thread consumer1 = new Consumer("Alex", stock);
        Thread consumer2 = new Consumer("Piter", stock);
        Thread consumer3 = new Consumer("Pablo", stock);
        Thread consumer4 = new Consumer("William", stock);

        producer.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();
        consumer4.start();

        try {
            producer.join();
            consumer1.join();
            consumer2.join();
            consumer3.join();
            consumer4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n" + consumer1);
        System.out.println(consumer2);
        System.out.println(consumer3);
        System.out.println(consumer4);
    }

    public static List<String> createList() {
        List<String> products = new ArrayList<>();
        try (Scanner sc = new Scanner(new File("src/classes/products.txt"))) {
            while (sc.hasNextLine()) {
                products.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return products;
    }
}