package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;
import benchmark.objects.B;
import benchmark.objects.N;
import benchmark.objects.G;
import benchmark.objects.H;
import benchmark.objects.I;
import benchmark.objects.P;
// import benchmark.objects.Q;

class Param {
    Param f;
}

class Tmp1 {
    Tmp1 f;
    int number;

    Tmp1() {
        this.f = new Tmp1();
    }
}

interface II {
    public II f();
}

class AA implements II {
    public BB f() {
        BenchmarkN.alloc(8888);
        return new BB();
    }
}

class BB implements II {
    public AA f() {
        BenchmarkN.alloc(9999);
        return new AA();
    }
}

interface J {
    public void f();

    public J f(J param, int num);
}

class AAA implements J {
    public void f() {
        BenchmarkN.alloc(5500);
        BBB bb = new BBB();
        // tmp=new BB();
    }

    public J f(J param, int num) {
        if (num > 0) {
            BenchmarkN.alloc(5300);
            // return new AA();
            param = new AAA();
        } else {
            BenchmarkN.alloc(5400);
            // return new AA();
            param = new AAA();
        }
        return param;
    }
}

class BBB implements J {
    public void f() {
        BenchmarkN.alloc(5100);
        AAA aa = new AAA();
    }

    public J f(J param, int num) {
        BenchmarkN.alloc(5200);
        param = new AAA();
        return param;
    }
}

class CCC {
    J i;
}

class Tmp2 {
    Tmp2 f;
    int number;

    Tmp2() {
    }
}

class Nan {
    public Nan next;

    public Nan() {
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        next = new Nan();
        BenchmarkN.alloc(12796);
        next = new Nan();
    }
}

public class mytest1700012796 {

    public mytest1700012796() {
    }

    private void assign(A x, A y) {
        y = x;
    }

    private void test1() {
        A a[] = new A[2];
        B b[] = new B[2];
        BenchmarkN.alloc(1);
        b[0] = new B();
        BenchmarkN.alloc(2);
        a[0] = new A(b[0]);
        BenchmarkN.alloc(3);
        A c = new A();
        BenchmarkN.alloc(4);
        B e = new B();
        assign(a[0], c);
        BenchmarkN.test(1, c);
        BenchmarkN.test(2, a[0]);
    }

    public void test2() {
        A a[] = new A[2];
        B b[] = new B[2];
        BenchmarkN.alloc(5);
        b[1] = new B();
        BenchmarkN.alloc(6);
        B c = new B();
        BenchmarkN.alloc(7);
        a[0] = new A();
        BenchmarkN.alloc(8);
        a[1] = new A();
        a[0].f = b[1];
        a[1].f = c;
        BenchmarkN.test(3, a[0].f);
        BenchmarkN.test(4, a[1].f);
    }

    private void test3() {
        int r1 = 1;
        int r2 = 2;
        BenchmarkN.alloc(9);
        B b = new B();
        BenchmarkN.alloc(10);
        A a = new A(b);
        BenchmarkN.alloc(11);
        A c = new A();
        BenchmarkN.alloc(12);
        A d = new A();
        if (r1 < r2) {
            c.g = a.g;
        } else if (r1 > r2) {
            a.g = c.g;
        } else {
            c.g = d.g;
        }
        BenchmarkN.alloc(13);
        B e = new B();
        assign(a, c);
        c.g = e;
        c = a;

        BenchmarkN.test(5, a);
        BenchmarkN.test(6, a.g);
        BenchmarkN.test(7, a.f);
        BenchmarkN.test(8, c.f);
        BenchmarkN.test(9, c);
        BenchmarkN.test(10, c.g);
    }

    static void test4(A a) {
        BenchmarkN.alloc(1001);
        a.f = new B();
    }

    static A test5(A a) {
        BenchmarkN.alloc(2001);
        a.f = new B();
        return a;
    }

    static B test6(int x) {
        B B1[] = new B[2];
        B B2[] = new B[1];
        BenchmarkN.alloc(100);
        B1[0] = new B();
        BenchmarkN.alloc(200);
        B1[1] = new B();
        BenchmarkN.alloc(999);
        B2[0] = new B();
        if (x == 100) {
            return B1[0];
        } else if (x == 200) {
            return B1[1];
        } else {
            BenchmarkN.alloc(300);
            return new B();
        }
    }

    static void test7(A a, int x) {
        B B1[] = new B[3];
        BenchmarkN.alloc(400);
        B1[0] = new B();
        BenchmarkN.alloc(500);
        B1[1] = new B();
        BenchmarkN.alloc(600);
        B1[2] = new B();
        if (x == 100) {
            a.f = B1[0];
        } else if (x == 200) {
            a.f = B1[1];
        } else {
            a.f = B1[2];
        }
    }

    static void testCall(A a) {
        BenchmarkN.test(11, a);
    }

