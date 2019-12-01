package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;
import benchmark.objects.B;

class Z {
    A a;
    public Z(A _a) {
        a = _a;
    }
};
public class MyTest2 {
    public static void main(String[] args) {

        BenchmarkN.alloc(101);
        B b1 = new B();
        BenchmarkN.alloc(102);
        B b2 = new B();
        BenchmarkN.alloc(103);
        B b3 = new B();

        BenchmarkN.alloc(1001);
        A a1 = new A(b1);
        BenchmarkN.alloc(1002);
        A a2 = new A(b2);
        BenchmarkN.alloc(1003);
        A a3 = new A(b3);

        BenchmarkN.alloc(1);
        Z z1 = new Z(a1);
        BenchmarkN.alloc(2);
        Z z2 = new Z(a2);
        BenchmarkN.alloc(3);
        Z z3 = new Z(a3);


        if (args.length > 1) a1 = a3;

        if (args.length > 2) z2 = z3;

        BenchmarkN.test(1, z1.a);
        BenchmarkN.test(2, z2.a);
        BenchmarkN.test(3, z3.a);

        BenchmarkN.alloc(197);
        B b97 = new B();
        BenchmarkN.alloc(198);
        B b98 = new B();
        BenchmarkN.alloc(199);
        B b99 = new B();

        z1.a.f = b97;
        z2.a.f = b98;
        z3.a.f = b99;

        BenchmarkN.test(101, z1.a.f);
        BenchmarkN.test(102, z2.a.f);
        BenchmarkN.test(103, z3.a.f);
    }
}
/*
1: 1001
2: 1002 1003
3: 1003
101: 197
102: 102 198 199 // 实际上这里应该只有198，但是我们这种方法做不到
103: 199
 */