package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;

public class InvokeTest {

    public static void specialchange(A k) {
        BenchmarkN.alloc(5);
        A d = new A();
        k = d;
        // System.out.println("Hi!");
    }

    public A func(A k) {
        BenchmarkN.alloc(4);
        A d = new A();
        specialchange(k);
        BenchmarkN.test(5, d);  // 4
        BenchmarkN.test(6, k);  // 2 3
        return d;
    }

    public static void main(String[] args) {
        BenchmarkN.alloc(1);
        A a = new A();
        BenchmarkN.alloc(2);
        A b = new A();
        BenchmarkN.alloc(3);
        A c = new A();
        BenchmarkN.test(1, a);  // 1
        if (args.length > 1) a = b;
        if (args.length > 2) b = c;
        BenchmarkN.test(2, a);  // 1 2
        InvokeTest Y = new InvokeTest();
        c = Y.func(b);
        BenchmarkN.test(3, c);  // 4
        BenchmarkN.test(4, b);  // 2 3
    }

}
