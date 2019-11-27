package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;

public class yytest0 {

    public static void internal() {
        BenchmarkN.alloc(4);
        A a1 = new A();
    }

    public static void main(String[] args) {
        BenchmarkN.alloc(1);
        A a = new A();
        BenchmarkN.alloc(2);
        A b = new A();
        BenchmarkN.alloc(3);
        A c = new A();
//        BenchmarkN.test(1, a); // the points-to set now?
//        if (args.length > 1) a = b;
//        if (args.length > 2) b = c;
        BenchmarkN.alloc(4);
        A[][][] arr = new A[6][6][6];
        BenchmarkN.test(1, arr);
        BenchmarkN.test(2, a);
        BenchmarkN.test(3, b);
        BenchmarkN.test(4, c);
    }
}
