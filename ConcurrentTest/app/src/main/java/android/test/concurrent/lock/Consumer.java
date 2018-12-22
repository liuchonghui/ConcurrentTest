package android.test.concurrent.lock;


public class Consumer extends Thread {
    String name;
    ChannelImpl channel;
    public Consumer(String name, ChannelImpl channel) {
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
            this.channel.take(this.name);
        }
    }
}