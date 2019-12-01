package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;
import benchmark.objects.B;

public class StaticFieldTest {

    static A a;
    public static void main(String[] args) {
        BenchmarkN.alloc(1);
        B b = new B();
        BenchmarkN.alloc(2);
        a = new A(b);
        B b2 = a.f;
        B b3 = b2;
        BenchmarkN.test(1, a);
        BenchmarkN.test(2, a.f);
        BenchmarkN.test(3, b);
        BenchmarkN.test(4, b2);
        BenchmarkN.test(5, b3);
    }
}