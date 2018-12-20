package android.test.concurrent.wait_notifyall;


import android.util.Log;

import java.util.LinkedList;

public class Consumer extends Thread {
    String name;
    LinkedList queue;
    public Consumer(String name, LinkedList queue) {
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
            synchronized (queue) {
                while (queue.size() == 0) {
                    try {
                        Log.d("PPP", "[" + name + "]queue is full, wait producer");
                        queue.wait();
                    } catch (Exception ex) {
                        ex.printStackTrace(); }
                }
                String randomStr = null;
                try {
                    Thread.sleep(33L);
                    randomStr = (String) queue.removeFirst();
                } catch (Exception e) {
                }
                Log.d("PPP", "[" + name + "]Consuming str : " + randomStr);
                queue.notifyAll();
            }
        }
    }
}