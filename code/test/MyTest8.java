package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;

public class MyTest8 {
    public static void main(String[] args) {

        BenchmarkN.alloc(1);
        A a = new A();
        BenchmarkN.alloc(2);
        A b = new A();
        BenchmarkN.alloc(3);
        A c = new A();
        BenchmarkN.alloc(4);
        A d = new A();
        BenchmarkN.alloc(5);
        A e = new A();

        A x[] = new A[3];
        x[0] = a;
        x[1] = b;
        x[2] = c;

        int s = args.length % 3;
        x[s] = d;
        int t = (args.length * 17 + 19) % 3;

        x[2] = e;

        BenchmarkN.test(1, x[0]);
        BenchmarkN.test(2, x[1]);
        BenchmarkN.test(3, x[2]);
        BenchmarkN.test(4, x[t]);

    }
}
/*
 * 1: 1 4 2: 2 4 3: 4 5 // 实际上没有4 4: 1 2 3 4 5
 */