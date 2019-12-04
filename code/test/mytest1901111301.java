package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;
import benchmark.objects.B;

/* 多层域敏感，答案：
    1: 200
    2: 1001
 */

class Z {
  A a;
  public Z(A _a) {
    a = _a;
  }
};
public class mytest1901111301 {
  public static void main(String[] args) {

    BenchmarkN.alloc(101);
    B b1 = new B();

    BenchmarkN.alloc(1001);
    A a1 = new A(b1);

    BenchmarkN.alloc(1);
    Z z1 = new Z(a1);

    BenchmarkN.alloc(200);
    B b200 = new B();

    z1.a.f = b200;

    BenchmarkN.test(1, z1.a.f);
    BenchmarkN.test(2, z1.a);
  }
}
