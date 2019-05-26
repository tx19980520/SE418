package com.ty0207;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {


    public static void main(String[] args) throws InterruptedException {
        AtomicInteger fail = new AtomicInteger();
        AtomicInteger count = new AtomicInteger();
        RequestLinkedBlockingDeque<Request> requests = new RequestLinkedBlockingDeque<>(20, fail);
        AtomicInteger success = new AtomicInteger();
        AtomicInteger request = new AtomicInteger();
        Producer producer1 = new Producer(count, requests, fail, success);
        Producer producer2 = new Producer(count, requests, fail, success);
        Producer producer3 = new Producer(count, requests, fail, success);
        Consumer consumer = new Consumer(requests, request);
        ExecutorService service = Executors.newCachedThreadPool();


        service.execute(producer1);
        service.execute(producer2);
        service.execute(producer3);
        service.execute(consumer);
        Thread.sleep(10 * 1000);
        System.out.println("shutdowning");
        service.shutdownNow();
        System.out.println("shutdowned");
        int wait = request.get() - success.get() - fail.get();
        System.out.println("requests: " + request +" success: " + success + " fail :" + fail + " waiting: " + wait);
    }
}
