package android.test.concurrent.semaphore;

import android.compact.utils.MathCompactUtil;

public class Producer extends Thread {
    String name;
    Shared shared;

    public Producer(String name, Shared shared) {
        super();
        this.name = name;
        this.shared = shared;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(33L);
            } catch (Exception e) {
            }
            this.shared.put(this.name, MathCompactUtil.randomString(6));
        }
    }
}
