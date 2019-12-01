package test;
import benchmark.internal.Benchmark;
import benchmark.objects.A;
import benchmark.objects.B;
import benchmark.internal.BenchmarkN;

public class mytest1700012821 {
    public static void main() {
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
        BenchmarkN.test(1, a1);
        BenchmarkN.test(2, a1.f);
        BenchmarkN.test(3, a2);
        BenchmarkN.test(4, a2.f);
        BenchmarkN.test(5, a3.f);

        // for loop test
        for (int i = 0; i < 10; ++i) {
            A a_ = new A();
        }

        // Array test
        A[] arr = new A[6];
        BenchmarkN.alloc(5);
        arr[0] = new A();
        BenchmarkN.alloc(6);
        arr[1] = new A();
        BenchmarkN.test(6, arr[0]);
        BenchmarkN.test(7, arr[0].f);

        // Multi-Array test
        A[][] multiArr = new A[6][6];
        BenchmarkN.alloc(7);
        multiArr[1][2] = new A(b);
        multiArr[0][0] = arr[1];
        BenchmarkN.test(8, multiArr[1][2]);
        BenchmarkN.test(9, multiArr[0][0]);



    }
}
