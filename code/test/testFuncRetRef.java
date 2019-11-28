package test;

import benchmark.internal.BenchmarkN;
import test.testObj.A;

public class testFuncRetRef {
    public static void main(String[] args) {
        A a = new A();
        a.i = new Integer(2);
        System.out.println(a.getI().toString());
    }
}
