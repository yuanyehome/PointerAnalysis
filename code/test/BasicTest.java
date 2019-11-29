package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;

public class BasicTest {

    public static void main(String[] args) {
        BenchmarkN.alloc(1);
        A a = new A();
        BenchmarkN.alloc(2);
        A b = new A();
        BenchmarkN.alloc(3);
        A c = new A();
        BenchmarkN.test(1, a);
        if (args.length > 1) a = b;
        BenchmarkN.test(2, a);
        BenchmarkN.test(3, c);
    }
}

/*
Result:
  1: 1
  2: 1 2
  3: 2 3
  4: 3
*/