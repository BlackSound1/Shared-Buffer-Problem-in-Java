import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {

    // Creating useful static variables
    public static final int BUFFERSIZE = 10;
    public static int[] buffer = new int[BUFFERSIZE];
    public static DecimalFormat numberFormat = new DecimalFormat("#.00");

    public static float q;

    public static int n = BUFFERSIZE - 1; // Allows empty Semaphore to change

    // Creating Semaphores
    public static Semaphore mutex = new Semaphore(1);
    public static Semaphore full = new Semaphore(0);
    public static Semaphore empty = new Semaphore(n);

    public static void main(String[] args) {
        // Creating the Threads
        Thread producer = new Thread(new Producer());
        Thread consumer = new Thread(new Consumer());

        zeroBuffer(); // "Empties" buffer by turning all int entries to 0

        q = setQ(); // Sets the value for q

        producer.start();
        consumer.start();
    }

    private static void zeroBuffer(){
        for (int i = 0; i < BUFFERSIZE; i++){
            buffer[i] = 0;
        }
    }

    private static float setQ(){
        Scanner scanner = new Scanner(System.in);
        float q = -1000;
        while (q > 1 || q < 0){
            System.out.print("Select a value of q between 0 and 1: ");
            q = scanner.nextFloat();
        }
        scanner.close();
        return q;
    }
}
