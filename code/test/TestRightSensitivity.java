package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;
import benchmark.objects.B;

public class TestRightSensitivity {

    public static void main(String[] args) {
        BenchmarkN.alloc(1);
        A a = new A();
        BenchmarkN.alloc(2);
        B b = new B();
        BenchmarkN.alloc(3);
//         A c = new A();
//         if (args.length > 1) a = c;
//         BenchmarkN.test(2, a.f);
        b = a.f;
        BenchmarkN.test(1, b);
    }
}
