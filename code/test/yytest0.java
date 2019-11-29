package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;

public class yytest0 {

    public static void internal() {
        BenchmarkN.alloc(4);
        A a1 = new A();
    }

    public static void main(String[] args) {

    }
}
