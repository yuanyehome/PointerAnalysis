package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;

class TEST {
    A array[];
};

public class mytest1700012723 {
    public static void main(String[] args) {
        TEST mytest = new TEST();
        BenchmarkN.alloc(1);
        A a = new A();
        BenchmarkN.alloc(2);
        A b = new A();
        BenchmarkN.alloc(3);
        A c = new A();
        mytest.array = new A[3];
        mytest.array[0] = a;
        mytest.array[1] = b;
        int random1 = args.length % 3;
        mytest.array[random1] = c; //We do not really know which element will be changed
        BenchmarkN.test(1, mytest.array[0]);
        BenchmarkN.test(2, mytest.array[1]);
        int random2 = args.length % 3;
        BenchmarkN.test(3, mytest.array[random2]);

    }
}
/*
1: 1 3
2: 2 3
3: 1 2 3
 */