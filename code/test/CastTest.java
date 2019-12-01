package test;

import benchmark.internal.Benchmark;
import benchmark.internal.BenchmarkN;
import benchmark.objects.A;

public class CastTest {
    public class MyA {}
    public class MyB extends MyA {}

    public static void main(String[] args) {
        BenchmarkN.alloc(1);
        CastTest t = new CastTest();
        BenchmarkN.alloc(2);
        CastTest t1 = new CastTest();
        BenchmarkN.alloc(3);
        MyA a = t.new MyA();
        BenchmarkN.alloc(4);
        MyA a2 = t.new MyA();
        BenchmarkN.alloc(5);
        MyB b = t.new MyB();
        BenchmarkN.alloc(6);
        MyB b2 = t.new MyB();

        a = b;
        b2 = (MyB) a;
        BenchmarkN.test(1, b2);
        if (args.length > 1)
            b = b2;
        BenchmarkN.test(2, b);
    }
}

