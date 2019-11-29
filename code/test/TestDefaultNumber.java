package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;

public class TestDefaultNumber {

    public static void main(String[] args) {
        BenchmarkN.alloc(1);
        A a = new A();
        A b = new A();
        BenchmarkN.alloc(3);
        A c = new A();
        if (args.length > 1) a = b;
        if (args.length > 2) b = c;
        BenchmarkN.test(1, a);
        BenchmarkN.test(2, b);
        BenchmarkN.test(3, c);
    }
}
