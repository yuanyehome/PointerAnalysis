package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;

public class ForLoopTest {
    public static void main(String[] args) {
        A a = new A();
        for (int i = 0; i < 10; ++i) {
            // BenchmarkN.alloc(i);
            a = new A();
        }
        BenchmarkN.test(1, a);
    }
}
