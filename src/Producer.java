public class Producer implements Runnable{
    public Producer() {
    }

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
            //float P = 0;
            float P = (float) Math.random(); // Generate random P
            System.out.println("P = " + Main.numberFormat.format(P));

            // Checks P against q
            if (P < Main.q){
                System.out.println("P < q");
                //System.out.println("PRODUCER: Mutex at beginning of loop is " + Main.mutex.getValue());

                int newNum = produce(); // creates a new item to add to the buffer. (Simply creates a new int 1)

                // Displays info about the emptiness of the buffer and waits if necessary
                System.out.println("PRODUCER: The value of empty is now: " + Main.empty.getValue());
                if (Main.empty.getValue() == 0){
                    System.out.println("PRODUCER: BUSY-WAITING");
                }
                Semaphore.wait(Main.empty);

                // Engages mutex lock if necessary
                //System.out.println("PRODUCER: Before wait: The value of mutex is now: " + Main.mutex.getValue());
                Semaphore.wait(Main.mutex);
                System.out.println("PRODUCER: After wait: The value of mutex is now: " + Main.mutex.getValue());

                addToBuffer(newNum); // Adds the created int 1 to the buffer.

                // Disengages mutex lock if necessary
                //System.out.println("PRODUCER: Before signal: The value of mutex is now: " + Main.mutex.getValue());
                Semaphore.signal(Main.mutex);
                System.out.println("PRODUCER: After signal: The value of mutex is now: " + Main.mutex.getValue());

                // Displays info about the fullness of the buffer
                Semaphore.signal(Main.full);
                System.out.println("PRODUCER: The value of full is now: " + Main.full.getValue());
                /*if (Main.full.getValue() < Main.n){
                    System.out.println("PRODUCER: STOPPING BUSY-WAITING");
                }*/

                if (Main.full.getValue() == Main.n){
                    System.out.println("PRODUCER: The buffer is full!");
                }

                //System.out.println("PRODUCER: Mutex at beginning of loop is " + Main.mutex.getValue());
            }else {
                // If P <= q, displays a message
                System.out.println("PRODUCER: P is not > than q! Producer doesn't produce...");
            }
        }
    }
}
