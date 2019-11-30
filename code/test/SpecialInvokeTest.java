package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;

public class SpecialInvokeTest {

  public static void specialchange(A k) {
    BenchmarkN.alloc(5);
    A d = new A();
  }

  public A func(A k) {
    BenchmarkN.alloc(4);
    A d = new A();
    specialchange(k);
    BenchmarkN.test(5, d);
    BenchmarkN.test(6, k);
    return d;
  }

  public static void main(String[] args) {
    BenchmarkN.alloc(1);
    A a = new A();
    BenchmarkN.alloc(2);
    A b = new A();
    BenchmarkN.alloc(3);
    A c = new A();
    BenchmarkN.test(1, a); // the points-to set now?
    if (args.length > 1) a = b;
    if (args.length > 2) b = c;
    BenchmarkN.test(2, a);
    SpecialInvokeTest Y = new SpecialInvokeTest();
    c = Y.func(b);
    BenchmarkN.test(3, c);
    BenchmarkN.test(4, b);
  }
}