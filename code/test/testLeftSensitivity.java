package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;
import benchmark.objects.B;

public class testLeftSensitivity {

    public static void main(String[] args) {
        BenchmarkN.alloc(1);
        A a = new A();
        BenchmarkN.alloc(2);
        A b = new A();
        BenchmarkN.alloc(3);
        A c = new A();
        BenchmarkN.alloc(4);
        B bb = new B();
        if (args.length > 1) a = b;
        if (args.length > 2) b = c;
        a.f = bb;
        BenchmarkN.test(2, a.f);
        BenchmarkN.test(3, bb);
    }
}
