package android.test.concurrent.lock;

import android.util.Log;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Channel2 implements ChannelImpl {
    static int capacity = 10;
    LinkedList<String> queue = new LinkedList<String>();
    AtomicInteger count = new AtomicInteger(0);
    ReentrantLock takeLock = new ReentrantLock();
    Condition consumerOptions = takeLock.newCondition();
    ReentrantLock putLock = new ReentrantLock();
    Condition producerOptions = putLock.newCondition();

    public Channel2() {
        super();
    }

    private void signalProducerOptions() {
        final ReentrantLock putLock = this.putLock;
        putLock.lock();
        try {
            producerOptions.signal();
        } finally {
            putLock.unlock();
        }
    }

    public String take(String name) {
        String randomStr = null;
        int c = -1;
        final AtomicInteger count = this.count;
        final ReentrantLock takeLock = this.takeLock;
        try {
            takeLock.lockInterruptibly();
            while (count.get() == 0) {
                Log.d("PPP", "[" + name + "]queue is empty, wait producer");
                consumerOptions.await();
            }
            randomStr = (String) queue.removeFirst();
            Log.d("PPP", "[" + name + "]Consuming str : " + randomStr);
            c = count.getAndDecrement();
            if (c > 1) {
                consumerOptions.signal();
            }
        } catch (Throwable t) {
        } finally {
            takeLock.unlock();
        }
        if (c == capacity) {
            signalProducerOptions();
        }
        return randomStr;
    }

    public void put(String name, String str) {
        int c = -1;
        final ReentrantLock putLock = this.putLock;
        final AtomicInteger count = this.count;
        try {
            putLock.lockInterruptibly();
            while (count.get() == capacity) {
                Log.d("PPP", "[" + name + "]queue is full, wait consumer");
                producerOptions.await();
            }
            queue.add(str);
            Log.d("PPP", "[" + name + "]Producing str : " + str);
            c = count.getAndIncrement();
            if (c + 1 < capacity) {
                producerOptions.signal();
            }
        } catch (Throwable t) {
        } finally {
            putLock.unlock();
        }
        if (c == 0) {
            signalNotEmpty();
        }
    }

    private void signalNotEmpty() {
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lock();
        try {
            consumerOptions.signal();
        } finally {
            takeLock.unlock();
        }
    }
}
