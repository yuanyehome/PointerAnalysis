package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;
import benchmark.objects.B;

public class StaticFieldTest {

    public static void main(String[] args) {
        A[] a = new A[10];
        BenchmarkN.alloc(1);
        B b = new B();
        BenchmarkN.alloc(2);
        a[0] = new A(b);
        B b2 = a[1].f;
        B b3 = b2;
        BenchmarkN.test(1, a[2]);
        BenchmarkN.test(2, a[3].f);
        BenchmarkN.test(3, b);
        BenchmarkN.test(4, b2);
        BenchmarkN.test(5, b3);
    }
}