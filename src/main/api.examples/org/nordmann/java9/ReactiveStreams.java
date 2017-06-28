package org.nordmann.java9;

import java.util.Arrays;
import java.util.concurrent.*;

/**
 * Created by yoavn on 5/3/17.
 */
public class ReactiveStreams {


    public static void main(String [] args) {

        final int MAX_BUFFER_CAPACITY = 128;
        final ExecutorService executor = Executors.newFixedThreadPool(1);

        //Create Publisher
        SubmissionPublisher<String> publisher = 
        		new SubmissionPublisher<>(executor, MAX_BUFFER_CAPACITY);

        //Register Subscriber
        SupermanSubscriber<String> subscriber = new SupermanSubscriber<>();
        publisher.subscribe(subscriber);

        //Publish items
        System.out.println("Publishing Items...");
        String[] items = {"1", "x", "2", "y", "3", "z"};
        Arrays.asList(items).stream().forEach(i -> publisher.submit(i));
        publisher.close();
        System.out.println("Publisher Closed...");
    }


    private static class SupermanSubscriber<T> implements Flow.Subscriber<T> {

        private Flow.Subscription subscription;

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            //subscription.request(Long.MAX_VALUE );
            subscription.request(1);
        }

        @Override
        public void onNext(T item) {
            System.out.println("Got : " + item);
            subscription.request(1);
        }

        @Override
        public void onError(Throwable t) {
            t.printStackTrace();
        }

        @Override
        public void onComplete() {
            System.out.println("Done");
            subscription.cancel();
        }
    }


}
