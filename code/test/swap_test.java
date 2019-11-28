package test;

import benchmark.objects.A;

public class swap_test {

    public void swap(A a, A b) {
        A tmp = a;
        a = b;
        b = tmp;
    }

    public void main(String[] args) {
        A a1 = new A();
        A a2 = new A();
        swap(a1, a2);
    }
}