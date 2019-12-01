package test;

import benchmark.internal.Benchmark;
import benchmark.internal.BenchmarkN;
import benchmark.objects.B;

class MultiArrayTest {
    public static void main(String[] args) {
        BenchmarkN.alloc(1);
        B b0 = new B();
        BenchmarkN.alloc(2);
        B[][] b = new B[2][3];
        b[1][2] = b0;
        BenchmarkN.test(1, b);          // 2
        BenchmarkN.test(2, b[1][2]);    // 1 (0 1 for us)
        BenchmarkN.test(3, b[1][1]);    // 1 (0 1 for us)
    }
}

/*
1: 0 1
2: 0 1
 */
