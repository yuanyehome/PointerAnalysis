package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;

public class MyTest7 {

    public static void newA() {
        new A();
    }

    public static void main(String[] args) {

        while (args.length > 100) {
            new A();
        }
        while (args.length > 50) {
            newA();
        }

        BenchmarkN.alloc(1);
        A a = new A();
        BenchmarkN.alloc(2);
        A b = new A();
        BenchmarkN.alloc(3);
        A c = new A();

        A x[] = new A[3];
        x[0] = a;
        x[1] = b;
        x[2] = c;

        BenchmarkN.test(1, x[0]);
        BenchmarkN.test(2, x[1]);
        BenchmarkN.test(3, x[2]);

        BenchmarkN.alloc(4);
        a = new A();
        BenchmarkN.alloc(5);
        b = new A();
        BenchmarkN.alloc(6);
        c = new A();

        A z[] = x;
        A y[] = new A[3];

        if (args.length > 10)
            x = y;

        x[0] = a;
        x[1] = b;
        x[2] = c;

        BenchmarkN.alloc(7);
        a = new A();
        BenchmarkN.alloc(8);
        b = new A();
        BenchmarkN.alloc(9);
        c = new A();

        y[0] = a;
        y[1] = b;
        y[2] = c;

        BenchmarkN.test(4, x[0]);
        BenchmarkN.test(5, x[1]);
        BenchmarkN.test(6, x[2]);

        BenchmarkN.test(7, y[0]);
        BenchmarkN.test(8, y[1]);
        BenchmarkN.test(9, y[2]);

        BenchmarkN.test(10, z[0]);
        BenchmarkN.test(11, z[1]);
        BenchmarkN.test(12, z[2]);

        int s = args.length % 3;

        int t = (args.length * 17 + 19) % 3;

        y[s] = new A();
        y[s] = new A();
        y[s] = new A();
        y[s] = new A();
        y[s] = new A();

        BenchmarkN.test(14, x[0]);
        BenchmarkN.test(15, x[1]);
        BenchmarkN.test(16, x[2]);

        BenchmarkN.test(17, y[0]);
        BenchmarkN.test(18, y[1]);
        BenchmarkN.test(19, y[2]);

        BenchmarkN.test(13, y[t]);
        BenchmarkN.test(20, x[t]);
    }
}
/*
 * == with fallback == 1: 1 2: 2 3: 3 4: 1 4 7 5: 2 5 8 6: 3 6 9 7: 7 8: 8 9: 9
 * 10: 1 4 11: 2 5 12: 3 6 13: 0 4 5 6 7 8 9 14: 0 1 4 7 15: 0 2 5 8 16: 0 3 6 9
 * 17: 0 7 18: 0 8 19: 0 9 20: 0 1 2 3 4 5 6 7 8 9
 */