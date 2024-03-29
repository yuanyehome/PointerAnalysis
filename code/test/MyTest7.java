//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package test;

import benchmark.internal.Benchmark;
import benchmark.objects.A;

public class MyTest7 {
    public MyTest7() {
    }

    public static void newA() {
        new A();
    }

    public static void main(String[] args) {
        while(args.length > 100) {
            new A();
        }

        while(args.length > 50) {
            newA();
        }

        Benchmark.alloc(1);
        A a = new A();
        Benchmark.alloc(2);
        A b = new A();
        Benchmark.alloc(3);
        A c = new A();
        A[] x = new A[]{a, b, c};
        Benchmark.test(1, x[0]);
        Benchmark.test(2, x[1]);
        Benchmark.test(3, x[2]);
        Benchmark.alloc(4);
        a = new A();
        Benchmark.alloc(5);
        b = new A();
        Benchmark.alloc(6);
        c = new A();
        A[] z = x;
        A[] y = new A[3];
        if (args.length > 10) {
            x = y;
        }

        x[0] = a;
        x[1] = b;
        x[2] = c;
        Benchmark.alloc(7);
        a = new A();
        Benchmark.alloc(8);
        b = new A();
        Benchmark.alloc(9);
        c = new A();
        y[0] = a;
        y[1] = b;
        y[2] = c;
        Benchmark.test(4, x[0]);
        Benchmark.test(5, x[1]);
        Benchmark.test(6, x[2]);
        Benchmark.test(7, y[0]);
        Benchmark.test(8, y[1]);
        Benchmark.test(9, y[2]);
        Benchmark.test(10, z[0]);
        Benchmark.test(11, z[1]);
        Benchmark.test(12, z[2]);
        int s = args.length % 3;
        int t = (args.length * 17 + 19) % 3;
        y[s] = new A();
        y[s] = new A();
        y[s] = new A();
        y[s] = new A();
        y[s] = new A();
        Benchmark.test(14, x[0]);
        Benchmark.test(15, x[1]);
        Benchmark.test(16, x[2]);
        Benchmark.test(17, y[0]);
        Benchmark.test(18, y[1]);
        Benchmark.test(19, y[2]);
        Benchmark.test(13, y[t]);
        Benchmark.test(20, x[t]);
    }
}
