package test;

import benchmark.internal.BenchmarkN;
import test.testObj.A;

public class DemonTest {
    public static void main(String[] args) {
        BenchmarkN.alloc(1);
        A a1 = new A();
        BenchmarkN.alloc(2);
        a1.b = new B();
        BenchmarkN.test(1, a1);
        BenchmarkN.test(2, a1.b);
    }
}
