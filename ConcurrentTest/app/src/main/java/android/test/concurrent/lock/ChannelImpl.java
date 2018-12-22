package android.test.concurrent.lock;

public interface ChannelImpl {
    String take(String name);

    void put(String name, String str);
}
