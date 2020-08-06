public class Producer implements Runnable{
    /*private final Semaphore mutex;
    private final Semaphore full;
    private final Semaphore empty;*/

    public Producer() {
        /*mutex = Main.mutex;
        full = Main.full;
        empty = Main.empty;*/
    }

    /*public Semaphore getMutex() { return mutex; }

    public Semaphore getFull() { return full; }

    public Semaphore getEmpty() { return empty; }*/

    public int produce(){ return 1; }

    public void addToBuffer(int newNum){
        for (int i = 0; i < Main.BUFFERSIZE; i++){
            if (Main.buffer[i] == 0){
                Main.buffer[i] = newNum;
                break;
            }
        }
        System.out.println("PRODUCER: Producer added a new item to end of buffer!");
    }

    @Override
    public void run() {
        while (true){
            float P = 0;
            //float P = (float) Math.random();

            if (P < Main.q){
                System.out.println("P < q");

                int newNum = produce();

                System.out.println("PRODUCER: The value of empty is now: " + Main.empty.getValue());
                if (Main.empty.getValue() == 0){
                    System.out.println("PRODUCER: MUST WAIT");
                }
                Semaphore.wait(Main.empty);

                System.out.println("PRODUCER: Before wait: The value of mutex is now: " + Main.mutex.getValue());
                Semaphore.wait(Main.mutex);
                System.out.println("PRODUCER: After wait: The value of mutex is now: " + Main.mutex.getValue());

                addToBuffer(newNum);

                System.out.println("PRODUCER: Before signal: The value of mutex is now: " + Main.mutex.getValue());
                Semaphore.signal(Main.mutex);
                System.out.println("PRODUCER: After signal: The value of mutex is now: " + Main.mutex.getValue());

                Semaphore.signal(Main.full);
                System.out.println("PRODUCER: The value of full is now: " + Main.full.getValue());
                if (Main.full.getValue() < Main.n){
                    System.out.println("PRODUCER: CAN STOP WAITING");
                }

                if (Main.full.getValue() == Main.BUFFERSIZE - 1){
                    System.out.println("PRODUCER: The buffer is full!");
                }
            }
        }
    }
}
