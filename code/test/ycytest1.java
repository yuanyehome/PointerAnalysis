package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;

public class Hello {

  public static A internal(A k) {
    BenchmarkN.alloc(4);
    A d = new A();
    k = d;
    BenchmarkN.test(4, d);
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
    c = internal(b);
    BenchmarkN.test(3, c);
    BenchmarkN.test(4, b);
  }
}
