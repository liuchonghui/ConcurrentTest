package android.test.concurrentuse;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {

    int priority = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinkedBlockingDeque<Runnable> queue = new LinkedBlockingDeque<Runnable>();
        final ExecutorService executor = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.DAYS, queue, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "ADM download-worker");
                thread.setPriority(Thread.MAX_PRIORITY - 1);
                return thread;
            }

        });

        final Task task1 = new Task(1);

        Button btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executor.execute(task1);
                Log.d("Task", "after execute task1, queue size|" + queue.size());
                executor.execute(new Task(2));
                Log.d("Task", "after execute task2, queue size|" + queue.size());
                executor.execute(new Task(3));
                Log.d("Task", "after execute task3, queue size|" + queue.size());
                executor.execute(new Task(4));
                Log.d("Task", "after execute task4, queue size|" + queue.size());
                int i = 0;
                BlockingQueue q = ((ThreadPoolExecutor) executor).getQueue();
                Iterator iter = q.iterator();
                while(iter.hasNext()){
                    Object o =  iter.next();
                    if (o instanceof Task) {
                        Log.d("Task", "queue[" + i++ + "] is Task" + ((Task) o).getNum());
                    }
                }
            }
        });
        final Task task7 = new Task(7);
        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executor.execute(new Task(5));
                Log.d("Task", "after execute task5, queue size|" + queue.size());
                executor.execute(new Task(6));
                Log.d("Task", "after execute task6, queue size|" + queue.size());
                executor.execute(task7);
                Log.d("Task", "after execute task7, queue size|" + queue.size());
                int i = 0;
                BlockingQueue q = ((ThreadPoolExecutor) executor).getQueue();
                Iterator iter = q.iterator();
                while(iter.hasNext()){
                    Object o =  iter.next();
                    if (o instanceof Task) {
                        Log.d("Task", "queue[" + i++ + "] is Task" + ((Task) o).getNum());
                    }
                }
            }
        });

        Button btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (queue.contains(task7)) {
                    Log.d("Task", "contains task" + task7.getNum());
                    queue.remove(task7);
                }
                queue.addFirst(task7);
                Log.d("Task", "add-first task" + task7.getNum());
                task1.setInterrupt();
                executor.execute(task1);
                Log.d("Task", "after execute task1, queue size|" + queue.size());
                int i = 0;
                BlockingQueue q = ((ThreadPoolExecutor) executor).getQueue();
                Iterator iter = q.iterator();
                while(iter.hasNext()){
                    Object o =  iter.next();
                    if (o instanceof Task) {
                        Log.d("Task", "queue[" + i++ + "] is Task" + ((Task) o).getNum());
                    }
                }
            }
        });

        Button btn4 = (Button) findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////

        final PriorityBlockingQueue<Runnable> queue2 = new PriorityBlockingQueue<>(28, comparator);
        final ExecutorService executor2 = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.DAYS, queue2, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "ADM download-worker");
                thread.setPriority(Thread.MAX_PRIORITY - 1);
                return thread;
            }
        }) {
            @Override
            public Future<?> submit(Runnable task) {
                execute(task);
                return null;
            }
        };

        Button btn5 = (Button) findViewById(R.id.btn5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executor2.submit(new Task(1));
                Log.d("Task", "after submit task1, queue2 size|" + queue2.size());
                executor2.submit(new Task(2));
                Log.d("Task", "after submit task2, queue2 size|" + queue2.size());
                executor2.submit(new Task(3));
                Log.d("Task", "after submit task3, queue2 size|" + queue2.size());
                executor2.submit(new Task(4));
                Log.d("Task", "after submit task4, queue2 size|" + queue2.size());
                int i = 0;
                BlockingQueue q = ((ThreadPoolExecutor) executor2).getQueue();
                Iterator iter = q.iterator();
                while(iter.hasNext()){
                    Object o =  iter.next();
                    if (o instanceof Task) {
                        Log.d("Task", "queue2[" + i++ + "] is Task" + ((Task) o).getNum());
                    }
                }
            }
        });
        Button btn6 = (Button) findViewById(R.id.btn6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executor2.submit(new Task(5));
                Log.d("Task", "after submit task5, queue2 size|" + queue2.size());
                executor2.submit(new Task(6));
                Log.d("Task", "after submit task6, queue2 size|" + queue2.size());
                executor2.submit(task7);
                Log.d("Task", "after submit task7, queue2 size|" + queue2.size());
                int i = 0;
                BlockingQueue q = ((ThreadPoolExecutor) executor2).getQueue();
                Iterator iter = q.iterator();
                while(iter.hasNext()){
                    Object o =  iter.next();
                    if (o instanceof Task) {
                        Log.d("Task", "queue2[" + i++ + "] is Task" + ((Task) o).getNum());
                    }
                }
            }
        });

        Button btn7 = (Button) findViewById(R.id.btn7);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task7.setPrority(priority++);
                queue2.comparator();
//                executor2.submit(task7);
//                Log.d("Task", "after submit task7, queue2 size|" + queue2.size());
//                int i = 0;
//                BlockingQueue q = ((ThreadPoolExecutor) executor2).getQueue();
//                Iterator iter = q.iterator();
//                while(iter.hasNext()){
//                    Object o =  iter.next();
//                    if (o instanceof Task) {
//                        Log.d("Task", "queue2[" + i++ + "] is Task" + ((Task) o).getNum() + "|" + ((Task) o).getPrority());
//                    }
//                }
            }
        });

        Button btn8 = (Button) findViewById(R.id.btn8);
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    Comparator comparator = new Comparator<Task>() {
        @Override
        public int compare(Task t1, Task t2) {
            return t2.getPrority() - t1.getPrority();
        }

        @Override
        public boolean equals(Object obj) {
            return false;
        }
    };

    class Task implements Runnable {
        private int num;
        private int prority;
        private boolean interrupt = false, isRunning = false;
        public Task(int num) {
            this.num = num;
        }
        public void setPrority(int prority) {
            this.prority = prority;
        }
        public int getPrority() {
            return this.prority;
        }
        public int getNum() {
            return this.num;
        }
        public void setInterrupt() {
            if (!isRunning) {
                return;
            }
            interrupt = true;
        }
        public boolean isRunning() {
            return isRunning;
        }
        @Override
        public void run() {
            isRunning = true;
            Log.d("Task", "Task(" + num + ") is running");
            while (!interrupt) {
                try {
                    Thread.sleep(100L);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
            Log.d("Task", "Task(" + num + ") is interrupted");
        }
    }

}
