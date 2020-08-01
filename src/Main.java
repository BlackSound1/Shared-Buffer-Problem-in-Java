import java.util.Random;
import java.util.Scanner;

public class Main {

    public static final int BUFFERSIZE = 10;
    public static int[] buffer = new int[BUFFERSIZE];
    static Scanner scanner = new Scanner(System.in);

    public static int n = BUFFERSIZE; // Allows empty Semaphore to change
    public static Semaphore mutex = new Semaphore(1);
    public static Semaphore full = new Semaphore(0);
    public static Semaphore empty = new Semaphore(n);

    public static void main(String[] args) {
        //System.out.println("TEST");

        Thread producer = new Thread(new Producer());
        Thread consumer = new Thread(new Consumer());

        zeroBuffer(); // "Empties" buffer by turning all int entries to 0

        float q = setQ(); // Sets the value for q

        // MAIN LOOP
        while (true){
            float P = (float) Math.random();
            float C = (float) Math.random();

            if (P < q){
                //producer.produce();
            }

            if (C < 1 - q){
                //consumer.consume();
            }

        }


    }

    private static void zeroBuffer(){
        for (int i = 0; i < BUFFERSIZE; i++){
            buffer[i] = 0;
        }
    }

    private static float setQ(){
        float q = -1000;
        while (q > 1 || q < 0){
            System.out.print("Select a value of q between 0 and 1: ");
            q = scanner.nextFloat();
        }
        return q;
    }
}
