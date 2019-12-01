package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;

class W {
    A x[];
};

public class MyTest9 {
    public static void main(String[] args) {
        W w = new W();

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

        w.x = new A[3];
        w.x[0] = a;
        w.x[1] = b;
        w.x[2] = c;

        int s = args.length % 3;
        w.x[s] = d;
        int t = (args.length * 17 + 19) % 3;

        w.x[2] = e;

        BenchmarkN.test(1, w.x[0]);
        BenchmarkN.test(2, w.x[1]);
        BenchmarkN.test(3, w.x[2]);
        BenchmarkN.test(4, w.x[t]);

    }
}
/*
 * 1: 1 4 2: 2 4 3: 4 5 // 实际上没有4 4: 1 2 3 4 5
 */