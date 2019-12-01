package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;
// import test.testObj.A;

public class ForLoopTest {
    public static void main(String[] args) {
        A a = new A();
        for (int i = 0; i < 10; ++i) {
            // BenchmarkN.alloc(1);
            a = new A();
        }
        BenchmarkN.test(1, a);
    }
}
