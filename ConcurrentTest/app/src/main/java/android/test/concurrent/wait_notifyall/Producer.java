package android.test.concurrent.wait_notifyall;

import android.compact.utils.MathCompactUtil;
import android.util.Log;

import java.util.LinkedList;

public class Producer extends Thread {
    static int maxSize = 10;
    String name;
    LinkedList queue;
    public Producer(String name, LinkedList queue) {
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
                while (queue.size() == maxSize) {
                    try {
                        Log.d("PPP", "[" + name + "]queue is full, wait consumer");
                        queue.wait();
                    } catch (Exception ex) {
                        ex.printStackTrace(); }
                }
                String randomStr = null;
                try {
                    Thread.sleep(33L);
                    randomStr = MathCompactUtil.randomString(6);
                } catch (Exception e) {
                }
                Log.d("PPP", "[" + name + "]Producing str : " + randomStr);
                queue.add(randomStr);
                queue.notifyAll();
            }
        }
    }
}
