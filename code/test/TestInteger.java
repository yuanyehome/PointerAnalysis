package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;

public class TestInteger {

    public static void main(String[] args) {
        BenchmarkN.alloc(1);
        A a = new A();
        BenchmarkN.alloc(2);
        A b = new A();
        BenchmarkN.alloc(3);
        A c = new A();
        Integer x = 3;
        BenchmarkN.test(1, a);
        if (args.length > 1) a = b;
        if (args.length > 2) b = c;
        BenchmarkN.test(2, a);
        BenchmarkN.test(3, b);
        BenchmarkN.test(4, c);
        BenchmarkN.test(5, x);
    }
}

/*
Result:
  1: 1
  2: 1 2
  3: 2 3
  4: 3
*/