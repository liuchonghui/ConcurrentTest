package android.test.concurrent.lock;

import android.util.Log;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Channel {
    static int maxSize = 10;
    LinkedList<String> queue = new LinkedList<String>();
    ReentrantLock reentrantLock = new ReentrantLock();
    Condition producerOptions = reentrantLock.newCondition();
    Condition consumerOptions = reentrantLock.newCondition();

    public Channel() {
        super();
    }

    public String take(String name) {
        final ReentrantLock lock = this.reentrantLock;
        try {
            lock.lock();
            while (queue.size() == 0) {
                Log.d("PPP", "[" + name + "]queue is empty, wait producer");
                consumerOptions.await();
            }
            String randomStr = null;
            try {
                randomStr = (String) queue.removeFirst();
            } catch (Exception e) {
            }
            Log.d("PPP", "[" + name + "]Consuming str : " + randomStr);
            producerOptions.signalAll();
            return randomStr;
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        } finally {
            lock.unlock();
            Log.d("PPP", "[" + name + "]unlock");
        }
    }

    public void put(String name, String str) {
        final ReentrantLock lock = this.reentrantLock;
        try {
            lock.lock();
            while (queue.size() == maxSize) {
                try {
                    Log.d("PPP", "[" + name + "]queue is full, wait consumer");
                    producerOptions.await();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            Log.d("PPP", "[" + name + "]Producing str : " + str);
            queue.add(str);
            consumerOptions.signalAll();
            Log.d("PPP", "[" + name + "]signalAll");
            Thread.sleep(3000L);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            lock.unlock();
            Log.d("PPP", "[" + name + "]unlock");
        }
    }
}
