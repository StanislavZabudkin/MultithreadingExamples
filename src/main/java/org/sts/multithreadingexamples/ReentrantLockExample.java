package org.sts.multithreadingexamples;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class ReentrantLockExample {
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    boolean isComplete = false;

    class Task implements Runnable {
    public void run() {
        lock.lock();

        try {
            while (!isComplete) {
                System.out.println("Task 1 completed "+ isComplete);
                sleep(150);
                condition.await();
            }

        }catch(InterruptedException e){
            System.out.println(e.getMessage());
        }finally {
            lock.unlock();
        }
    }
    }


    public class doSomethingFirst implements Runnable {
        public void run(){
            lock.lock();
            try {
                isComplete = true;
                System.out.println("doSomethingFirst completed " + isComplete);
                condition.signal();
            }finally {
                lock.unlock();
            }
        }
    }

    public void start(){
        Thread thread1 = new Thread(new Task(), "trhead 1");
        Thread thread2 = new Thread(new doSomethingFirst(), "trhead 2");

        thread1.start();
        thread2.start();
    }

    public static void main(String[] args) {
        ReentrantLockExample example = new ReentrantLockExample();
        example.start();
    }
}
