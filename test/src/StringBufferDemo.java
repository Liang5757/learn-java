import java.util.Arrays;

public class StringBufferDemo {
    public static void main(String[] args) {
        String a1 = new String("a").intern();
        String a2 = new String("a").intern();
        System.out.println(a1.equals(a2));
    }
}