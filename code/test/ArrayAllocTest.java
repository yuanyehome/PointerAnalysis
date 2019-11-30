package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;

public class ArrayAllocTest {

    public static void main(String[] args) {
        BenchmarkN.alloc(1);
        A a[] = new A[3];
        BenchmarkN.test(1, a);
        BenchmarkN.test(2, a[0]);
        BenchmarkN.test(3, a[1]);
        BenchmarkN.test(4, a[2]);
        BenchmarkN.alloc(2);
        A b = new A();
        a[1] = b;
        BenchmarkN.test(5, a);
        BenchmarkN.test(6, a[0]);
        BenchmarkN.test(7, a[1]);
        BenchmarkN.test(8, a[2]);
        a[0] = b;
        BenchmarkN.test(9, a);
        BenchmarkN.test(10, a[0]);
        BenchmarkN.test(11, a[1]);
        BenchmarkN.test(12, a[2]);
    }
}