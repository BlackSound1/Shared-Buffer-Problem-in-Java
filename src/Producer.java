public class Producer implements Runnable{
    private Semaphore mutex;
    private Semaphore full;
    private Semaphore empty;
    private int q;

    public Producer() {
        mutex = Main.mutex;
        full = Main.full;
        empty = Main.empty;
    }

    public Semaphore getMutex() { return mutex; }

    public Semaphore getFull() { return full; }

    public Semaphore getEmpty() { return empty; }

    public int getQ() { return q; }

    public void setQ(int q) { this.q = q; }

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
            int newNum = produce();

            Semaphore.wait(empty);
            Semaphore.wait(mutex);

            addToBuffer(newNum);

            Semaphore.signal(mutex);
            Semaphore.signal(full);
        }
    }

}
