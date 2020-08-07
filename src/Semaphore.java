public class Semaphore {
    private volatile int value;

    public Semaphore(int value) {
        this.value = value;
    }

    public int getValue() { return value; }

    public void setValue(int value) { this.value = value; }

    public static void wait(Semaphore s){
        while (s.value <= 0){
            ;
        }
        s.value--;
    }

    public static void signal(Semaphore s){
        s.value++;
    }
}
