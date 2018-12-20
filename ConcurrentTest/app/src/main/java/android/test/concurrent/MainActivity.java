package android.test.concurrent;

import android.app.Activity;
import android.os.Bundle;
import android.test.concurrent.wait_notifyall.Consumer;
import android.test.concurrent.wait_notifyall.Producer;
import android.view.View;
import android.widget.Button;

import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class MainActivity extends Activity {

    LinkedList<String> Queue = new LinkedList<String>();
    LinkedBlockingQueue LBQueue;
    ArrayBlockingQueue<String> BQueue = new ArrayBlockingQueue<String>(10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 使用wait-notifyall实现
                Producer p1 = new Producer("p1", Queue);
                Producer p2 = new Producer("p2", Queue);
                p2.start();
                p1.start();
                Consumer c1 = new Consumer("c1", Queue);
                Consumer c2 = new Consumer("c2", Queue);
                c2.start();
                c1.start();
            }
        });
        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 使用BlockingQueue实现
                android.test.concurrent.blockingqueue.Producer p1 = new android.test.concurrent.blockingqueue.Producer("p1", BQueue);
                android.test.concurrent.blockingqueue.Producer p2 = new android.test.concurrent.blockingqueue.Producer("p2", BQueue);
                p2.start();
                p1.start();
                android.test.concurrent.blockingqueue.Consumer c1 = new android.test.concurrent.blockingqueue.Consumer("c1", BQueue);
                android.test.concurrent.blockingqueue.Consumer c2 = new android.test.concurrent.blockingqueue.Consumer("c2", BQueue);
                c2.start();
                c1.start();
            }
        });

        Button btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }


}
