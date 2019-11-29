package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;

public class BasicTest {

    public static void internal() {
        BenchmarkN.alloc(4);
        A a1 = new A();
    }

    public static void main(String[] args) {
        BenchmarkN.alloc(1);
        A a = new A();
        BenchmarkN.alloc(2);
        A b = new A();
        BenchmarkN.alloc(3);
        A c = new A();
        BenchmarkN.test(1, a); // the points-to set now?
        if (args.length > 1) a = b;
        if (args.length > 2) b = c;
        BenchmarkN.test(2, a);
        BenchmarkN.test(3, b);
        BenchmarkN.test(4, c);
        if (args.length > 3) c = a;
    }
}

/*
Result:
  1: 1
  2: 1 2
  3: 2 3
  4: 3
*/