package HW4.src;


public class ThreadExample {

    static Object lock = new Object();
    static boolean isOneTurn = true;

    static Runnable printOne = () -> {
        while(true){
            synchronized (lock){
                if(isOneTurn){
                    System.out.println(1);
                    isOneTurn = false;
                    lock.notify();
                }
                else{
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    };

    static Runnable printTwo = () -> {
        while(true){
            synchronized (lock){
                if(!isOneTurn){
                    System.out.println(2);
                    isOneTurn = true;
                    lock.notify();
                }
                else{
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    };

    public static void main(String[] args){
        Thread first = new Thread(printOne);
        Thread second = new Thread(printTwo);

        first.start();
        second.start();
    }
}
