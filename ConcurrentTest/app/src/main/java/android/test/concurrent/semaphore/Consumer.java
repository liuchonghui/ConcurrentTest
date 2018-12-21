package android.test.concurrent.semaphore;


public class Consumer extends Thread {
    String name;
    Shared shared;

    public Consumer(String name, Shared shared) {
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
            this.shared.take(this.name);
        }
    }
}