package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;
import benchmark.objects.B;

public class TestLargerField {

    public static class X {
        A a = new A();
    }

    public static void main(String[] args) {
        BenchmarkN.alloc(1);
        X x = new X();
        BenchmarkN.alloc(2);
        A a = new A();
        BenchmarkN.alloc(3);
        B b = new B();
        x.a.f = b;
        BenchmarkN.test(1, x.a.f);
        x.a = a;
        BenchmarkN.test(2, x.a);
        BenchmarkN.test(3, x);
    }
}