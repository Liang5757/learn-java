package test;

public class Main {

    private static int NUM = 100000;

    public static void main(String[] args) {
        System.out.println(NUM + ":");
        testString();
        testStringBuffer();
        testStringBuilder();
    }

    private static void testString() {
        long start = System.currentTimeMillis();
        String str = "";
        for (int i = 0; i < NUM; i++) {
            str += i;
        }
        long end = System.currentTimeMillis();
        System.out.println("String:" + (end - start));
    }

    private static void testStringBuffer() {
        long start = System.currentTimeMillis();
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < NUM; i++) {
            str.append(i);
        }
        long end = System.currentTimeMillis();
        System.out.println("StringBuffer:" + (end - start));
    }

    private static void testStringBuilder() {
        long start = System.currentTimeMillis();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < NUM; i++) {
            str.append(i);
        }
        long end = System.currentTimeMillis();
        System.out.println("StringBuilder:" + (end - start));
    }
}
