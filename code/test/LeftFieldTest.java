
package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;
import benchmark.objects.B;

public class LeftFieldTest {

    public static void main(String[] args) {
        BenchmarkN.alloc(1);
        A a = new A();
        BenchmarkN.alloc(2);
        B b = new B();
        a.f = b;
        BenchmarkN.test(1, a.f);
        BenchmarkN.test(2, b);
    }
}