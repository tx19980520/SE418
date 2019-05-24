package com.ty0207;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer implements Runnable {
    private LinkedBlockingDeque<Request> requests;
    private AtomicInteger request;

    public Consumer(LinkedBlockingDeque<Request> requests, AtomicInteger request) {
        this.requests = requests;
        this.request = request;
    }

    @Override
    public void run() {
        System.out.println("start consumer thread,id:" + Thread.currentThread().getId());
        try {
            while (true) {
                long timeout = 2000;
                if(this.requests.offerLast(new Request(), timeout, TimeUnit.MILLISECONDS))
                {
                    System.out.println("add new Request success!");
                    this.request.incrementAndGet();
                }
                else{
                    System.out.println("add new Request failed!");
                }
                Thread.sleep(500);
            }
        } catch (Exception e) {
            // e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("consumer thread quit safely, id:" + Thread.currentThread().getId());
        }
    }

}