    static void QQ() {
        BenchmarkN.alloc(601);
        A qa = new A();
        BenchmarkN.test(601, qa);// qa->{1}
        PP();
        BenchmarkN.alloc(602);
        qa = new A();
        BenchmarkN.test(602, qa);// qa->{3}
    }

    static void PP() {
        BenchmarkN.alloc(603);
        A qa = new A();
        BenchmarkN.test(603, qa);// qa->{2}
        QQ();
    }

    public static void main(String[] args) {
        /* field sensitivity */
        BenchmarkN.alloc(14);
        mytest1700012796 fs2 = new mytest1700012796();
        fs2.test1();
        fs2.test2();

        /* test4 */
        mytest1700012796 tttt = new mytest1700012796();
        tttt.test3();

        /* test field 1 */
        BenchmarkN.alloc(15);
        Tmp1 tmp1 = new Tmp1();
        BenchmarkN.alloc(16);
        Tmp1 tmp2 = new Tmp1();
        BenchmarkN.alloc(17);
        Tmp1 tmp3 = new Tmp1();
        tmp2.f = tmp3;
        tmp1.f.f = tmp2.f;
        BenchmarkN.alloc(18);
        tmp3.f = new Tmp1();
        BenchmarkN.test(12, tmp1.f.f.f);

        /* test field 2 */
        BenchmarkN.alloc(19);
        Tmp2 tmp4 = new Tmp2();
        BenchmarkN.alloc(20);
        Tmp2 tmp5 = new Tmp2();
        BenchmarkN.alloc(21);
        Tmp2 tmp6 = new Tmp2();
        tmp5.f = tmp6;
        tmp4.f = tmp5;
        BenchmarkN.alloc(22);
        tmp4.f.f = new Tmp2();
        tmp4.f.f = tmp5.f;
        Tmp2 tmp7 = tmp5.f;
        BenchmarkN.alloc(23);
        tmp5.f = new Tmp2();
        BenchmarkN.alloc(24);
        tmp5 = new Tmp2();
        BenchmarkN.alloc(25);
        tmp5.f = new Tmp2();

        BenchmarkN.test(13, tmp7);
        BenchmarkN.test(14, tmp6);
        BenchmarkN.test(15, tmp4.f.f);
        BenchmarkN.test(16, tmp5);
        BenchmarkN.test(17, tmp5.f);

        /* param not cover */
        BenchmarkN.alloc(26);
        A pa = new A();
        BenchmarkN.alloc(27);
        A pb = new A();
        BenchmarkN.alloc(28);
        A pc = new A();
        testCall(pa);
        testCall(pb);
        testCall(pc);

        /* test order */
        BenchmarkN.alloc(29);
        A ppp = new A();
        for (int i = 0; i < args.length; ++i) {
            BenchmarkN.test(18, ppp);
            BenchmarkN.alloc(30);
            ppp = new A();
        }
        BenchmarkN.alloc(31);
        ppp = new A();

        /* test order 1 */
        BenchmarkN.alloc(32);
        A pa1 = new A();
        if (args.length > 1) {
            BenchmarkN.test(19, pa1);
        }
        BenchmarkN.alloc(33);
        pa1 = new A();

        /* test param */
        BenchmarkN.alloc(34);
        Param p1 = new Param();
        BenchmarkN.alloc(35);
        p1.f = new Param();
        BenchmarkN.alloc(36);
        Param p2 = new Param();
        BenchmarkN.alloc(37);
        p2.f = new Param();
        p1 = p2;
        BenchmarkN.alloc(38);
        p2.f = new Param();
        BenchmarkN.test(20, p1);
        BenchmarkN.test(21, p1.f);
        BenchmarkN.test(22, p2);
        BenchmarkN.test(23, p2.f);

        /* hello */
        BenchmarkN.alloc(39);
        A a = new A();
        BenchmarkN.alloc(40);
        A b = new A();
        BenchmarkN.alloc(41);
        A c = new A();
        if (args.length > 1)
            a = b;
        BenchmarkN.test(24, a);
        BenchmarkN.test(25, c);

        /* sr test 1 */
        A sra, sra1;
        BenchmarkN.alloc(1000);
        sra = new A();// a->{1000}
        test4(sra);
        BenchmarkN.test(1000, sra);
        BenchmarkN.test(1001, sra.f);

        BenchmarkN.alloc(2000);
        sra = new A();// a->{2000},a.f->{1001}
        sra1 = test5(sra);// a->{2000},a.f->{2001},a1->{2000}
        BenchmarkN.test(2000, sra);
        BenchmarkN.test(2001, sra.f);
        BenchmarkN.test(2002, sra1);

        /* sr test 2 */
        BenchmarkN.test(666, test6(1000)); // 100 200 300

        A sra2 = new A();
        test7(sra2, 1000);
        BenchmarkN.test(26, sra2.f); // 400 500 600

        /* srb test */
        A rba;
        BenchmarkN.alloc(99);
        B rbc = new B();
        if (args.length > 1) {
            BenchmarkN.alloc(101);
            rba = new A();
        } else {
            BenchmarkN.alloc(102);
            rba = new A();
        }
        if (args.length > 1) {
            BenchmarkN.alloc(103);
            rba.f = new B();
        } else {
            BenchmarkN.alloc(104);
            rba.f = new B();
        }
        // rbc=rba.id(rba.id(rba.getH()));

        BenchmarkN.test(27, rba);
        BenchmarkN.test(28, rba.f);
        // BenchmarkN.test(29, rbc);

        /* test N */
        BenchmarkN.alloc(42);
        N n1 = new N();// n1->{1}
        BenchmarkN.alloc(43);
        N n2 = new N();// n2->{3}
        BenchmarkN.alloc(44);
        N n3 = new N();// n3->{4}
        BenchmarkN.alloc(45);
        N n4 = new N();// n4->{5}
        BenchmarkN.alloc(46);
        n4.next = new N();// n4.n->{6}*/
        BenchmarkN.test(27, n1);
        BenchmarkN.test(28, n2);
        BenchmarkN.test(29, n3);
        BenchmarkN.test(30, n4);
        BenchmarkN.test(31, n1.next.next.next);
        BenchmarkN.test(32, n2.next);
        BenchmarkN.test(33, n3.next);
        BenchmarkN.test(34, n4.next);

        /* test 1 */
        BenchmarkN.alloc(47);
        II i = new AA();// i->{3}
        BenchmarkN.test(35, i);
        if (args.length > 1)
            i = i.f();// i->{1,3}
        BenchmarkN.alloc(48);
        II x = new AA();// x->{4}
        i.f();// {1,2}
        BenchmarkN.test(36, i.f());

        /* test more condition */
        BenchmarkN.alloc(49);
        A ca = new A();
        if (args.length > 1) {
            BenchmarkN.alloc(50);
            ca = new A();
        } else {
            BenchmarkN.alloc(51);
            ca = new A();
        }
        BenchmarkN.alloc(52);
        ca = new A();
        if (args.length > 3) {
            BenchmarkN.alloc(53);
            ca = new A();
        }
        BenchmarkN.test(37, ca);

        /* test extends */
        BenchmarkN.alloc(54);
        A ea = new A();
        BenchmarkN.alloc(55);
        P ep = new P(ea);
        BenchmarkN.test(38, ep.getA());
        BenchmarkN.alloc(56);
        ea = new A();
        ep.alias(ea);
        BenchmarkN.test(39, ep.getA());
        BenchmarkN.alloc(57);
        A eb = new A();
        eb = ep.getA();
        BenchmarkN.test(40, eb);

        /* test array */
        A Aarray[] = new A[3];
        BenchmarkN.alloc(58);
        Aarray[0] = new A();
        BenchmarkN.alloc(59);
        Aarray[1] = new A();
        A aaa = Aarray[1];
        BenchmarkN.test(41, Aarray[0]);
        BenchmarkN.test(42, Aarray[1]);
        // BenchmarkN.test(43, Aarray[2]);
        BenchmarkN.test(44, aaa);

        /* test GHI */
        BenchmarkN.alloc(60);
        I ig = new G();
        BenchmarkN.alloc(61);
        I ih = new H();
        BenchmarkN.alloc(62);
        A ghia = new A();
        BenchmarkN.alloc(63);
        A ghib = new A();
        ghia = ig.foo(ghib);
        BenchmarkN.test(45, ghia);

        /* sr test 3 */
        BenchmarkN.alloc(64);
        B[] bs = new B[3];
        BenchmarkN.alloc(65);
        bs[0] = new B();
        bs[1] = bs[0];
        BenchmarkN.alloc(66);
        bs[2] = new B();

        BenchmarkN.test(46, bs);
        BenchmarkN.test(47, bs[0]);
        BenchmarkN.test(48, bs[1]);
        BenchmarkN.test(49, bs[2]);

        /* test cover */
        BenchmarkN.alloc(67);
        A covera = new A();
        if (args.length > 1) {
            BenchmarkN.alloc(68);
            covera = new A();
            BenchmarkN.alloc(69);
            covera = new A();
            BenchmarkN.alloc(70);
            covera = new A();
        }
        BenchmarkN.test(50, covera);

        /* impl */
        BenchmarkN.alloc(71);
        J j = new BBB();// i->{3}
        BenchmarkN.test(51, j);
        if (args.length > 1)
            j = j.f(j, 1);// i->{3,5}
        BenchmarkN.alloc(72);
        j.f();// {1,2}
        BenchmarkN.test(52, j.f(j, 0));

        BenchmarkN.alloc(666666);
        Nan nan = new Nan();
        BenchmarkN.test(99, nan);
        BenchmarkN.test(999, nan.next);
    }

}
