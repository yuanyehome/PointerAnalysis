package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;
import benchmark.objects.B;

public class Test4 {

	public Test4() {}

	private void assign(A x, A y) {
		y.f = x.f;
	}

	private void test() {
		int r1 = 1;
		int r2 = 2;
		BenchmarkN.alloc(1);
		B b = new B(); // b->{1}
		BenchmarkN.alloc(2);
		A a = new A(b); // a.f->{1}, a->{2}
		BenchmarkN.alloc(3);
		A c = new A(); // c->{3}
		BenchmarkN.alloc(4);
		A d = new A(); // d->{1}
		if (r1 < r2) {
			c.g = a.g; // 0?
		} else if (r1 > r2) {
			a.g = c.g; // 0?
		} else {
			c.g = d.g; // 0?
		}
		BenchmarkN.alloc(5);
		B e = new B(); // e->{5}
		assign(a, c); // c.f->{1}
		c.g = e; // c.g->{5}
		c = a; // c->{2}

		BenchmarkN.test(1, a); // 2
		BenchmarkN.test(2, a.g); // 0?
		BenchmarkN.test(3, a.f); // 1
		BenchmarkN.test(4, c.f); // 1, same with a.f
		BenchmarkN.test(5, c);   // 2
		BenchmarkN.test(6, c.g); // 5
	}

	public static void main(String[] args) {
		Test4 test = new Test4();
		test.test();
	}
}