public class Producer implements Runnable{
    private Semaphore mutex;
    private Semaphore full;
    private Semaphore empty;

    public Producer() {
        mutex = Main.mutex;
        full = Main.full;
        empty = Main.empty;
    }

    public Semaphore getMutex() { return mutex; }

    public Semaphore getFull() { return full; }

    public Semaphore getEmpty() { return empty; }

    public int produce(){ return 1; }

    public void addToBuffer(int newNum){
        for (int i = 0; i < Main.BUFFERSIZE; i++){
            if (Main.buffer[i] == 0){
                Main.buffer[i] = newNum;
                break;
            }
        }
        System.out.println("Producer added a new item to end of buffer!");
    }

    @Override
    public void run() {
        while (true){
            float P = (float) Math.random();

            if (Main.q == 1 || P < Main.q){
                int newNum = produce();

                Semaphore.wait(empty);
                System.out.println("The value of empty is now: " + Main.empty.getValue());

                Semaphore.wait(mutex);
                System.out.println("The value of mutex is now: " + Main.mutex.getValue());

                addToBuffer(newNum);

                Semaphore.signal(mutex);
                System.out.println("The value of mutex is now: " + Main.mutex.getValue());

                Semaphore.signal(full);
                System.out.println("The value of full is now: " + Main.full.getValue());

                if (Main.full.getValue() == 0){
                    System.out.println("The buffer is full!");
                }
            }
        }
    }
}
