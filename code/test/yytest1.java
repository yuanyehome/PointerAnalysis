package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;
import benchmark.objects.B;
import benchmark.objects.test;

public class yytest1 {

    public static void main(String[] args) {
        test t = new test();
        test t2 = new test();
        t.a.f = t2.a.f;
    }
}
