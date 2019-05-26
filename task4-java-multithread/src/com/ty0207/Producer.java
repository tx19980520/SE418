package com.ty0207;

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable {

    private AtomicInteger count;
    private AtomicInteger success;
    private AtomicInteger fail;
    private RequestLinkedBlockingDeque<Request> requests;

    public Producer(AtomicInteger count, RequestLinkedBlockingDeque<Request> requests, AtomicInteger fail, AtomicInteger success) {
        this.count = count;
        this.requests = requests;
        this.success = success;
        this.fail = fail;
    }

    @Override
    public void run() {
        System.out.println("start producer thread,id:" + Thread.currentThread().getId());
        try {
            while (true) {
                Random r = new Random();
                // add the production count
                Integer need = r.nextInt(4) + 1;// 1-4
                Integer count = this.count.addAndGet(need);
                System.out.println("production now: " + count + " by " + Thread.currentThread().getId());
                // deal with request
                System.out.println("requests size: " + this.requests.size());
                try {
                    Request request = this.requests.takeRequests();
                    if (request.getNum() <= this.count.get()) {
                        System.out.println("request " + request.getNum() + " has " + this.count.get());
                        this.count.updateAndGet(x -> (x - request.getNum()));
                        this.requests.remove(request);
                        System.out.println("success" + this.success.incrementAndGet());

                    } else {
                        System.out.println("count last: " + this.count.get());
                    }
                } catch (Exception e) {
                    if (e instanceof NoSuchElementException) {
                        System.out.println("now there is no request.");
                    } else if (e instanceof NullPointerException) {
                        System.out.println("now there is no request.");
                    }
                }
                System.out.println("clean old request");
                this.requests.clean();
                // clean the old request
                // sure you can choose use System.currentTimeMillis multitimes
                System.out.println("Producer sleep 2000ms");
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("producer thread quit safely, id:" + Thread.currentThread().getId());
        }
    }

    public AtomicInteger getCount() {
        return count;
    }

    public void setCount(AtomicInteger count) {
        this.count = count;
    }

    public LinkedBlockingDeque<Request> getRequests() {
        return requests;
    }

    public void setRequests(RequestLinkedBlockingDeque<Request> requests) {
        this.requests = requests;
    }
}
