package test;
// put this file into package "test"

import benchmark.internal.BenchmarkN;

public class mytest1600012710 {

    public static void main(String[] args) {
        BenchmarkN.alloc(1);
        K1 k = new K4();
        BenchmarkN.alloc(2);
        k.h = new A();
        
        A a = k.get();    // virtual call
        K4 k4 = (K4) k;   // type cast
        A a4 = k4.get();
        BenchmarkN.test(1, a);             // 2
        BenchmarkN.test(2, a4);            // 2

        A s = S.get();    // static call & static fields
        BenchmarkN.test(3, s);             // 3
        
        BenchmarkN.alloc(4);
        A[] arr = new A[5];                 // array
        BenchmarkN.alloc(5);
        arr[0] = new A();
        BenchmarkN.alloc(6);
        arr[1] = new A();                   // constant indexes
        arr[2] = arr[0];
        int i = Integer.parseInt(args[0]);  // variable (unknown) indexes
        int j = Integer.parseInt(args[0]);
        BenchmarkN.alloc(7);
        arr[i] = new A();

        A x1 = arr[1];
        A x2 = arr[2];
        A x3 = arr[j];
        BenchmarkN.test(4, x1);        // 6 7 
        BenchmarkN.test(5, x2);        // 5 7
        BenchmarkN.test(6, x3);        // 5 6 7
        BenchmarkN.test(7, arr);       // 4
        
        BenchmarkN.alloc(8);
        Z z1 = new Z();
        Z z2 = z1;
        BenchmarkN.alloc(9);
        z2.f = new Z();
        BenchmarkN.test(8, z1.f);      // 9
        
    }
}

class S {
    private static final A s;
    static {
        BenchmarkN.alloc(3);
        s = new A();
    }

    public static A get() {return s;}

}

class A{}

class K1 {
    public A f;
    public A g;
    public A h;
    public A get() {return f;}
}

class K2 extends K1 {}
class K3 extends K2 { public A get() {return g;} }
class K4 extends K3 { public A get() {return h;} }

class Z {
    public Z f;
}
