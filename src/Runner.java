import classes.Consumer;
import classes.Custom;
import classes.Producer;
import classes.Stock;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Runner {
    static final String GREEN = "\u001b[32m";
    static final String DEFAULT = "\u001b[0m";

    public static void main(String[] args) throws Exception {
        List<String> products = createList();
        System.out.println("Products which will be created and added by producer:");
        for (String str : products) {
            System.out.println(GREEN + str + DEFAULT);
        }
        System.out.println();

        // only thread pulls!!!
        // nothing join()!!!!

        Stock stock = new Stock(products, 5);

        Producer prod = new Producer("\"OSCORP\"", stock, products, 3);

        Consumer cons1 = new Consumer("Alex", stock, products, 3);
        Consumer cons2 = new Consumer("Piter", stock, products,5);
        Consumer cons3 = new Consumer("Pablo", stock, products, 7);
        Consumer cons4 = new Consumer("William", stock, products, 2);

        List<Consumer> consumers = new ArrayList<>();
        consumers.add(cons1);
        consumers.add(cons2);
        consumers.add(cons3);
        consumers.add(cons4);

        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        List<Future<String>> futures = new ArrayList<>();

        futures.add(
                CompletableFuture.supplyAsync(
                        () -> { Producer c = prod.produced();
                            return c.toString();},
                        threadPool
                ));
        for (int i = 0; i < consumers.size(); i++) {
            int finalI = i;
            futures.add(
                    CompletableFuture.supplyAsync(
                            () -> { Consumer c = consumers.get(finalI).buy();
                                return c.toString();},
                            threadPool
                    ));
        }
        List<String> result = new ArrayList<>();
        for (Future<String> future : futures) {
//            System.out.println(future.get());
            result.add(future.get());
        }

        threadPool.shutdown();

        result.forEach(System.out::println);
//        Thread producer = null;
//        try {
//            producer = new Producer("\"OSCORP\"", stock, products, 3);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Thread consumer1 = new Consumer("Alex", stock, products, 3);
//        Thread consumer2 = new Consumer("Piter", stock, products,5 );
//        Thread consumer3 = new Consumer("Pablo", stock, products, 7);
//        Thread consumer4 = new Consumer("William", stock, products, 2);
//
//        producer.start();
//        consumer1.start();
//        consumer2.start();
//        consumer3.start();
//        consumer4.start();
//
//        try {
//            producer.join();
//            consumer1.join();
//            consumer2.join();
//            consumer3.join();
//            consumer4.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("\n" + consumer1);
//        System.out.println(consumer2);
//        System.out.println(consumer3);
//        System.out.println(consumer4);
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