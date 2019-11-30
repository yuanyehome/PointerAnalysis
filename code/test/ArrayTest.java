package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;

public class ArrayTest {

    public static void main(String[] args) {
        BenchmarkN.alloc(1);
        A a[] = new A[10];
        BenchmarkN.alloc(2);
        A b = new A();
        a[3] = b;
        BenchmarkN.test(1, a[2]);
        BenchmarkN.test(2, a[3]);
    }
}