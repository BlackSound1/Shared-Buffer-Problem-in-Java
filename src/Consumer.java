public class Consumer implements Runnable{
    private Semaphore mutex;
    private Semaphore full;
    private Semaphore empty;

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
    }

    @Override
    public void run() {
        while (true){
            Semaphore.wait(full);
            Semaphore.wait(mutex);

            consume();

            Semaphore.signal(mutex);
            Semaphore.signal(empty);

            System.out.println("Consumer has consumed the last item in the buffer!");
        }
    }
}
