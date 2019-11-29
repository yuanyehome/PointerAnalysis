package test;

import benchmark.internal.BenchmarkN;
import test.testObj.A;

public class SysCallTest {
    public static void main(String[] args) {
        BenchmarkN.alloc(1);
        A a = new A();
        System.out.println("\033[33mSystem call here\033[m");
        BenchmarkN.test(1, a);
    }
}
