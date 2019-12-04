package test;
import benchmark.internal.Benchmark;
import benchmark.objects.A;
import benchmark.objects.B;
import benchmark.internal.BenchmarkN;

public class mytest1700012821 {

    static A a;


    static class X {
        public A a = new A();
    }

    class CastTest {
        public class MyA {}
        public class MyB extends CastTest.MyA {}

        public void CastTest_() {
            BenchmarkN.alloc(100);
            CastTest t = new CastTest();
            BenchmarkN.alloc(200);
            CastTest t1 = new CastTest();
            BenchmarkN.alloc(300);
            CastTest.MyA a = t.new MyA();
            BenchmarkN.alloc(400);
            CastTest.MyA a2 = t.new MyA();
            BenchmarkN.alloc(500);
            CastTest.MyB b = t.new MyB();
            BenchmarkN.alloc(600);
            CastTest.MyB b2 = t.new MyB();

            a = b;
            b2 = (CastTest.MyB) a;
            BenchmarkN.test(100, b2); // 500
            int x = 3;
            if (x > 1)
                b = b2;
            BenchmarkN.test(200, b); // 500
        }
    }

    static void TestStatic() {
        BenchmarkN.alloc(8);
        B b = new B();
        BenchmarkN.alloc(9);
        a = new A(b);
        B b2 = a.f;
        B b3 = b2;
        BenchmarkN.test(10, a);
        BenchmarkN.test(11, a.f);
        BenchmarkN.test(12, b);
        BenchmarkN.test(13, b2);
        BenchmarkN.test(14, b3);
    }

    static void TestForLoop() {
        BenchmarkN.alloc(10);
        A a_ = new A();
        for (int i = 0; i < 10; ++i) {
            a_ = new A();
        }
        BenchmarkN.test(15, a_);
    }

    static void TestMultiField() {
        BenchmarkN.alloc(11);
        X x = new X();
        BenchmarkN.test(16, x.a);
        BenchmarkN.test(17, x.a.f);
        BenchmarkN.alloc(12);
        x.a.f = new B();
        BenchmarkN.test(18, x.a.f);
    }

    static void TestCast() {
        mytest1700012821 s = new mytest1700012821();
        CastTest tests = s.new CastTest();
        tests.CastTest_();
    }

    public static void main(String[] args) {
        // test system call;
        Integer x = 3;
        System.out.println(x);

        // basic test
        BenchmarkN.alloc(1);
        A a1 = new A();
        BenchmarkN.alloc(2);
        B b = new B();
        BenchmarkN.alloc(3);
        A a2 = new A(b);
        BenchmarkN.alloc(4);
        A a3 = new A(a1.f);
        BenchmarkN.test(1, a1); // 1
        BenchmarkN.test(2, a1.f); // 0
        BenchmarkN.test(3, a2); // 3
        BenchmarkN.test(4, a2.f); // 0 2 (should be 2)
        BenchmarkN.test(5, a3.f); // 0

        // for loop test

        // Array test
        A[] arr = new A[6];
        BenchmarkN.alloc(5);
        arr[0] = new A();
        BenchmarkN.test(6, arr[0]); // 5
        BenchmarkN.alloc(6);
        arr[1] = new A();
        BenchmarkN.test(7, arr[0].f); // 0

        // Multi-Array test
        A[][] multiArr = new A[6][6];
        BenchmarkN.alloc(7);
        multiArr[1][2] = new A(b);
        multiArr[0][0] = arr[1];
        multiArr[2][3] = new A();
        BenchmarkN.test(8, multiArr[1][2]); // 0 5 6 7
        BenchmarkN.test(9, multiArr[0][0]); // 0 5 6 7

        TestStatic();

        TestForLoop();

        TestCast();

        TestMultiField();



    }
}
