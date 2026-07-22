public class DeadLock {

    static Object lock1 = new Object();
    static Object lock2 = new Object();

    public static void main(String[] args){

        Runnable toDoFirst = () -> {
            synchronized(lock1){
                System.out.println("Thread 1: locked lock1!");
                try {
                    Thread.sleep(10);
                    synchronized (lock2){
                        System.out.println("Thread 1: locked lock2!");
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        };

        Runnable toDoSecond = () -> {
            synchronized(lock2){
                System.out.println("Thread 2: locked lock2!");
                try {
                    Thread.sleep(10);
                    synchronized (lock1){
                        System.out.println("Thread 2: locked lock1!");
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Thread thread1= new Thread(toDoFirst);
        Thread thread2= new Thread(toDoSecond);

        thread1.start();
        thread2.start();
    }
}
