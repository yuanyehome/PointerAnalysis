package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;
import benchmark.objects.B;

public class yytest1 {

    public static void main(String[] args) {
        BenchmarkN.alloc(1);
        A a1 = new A();
        BenchmarkN.alloc(2);
        A a2 = new A();
        BenchmarkN.alloc(3);
        B b = new B();
        a1.f = a2.f;
        a1 = a2;
        BenchmarkN.test(1, a1);
        BenchmarkN.test(2, b);
        BenchmarkN.test(3, a1.f);
    }
}
