package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;
import benchmark.objects.B;

public class MyTest6 {

    static void test1(A a) {
        BenchmarkN.alloc(1001);
        a.f = new B();
    }

    static A test2(A a) {
        BenchmarkN.alloc(2001);
        a.f = new B();
        return a;
    }

    public static void main(String[] args) {
        A a, a1;

        BenchmarkN.alloc(1000);
        a = new A();
        test1(a);
        BenchmarkN.test(1000, a);
        BenchmarkN.test(1001, a.f);

        BenchmarkN.alloc(2000);
        a = new A();
        a1 = test2(a);
        BenchmarkN.test(2000, a);
        BenchmarkN.test(2001, a.f);
        BenchmarkN.test(2002, a1);
    }
}
/*
 * 1000: 1000 1001: 1001 2000: 2000 2001: 2001 2002: 2000
 */
