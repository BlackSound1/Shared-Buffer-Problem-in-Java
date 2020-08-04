public class Consumer implements Runnable{
    private final Semaphore mutex;
    private final Semaphore full;
    private final Semaphore empty;

    public Consumer() {
        mutex = Main.mutex;
        full = Main.full;
        empty = Main.empty;
    }

    public Semaphore getMutex() { return mutex; }

    public Semaphore getFull() { return full; }

    public Semaphore getEmpty() { return empty; }

    public void consume(){
        for (int i = Main.BUFFERSIZE - 1; i > 0 ; i--){
            if (Main.buffer[i] == 1){
                Main.buffer[i] = 0;
                break;
            }
        }
        System.out.println("An item has been removed from the buffer!");
    }

    @Override
    public void run() {
        while (true){
            float C = (float) Math.random();

            if (Main.q == 0 || C < 1 - Main.q) {
                Semaphore.wait(full);
                System.out.println("The value of full is now: " + Main.full.getValue());

                Semaphore.wait(mutex);
                System.out.println("The value of mutex is now: " + Main.mutex.getValue());

                consume();

                Semaphore.signal(mutex);
                System.out.println("The value of mutex is now: " + Main.mutex.getValue());

                Semaphore.signal(empty);
                System.out.println("The value of empty is now: " + Main.empty.getValue());

                System.out.println("Consumer has consumed the last item in the buffer!");

                if (Main.empty.getValue() == Main.BUFFERSIZE){
                    System.out.println("The buffer is empty!");
                }
            }
        }
    }
}
