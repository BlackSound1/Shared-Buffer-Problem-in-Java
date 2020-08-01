import java.util.ArrayList;

public class Semaphore {
    private int value;
    private ArrayList<Thread> list;

    public Semaphore() {
        value = 0;
        list = null;
    }

    public Semaphore(int value) {
        this.value = value;
        list = null;
    }

    public int getValue() { return value; }

    public void setValue(int value) { this.value = value; }

    public ArrayList<Thread> getList() { return list; }

    public void setList(ArrayList<Thread> list) { this.list = list; }

    public static void wait(Semaphore s){
        while (s.value <= 0){
            ; // busy wait?
        }
        s.value--;
    }

    public static void signal(Semaphore s){
        s.value++;
    }
}
