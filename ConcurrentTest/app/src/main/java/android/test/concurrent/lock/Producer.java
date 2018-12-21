package android.test.concurrent.lock;

import android.compact.utils.MathCompactUtil;

public class Producer extends Thread {
    String name;
    Channel channel;

    public Producer(String name, Channel channel) {
        super();
        this.name = name;
        this.channel = channel;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(33L);
            } catch (Exception e) {
            }
            this.channel.put(this.name, MathCompactUtil.randomString(6));
        }
    }
}
