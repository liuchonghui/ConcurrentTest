package android.test.concurrent.semaphore;

import android.util.Log;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class Shared {
    LinkedList<String> queue = new LinkedList<String>();
    Semaphore semaphore = new Semaphore(0);
    Semaphore mutex = new Semaphore(1);

    public Shared() {
        super();
    }

    public String take(String name) {
        try {
            semaphore.acquire(1);
            mutex.acquire();
            String str = queue.remove();
            Log.d("PPP", "[" + name + "]Consuming str : " + str);
            mutex.release();
            return str;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public void put(String name, String str) {
        try {
            mutex.acquire();
            queue.add(str);
            Log.d("PPP", "[" + name + "]Producing str : " + str);
            mutex.release();
            semaphore.release(1);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
