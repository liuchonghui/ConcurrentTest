package android.test.concurrent.blockingqueue;

import android.compact.utils.MathCompactUtil;
import android.util.Log;

import java.util.concurrent.BlockingQueue;

public class Producer extends Thread {
    static int maxSize = 10;
    String name;
    BlockingQueue queue;

    public Producer(String name, BlockingQueue queue) {
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
                randomStr = MathCompactUtil.randomString(6);
            } catch (Exception e) {
            }
            Log.d("PPP", "[" + name + "]Producing str : " + randomStr);
            try {
                queue.put(randomStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
