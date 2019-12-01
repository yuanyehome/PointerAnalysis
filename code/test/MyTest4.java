package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;
import benchmark.objects.B;

public class MyTest4 {

    static B test1(int x) {
        if (x == 1) {
            BenchmarkN.alloc(11);
            return new B();
        } else if (x == 2) {
            BenchmarkN.alloc(12);
            return new B();
        } else {
            BenchmarkN.alloc(13);
            return new B();
        }
    }

    static void test2(A a, int x) {
        if (x == 1) {
            BenchmarkN.alloc(21);
            a.f = new B();
        } else if (x == 2) {
            BenchmarkN.alloc(22);
            a.f = new B();
        } else {
            BenchmarkN.alloc(23);
            a.f = new B();
        }
    }

    public static void main(String[] args) {

        BenchmarkN.test(1, test1(1000));

        A a = new A();
        test2(a, 1000);
        BenchmarkN.test(2, a.f);
    }
}

/*
 * 1: 11 12 13 2: 21 22 23
 */