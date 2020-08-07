import java.text.DecimalFormat;

public class Consumer implements Runnable{
    public Consumer() {
    }

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
            //float C = 0;
            float C = (float) Math.random(); // Creates random C
            System.out.println("C = " + Main.numberFormat.format(C));

            // Checks C against q
            if (C < 1 - Main.q) {
                System.out.println("C < 1 - q");
                //System.out.println("CONSUMER: Mutex at beginning of loop is " + Main.mutex.getValue());

                // Displays info about fullness of buffer and waits if necessary
                System.out.println("CONSUMER: The value of full is now: " + Main.full.getValue());
                if (Main.full.getValue() == Main.n){
                    System.out.println("CONSUMER: BUSY-WAITING");
                }
                Semaphore.wait(Main.full);

                // Engages mutex lock if necessary
                //System.out.println("CONSUMER: Before wait: The value of mutex is now: " + Main.mutex.getValue());
                Semaphore.wait(Main.mutex);
                System.out.println("CONSUMER: After wait: The value of mutex is now: " + Main.mutex.getValue());

                consume(); // removes item from buffer

                // Disengages mutex lock if necessary
                //System.out.println("CONSUMER:  Before signal: The value of mutex is now: " + Main.mutex.getValue());
                Semaphore.signal(Main.mutex);
                System.out.println("CONSUMER: After signal: The value of mutex is now: " + Main.mutex.getValue());

                // Displays info about the emptiness of the buffer
                Semaphore.signal(Main.empty);
                System.out.println("CONSUMER: The value of empty is now: " + Main.empty.getValue());
                /*if (Main.empty.getValue() < Main.n){
                    System.out.println("CONSUMER: STOPPING BUSY-WAITING");
                }*/

                //System.out.println("CONSUMER: Consumer has consumed the last item in the buffer!");

                if (Main.empty.getValue() == Main.n){
                    System.out.println("CONSUMER: The buffer is empty!");
                }

                //System.out.println("CONSUMER: Mutex at end of loop is " + Main.mutex.getValue());
            }else {
                // If C >= 1 - q: display a message
                System.out.println("CONSUMER: C is not < 1 - q! Consumer does not consume...");
            }
        }
    }
}
