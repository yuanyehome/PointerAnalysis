package test;

public class ArrayTestFromYY {
    public static void main(String[] args) {
        Integer[] a = new Integer[7];
        Integer[] b = new Integer[5];
        b = a;
        Integer x = 4;
        a[1] = x;
        x = 3;
        System.out.println(a[1]);
    }
}