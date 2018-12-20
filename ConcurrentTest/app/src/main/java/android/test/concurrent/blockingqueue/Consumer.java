package android.test.concurrent.blockingqueue;


import android.util.Log;

import java.util.LinkedList;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class Consumer extends Thread {
    String name;
    BlockingQueue queue;
    public Consumer(String name, BlockingQueue queue) {
        super();
        this.name = name;
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(33L);
            } catch (Exception e) {
            }
            String randomStr = null;
            try {
                randomStr = (String) queue.take();
            } catch (Exception e) {
            }
            Log.d("PPP", "[" + name + "]Consuming str : " + randomStr);
        }
    }
}