package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;
import benchmark.objects.B;

public class mytest1901110055 {

  public mytest1901110055() {}

  private void assign(A x, A y) {
    y.f = x.f;
  }

  private void test() {
    BenchmarkN.alloc(1);
    B b = new B();
    BenchmarkN.alloc(2);
    A a = new A();
    BenchmarkN.alloc(3);
    A c = new A();
    BenchmarkN.alloc(4);
    B e = new B();
    a.f = b;
    assign(a, c);
    B d = c.f;
    
    BenchmarkN.test(1, d);
    BenchmarkN.test(2, a);
  }
 
  

  public static void main(String[] args) {

    BenchmarkN.alloc(9);
    mytest1901110055 fs2 = new mytest1901110055();
    fs2.test();
    
  }

}
