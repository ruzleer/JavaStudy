package HW4.src;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LiveLock {

     static Lock lock1 = new ReentrantLock();
     static Lock lock2 = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        Runnable lockFirst = () -> {

            while(true){
                try {
                    if(lock1.tryLock(1, TimeUnit.MILLISECONDS)){
                        Thread.sleep(200);
                        System.out.println("First locked!");
                        if(lock2.tryLock(1, TimeUnit.MILLISECONDS)){
                            System.out.println("Second locked!");
                            lock2.unlock();
                            lock1.unlock();
                            break;
                        }
                        else{
                            System.out.println("First unlocking!");
                            lock1.unlock();
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Runnable lockSecond = () -> {

            while(true){
                try {
                    if(lock2.tryLock(1, TimeUnit.MILLISECONDS)){
                        Thread.sleep(200);
                        System.out.println("Second locked!");
                        if(lock1.tryLock(1, TimeUnit.MILLISECONDS)){
                            System.out.println("First locked!");
                            lock1.unlock();
                            lock2.unlock();
                            break;
                        }
                        else{
                            System.out.println("Second unlocking!");
                            lock2.unlock();
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Thread first = new Thread(lockFirst);
        Thread second = new Thread(lockSecond);

        first.start();
        second.start();
    }
}
