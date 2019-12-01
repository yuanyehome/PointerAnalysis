package test;

import benchmark.internal.BenchmarkN;
import benchmark.objects.A;

class MyNode {
    int value;
    MyNode next;
}

public class MyTest3 {
    public static void main(String[] args) {

        MyNode head = null;
        for (int i = 0; i < 10; i++) {
            BenchmarkN.alloc(1);
            MyNode node = new MyNode();
            node.value = i;
            node.next = head;
            head = node;
        }

        BenchmarkN.alloc(2);
        MyNode haha = new MyNode();
        haha.value = -100;
        haha.next = head;

        BenchmarkN.test(1, head);
        BenchmarkN.test(2, head.next);
        BenchmarkN.test(3, head.next.next);

        BenchmarkN.test(4, haha);
        BenchmarkN.test(5, haha.next);
        BenchmarkN.test(6, haha.next.next);

        BenchmarkN.alloc(10);
        head = new MyNode();
        head.value = 10;
        head.next = null;
        BenchmarkN.alloc(20);
        haha = new MyNode();
        haha.value = 20;
        haha.next = head;
        head = haha;
        BenchmarkN.alloc(30);
        haha = new MyNode();
        haha.value = 30;
        haha.next = head;
        head = haha;
        BenchmarkN.test(10, head);
        BenchmarkN.test(20, head.next);
        BenchmarkN.test(30, head.next.next);

    }
}
/*
 * 1: #null 1 2: #null #unk 1 // 假设null.anyfield = #unk 3: #null #unk 1 4: 2 5:
 * #null 1 6: #null #unk 1 10: 30 20: 20 30: 10
 */