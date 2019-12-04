package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;

public class mytest1700012928 {
	
	public static A foo(A a, A b, A c, int n) {
		if(n > 1)
			b = c;
		return a;
	}
	
	public static void main(String[] args) {
		BenchmarkN.alloc(1); 
		A a = new A();
		BenchmarkN.alloc(2);
		A b = new A();
		BenchmarkN.alloc(3);
		A c = new A();
		
		a = foo(a, b, c, args.length);
		BenchmarkN.test(1, b);
	}
}
