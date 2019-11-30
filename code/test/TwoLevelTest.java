package test;

import benchmark.internal.BenchmarkN;
import test.testObj.A;

public class TwoLevelTest {
    public static void main(String[] args) {
        BenchmarkN.alloc(1);
        A a = new A();
        a.b.val = 1;
        int y;
        y = a.b.val;
    }
}