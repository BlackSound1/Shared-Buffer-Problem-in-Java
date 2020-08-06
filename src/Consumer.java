public class Consumer implements Runnable{
    /*private final Semaphore mutex;
    private final Semaphore full;
    private final Semaphore empty;*/

    public Consumer() {
        /*mutex = Main.mutex;
        full = Main.full;
        empty = Main.empty;*/
    }

    /*public Semaphore getMutex() { return mutex; }

    public Semaphore getFull() { return full; }

    public Semaphore getEmpty() { return empty; }*/

    public void consume(){
        for (int i = Main.buffer.length-1; i >= 0 ; i--){
            if (Main.buffer[i] == 1){
                Main.buffer[i] = 0;
                break;
            }
        }
        System.out.println("CONSUMER: An item has been removed from the buffer!");
    }

    @Override
    public void run() {
        while (true){
            float C = 0;
            // float C = (float) Math.random();

            if (C < 1 - Main.q) {
                System.out.println("C < 1 - q");

                System.out.println("CONSUMER: The value of full is now: " + Main.full.getValue());
                if (Main.full.getValue() == Main.n){
                    System.out.println("CONSUMER: MUST WAIT");
                }
                Semaphore.wait(Main.full);

                System.out.println("CONSUMER: The value of mutex is now: " + Main.mutex.getValue());
                Semaphore.wait(Main.mutex);

                consume();

                Semaphore.signal(Main.mutex);
                System.out.println("CONSUMER: The value of mutex is now: " + Main.mutex.getValue());

                Semaphore.signal(Main.empty);
                System.out.println("CONSUMER: The value of empty is now: " + Main.empty.getValue());
                if (Main.empty.getValue() < Main.n){
                    System.out.println("CONSUMER: MAY STOP WAITING");
                }

                //System.out.println("CONSUMER: Consumer has consumed the last item in the buffer!");

                if (Main.empty.getValue() == 0){
                    System.out.println("CONSUMER: The buffer is empty!");
                }
            }
        }
    }
}
