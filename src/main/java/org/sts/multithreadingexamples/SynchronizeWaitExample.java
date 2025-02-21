package org.sts.multithreadingexamples;

public class SynchronizeWaitExample {
    boolean isCompleted = false;
    static final Object lock = new Object();

    class Taskl implements Runnable{
        public void run() {
            synchronized (lock){
                if (!isCompleted) {
                    try {
                        System.out.println(" isCompleted " + Thread.currentThread().getName() + isCompleted);
                        lock.wait(111);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    class Task2 implements Runnable{
        public void run() {
            synchronized (lock){
                        isCompleted = true;
                         System.out.println(" isCompleted " + Thread.currentThread().getName() + isCompleted);
                        lock.notify();
                        System.out.println(" After lock isCompleted " + Thread.currentThread().getName() + isCompleted);
                }
            }
        }

        public void start(){

            Thread thread1 = new Thread(new Taskl(), "Thread Task1");
            Thread thread2 = new Thread(new Task2(), "Thread Task2");

            thread1.start();
            thread2.start();

        }

    public static void main(String[] args) {
        SynchronizeWaitExample example = new SynchronizeWaitExample();
        example.start();
    }

}
