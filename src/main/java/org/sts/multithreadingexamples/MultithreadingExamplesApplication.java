package org.sts.multithreadingexamples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.RunnableFuture;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

@SpringBootApplication
public class MultithreadingExamplesApplication {

    private static int c = 0;
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    public void increment(){
        lock.lock();
        try{
            c++;
            System.out.println(Thread.currentThread().getName() + " " + c);
            sleep(1000);
            condition.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void runTask(){
        Runnable task = () -> {
            for (int i = 0; i < 10; i++) {
                increment();
       /*         try {
                    wait(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }*/
            }
        };
        Thread t1 = new Thread(task, "Trhread 1 ");
        Thread t2 = new Thread(task, "Trhread 2 ");

        t1.start();
        t2.start();
    }

    public static void main(String[] args) {

        MultithreadingExamplesApplication app = new MultithreadingExamplesApplication();
        app.runTask();

 //       SpringApplication.run(MultithreadingExamplesApplication.class, args);
    }

}
