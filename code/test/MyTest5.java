package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;
import benchmark.objects.B;

class MyTreeNode {
    int l, r;
    MyTreeNode lch, rch;
};

public class MyTest5 {

    static MyTreeNode buildTree(int l, int r) {
        MyTreeNode ret;
        if (l % 2 == 1) {
            BenchmarkN.alloc(1);
            ret = new MyTreeNode();
        } else {
            BenchmarkN.alloc(2);
            ret = new MyTreeNode();
        }

        ret.l = l;
        ret.r = r;

        if (r - l > 1) {
            int m = (l + r) / 2;
            ret.lch = buildTree(l, m);
            ret.rch = buildTree(m, r);
        }

        return ret;
    }

    public static void main(String[] args) {
        BenchmarkN.alloc(101);
        B b = new B();
        BenchmarkN.alloc(100);
        A a = new A(b);
        BenchmarkN.test(100, a);
        BenchmarkN.test(101, a.f);

        MyTreeNode myRoot = buildTree(0, 4);

        BenchmarkN.test(1, myRoot);
        BenchmarkN.test(2, myRoot.lch);
        BenchmarkN.test(3, myRoot.rch);
        BenchmarkN.test(4, myRoot.lch.lch);
        BenchmarkN.test(5, myRoot.lch.rch);
        BenchmarkN.test(6, myRoot.rch.lch);
        BenchmarkN.test(7, myRoot.rch.rch);

        BenchmarkN.test(200, a);
        BenchmarkN.test(201, a.f);
    }
}
/*
 * 我也不知道结果是什么
 */