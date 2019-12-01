package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;

public class MyTest1 {

    static A sa;

    private void dummy(A x) {
        BenchmarkN.test(10000, x);
        BenchmarkN.test(9000, this);

    }

    private int add(int a, int b) {
        return a + b;
    }

    private A haha(int x) {
        if (x == 100) {
            BenchmarkN.alloc(100);
            return new A();
        }
        if (x == 200) {
            BenchmarkN.alloc(200);
            return new A();
        }
        BenchmarkN.alloc(300);
        return new A();
    }

    public static void main(String[] args) {
        BenchmarkN.alloc(9999);
        MyTest1 t = new MyTest1();

        BenchmarkN.alloc(1);
        A a = new A();
        BenchmarkN.alloc(2);
        A b = new A();
        BenchmarkN.alloc(3);
        A c = new A();

        System.out.println(t.add(100,200));

        if (args.length > 1) a = b;

        //if (args.length > 1) c = a;
        BenchmarkN.test(1, a);
        BenchmarkN.test(2, b);
        BenchmarkN.test(3, c);

        BenchmarkN.test(300, t.haha(10));

        t.dummy(c);

        int x = 1000;
        sa = c;
        BenchmarkN.test(12345, sa);
    }
}
/*
1: 1 2
2: 2
3: 3
300: 100 200 300
9000: 9999
10000: 3
12345: #unk // 我们的程序不支持静态变量的分析
 */