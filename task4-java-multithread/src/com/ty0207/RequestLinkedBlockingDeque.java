package com.ty0207;

import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestLinkedBlockingDeque<E> extends LinkedBlockingDeque<E> {

    private AtomicInteger fail;
    public RequestLinkedBlockingDeque(int capacity, AtomicInteger fail) {
        super(capacity);
        this.fail = fail;
    }

    public boolean getRequests(E e, long timeout, TimeUnit unit) throws InterruptedException {
        return this.offerLast(e, timeout, unit);
    }

    public E takeRequests() {
        if (this.size() > 5) {
            System.out.println("FILO");
            // need FILO
            try {

                return this.getLast();
            } catch (NoSuchElementException e) {
                System.out.println("now there is no request.");
                return null;
            }
        } else {
            System.out.println("FIFO");
            try {
                return this.getFirst();
            } catch (NoSuchElementException e) {
                System.out.println("now there is no request.");
                return null;
            }
        }

    }

    public void clean()
    {
        long now = System.currentTimeMillis();
        try {
            E request = this.getFirst();
            if (now - ((Request)request).getTime() > 5000) {
                // need clean
                System.out.println("request " + request.hashCode() + "is out.");
                this.takeFirst();
                this.fail.incrementAndGet();
            }
        } catch (NoSuchElementException ignored) {

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
