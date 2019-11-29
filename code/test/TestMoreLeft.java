package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;
import benchmark.objects.B;

public class TestMoreLeft {

    public static void main(String[] args) {
        BenchmarkN.alloc(1);
        A a = new A();
        BenchmarkN.alloc(2);
        B b = new B();
        B c = new B();
        BenchmarkN.alloc(3);
        B d = new B();
        if (args.length > 1) b = c;
        a.f = b;
        if (args.length > 1) a.f = d;
        BenchmarkN.test(1, a.f);
        BenchmarkN.test(2, b);
    }
}
/*
Result:
1: 0 2 3
2: 0 2
 */