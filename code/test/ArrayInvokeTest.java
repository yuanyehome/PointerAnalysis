package test;

import benchmark.internal.Benchmark;
import benchmark.internal.BenchmarkN;
import benchmark.objects.A;

public class ArrayInvokeTest {

    private static void f(A a) {
        BenchmarkN.test(1, a);
    }

    private static A g(A[] as) {
        BenchmarkN.alloc(3);
        A c = new A();
        as[1] = c;
        BenchmarkN.alloc(4);
        A d = new A();
        return d;
    }

    public static void main(String[] args) {
        BenchmarkN.alloc(1);
        A[] a = new A[5];
        BenchmarkN.alloc(2);
        A b = new A();
        a[0] = b;
        f(a[0]);

        a[2] = g(a);
        BenchmarkN.test(2, a[1]);
        BenchmarkN.test(3, a[2]);
    }
}