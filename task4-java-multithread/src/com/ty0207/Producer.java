package com.ty0207;

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable {

    private AtomicInteger count;
    private AtomicInteger success;
    private AtomicInteger fail;
    private LinkedBlockingDeque<Request> requests;

    public Producer(AtomicInteger count, LinkedBlockingDeque<Request> requests, AtomicInteger success, AtomicInteger fail) {
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
                System.out.println("production now: "+ count + " by " + Thread.currentThread().getId());
                // deal with request
                System.out.println("requests size: " + this.requests.size());
                if(this.requests.size() > 5)
                {
                    System.out.println("FILO");
                    // need FILO
                    try{
                        Request request = this.requests.getLast();
                        if(request.getNum() <= this.count.get())
                        {
                            System.out.println("request "+ request.getNum() + " has " + this.count.get() );
                            this.count.updateAndGet(x -> (x - request.getNum()));
                            this.requests.remove(request);
                            this.success.incrementAndGet();
                        }
                        else{
                            System.out.println("count last: " + this.count.get());
                            break;
                        }
                    }
                    catch (NoSuchElementException e)
                    {
                        System.out.println("now there is no request.");
                        break;
                    }
                }
                else{
                    // can FIFO
                    while(true)
                    {
                        System.out.println("FIFO");
                        try{
                            Request request = this.requests.getFirst();
                            if(request.getNum() <= this.count.get())
                            {
                                System.out.println("request "+ request.getNum() + " has " + this.count.get() );
                                this.count.updateAndGet(x -> (x - request.getNum()));
                                this.requests.remove(request);
                                this.success.incrementAndGet();
                            }
                            else{
                                System.out.println("count last: " + this.count.get());
                                break;
                            }
                        }
                        catch (NoSuchElementException e)
                        {
                            System.out.println("now there is no request.");
                            break;
                        }
                    }
                }
                System.out.println("clean old request");
                // clean the old request
                while(true)
                {
                    // sure you can choose use System.currentTimeMillis multitimes
                    long now = System.currentTimeMillis();
                    try{
                        Request request = this.requests.getFirst();
                        if(now - request.getTime() > 2000)
                        {
                            // need clean
                            System.out.println("request " + request.hashCode() + "is out.");
                            this.requests.takeFirst();
                            this.fail.incrementAndGet();
                        }
                        else{
                            break;
                        }
                    }catch (NoSuchElementException ignored)
                    {
                       break;
                    }
                }
                System.out.println("Producer sleep 4000ms");
                Thread.sleep(4000);
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

    public void setRequests(LinkedBlockingDeque<Request> requests) {
        this.requests = requests;
    }
}
