package org.nordmann.java9;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by yoavn on 5/3/17.
 */
public class CompletableFutureEnhancements {

    private static final Integer DEFAULT_PRICE = 1;

    public static void main(String [] args){


    }


    private static void exampleOld1() {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        try {
            CompletableFuture.supplyAsync(() -> findBestPrice("LDN - NYC"), executorService)
                            .thenCombine(CompletableFuture.supplyAsync(() -> queryExchangeRateFor("GBP")),
                                    CompletableFutureEnhancements::convert)
                            .get(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public <T> CompletableFuture<T> timeoutAfter(long timeout, TimeUnit unit) {
        CompletableFuture<T> result = new CompletableFuture<>();

        //delayer.schedule(() -> result.completeExceptionally(new TimeoutException()), timeout, unit);
        return result;
    }


    private static void exampleOld2(){
        CompletableFuture completableFuture = new CompletableFuture().newIncompleteFuture();
    }


    private static void example1(){
        CompletableFuture completableFuture = new CompletableFuture().newIncompleteFuture();
    }

    private static void example2(){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CompletableFuture.supplyAsync(() -> findBestPrice("LDN - NYC"), executorService)
                .thenCombine(CompletableFuture.supplyAsync(() -> queryExchangeRateFor("GBP")), CompletableFutureEnhancements::convert)
                .orTimeout(1, TimeUnit.SECONDS)
                .whenComplete((amount, error) -> {
                    if (error == null) {
                        System.out.println("The price is: " + amount + "GBP");
                    } else {
                        System.out.println("Sorry, we could not return you a result");
                    }
                });
    }



    private static void example3(){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CompletableFuture.supplyAsync(() -> findBestPrice("LDN - NYC"), executorService)
                .thenCombine(CompletableFuture.supplyAsync(() -> queryExchangeRateFor("GBP")),
                        CompletableFutureEnhancements::convert)
                .completeOnTimeout(DEFAULT_PRICE, 1, TimeUnit.SECONDS)
                .thenAccept(amount -> {
                    System.out.println("The price is: " + amount + "GBP");
                });
    }



    private static void example4(){
        new CompletableFuture().completeAsync(() -> "Inside task",
                CompletableFuture.delayedExecutor(3, TimeUnit.SECONDS))
                .thenAccept(result -> System.out.println("accept: " + result));
    }


    private static Double findBestPrice(String s) {
        try {
            Thread.currentThread().wait(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Random().nextDouble();
    }


    private static Double queryExchangeRateFor(String gbp) {
        try {
            Thread.currentThread().wait(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Random().nextDouble();
    }


    private static <V, U> Integer convert(Double aDouble, U u) {
        try {
            Thread.currentThread().wait(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Random().nextInt();
    }

}
