public class Semaphore {
    private int value;

    /*public Semaphore() {
        value = 0;
    }*/

    public Semaphore(int value) {
        this.value = value;
    }

    public int getValue() { return value; }

    public void setValue(int value) { this.value = value; }

    public static void wait(Semaphore s){
        while (s.value <= 0){
            ;
        }
        //System.out.println("BUSY WAITING!! Value of this Semaphore is " + s.value);
            /*try {
                Thread.sleep(500); // busy wait?
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

        s.value--;
    }

    public static void signal(Semaphore s){
        s.value++;
    }
}
