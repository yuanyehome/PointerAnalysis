package test;

import java.util.*;

public class yytest3 {
    public static class A {
        int x = 1;
    }

    public static void main(String[] args) {
        List<A> arr = new ArrayList<A>();
        A tmp = new A();
        arr.add(0, tmp);
        A tmp2 = arr.get(0);
        tmp2.x = 1000;
        System.out.println(arr.get(0).x);
    }
}