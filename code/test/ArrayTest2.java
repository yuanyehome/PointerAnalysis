package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.B;

class ArrayTest2 {
    static void test(B[] c) {
        BenchmarkN.alloc(1);
        c[0] = new B();
        BenchmarkN.alloc(2);
        c[1] = new B();
    }

    public static void main(String[] args) {
        BenchmarkN.alloc(3);
        B b[] = new B[2];
        test(b);
        BenchmarkN.test(1, b[0]);
        BenchmarkN.test(2, b[1]);
    }
}

/*
1: 0 1
2: 0 1
 */
