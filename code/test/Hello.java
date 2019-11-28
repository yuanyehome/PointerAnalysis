package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;

public class Hello {

    public static void internal() {
        BenchmarkN.alloc(4);
        A a1 = new A();
    }

    public static void main(String[] args) {
        BenchmarkN.alloc(1);
        A a = new A();
        BenchmarkN.test(1, a); // the points-to set now?
    }
}
