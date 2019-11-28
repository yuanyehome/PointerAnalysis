package test;

import benchmark.internal.BenchmarkN;
import test.testObj.A;

public class TestFunctionSensitivity {
    static A a = new A();
    static A b = new A();

    public static void test(A a) {
        TestFunctionSensitivity.a.j = 3;
        a.j = 5;
        return;
    }

    public static void main(String[] args) {
        A tmp = new A();
        test(tmp);
        System.out.println(tmp.j);
        return;
    }
}
