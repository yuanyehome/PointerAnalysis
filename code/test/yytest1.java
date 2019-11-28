package test;

public class yytest1 {

    public static class A {
        int a;
        A(int x){a = x;}
    }

    public static void test(A a, A b) {
        A tmp = a;
        a = b;
        b = tmp;
    }

    public static void main(String[] args) {
        A a = new A(1);
        A b = new A(2);
        a.a = b.a;
    }
}
