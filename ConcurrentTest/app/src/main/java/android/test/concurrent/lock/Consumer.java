package android.test.concurrent.lock;


public class Consumer extends Thread {
    String name;
    Channel channel;
    public Consumer(String name, Channel channel) {
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