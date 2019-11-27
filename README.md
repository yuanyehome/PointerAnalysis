# 指针分析 

软件分析技术课程作业，基于 [Soot 程序分析框架](sable.github.io/soot[
](https://cn.bing.com/search?q=soot&qs=n&form=QBLHCN&sp=-1&pq=soot&sc=8-4&sk=&cvid=D42429B894C04E4483BF5A0C033113D3#))。

示例程序：

``` java
public static void main(String[] args) {
  BenchmarkN.alloc(1); //标记分配点，没有标记的默认编号为0
  A a = new A();
  BenchmarkN.alloc(2);
  A b = new A();
  BenchmarkN.alloc(3);
  A c = new A();
  if (args.length > 1) a = b;
  BenchmarkN.test(1, a); //标记测试点编号和被测变量
  BenchmarkN.test(2, c);
}
```

输出：

``` 
1: 1 2
2: 3
```

贡献者：

- [malusamayo](https://github.com/malusamayo)
- [yuanye](https://github.com/yuanyehome)
- [vbcpascal](https://github.com/vbcpascal)

# Pointer Analysis

Homework of *Software Analysis*, based on [Soot](sable.github.io/soot[
](https://cn.bing.com/search?q=soot&qs=n&form=QBLHCN&sp=-1&pq=soot&sc=8-4&sk=&cvid=D42429B894C04E4483BF5A0C033113D3#)).

Example program：

```java
public static void main(String[] args) {
  BenchmarkN.alloc(1);   // Mark distribution points, the default number is 0 without markers
  A a = new A();
  BenchmarkN.alloc(2);
  A b = new A();
  BenchmarkN.alloc(3);
  A c = new A();
  if (args.length > 1) a = b;
  BenchmarkN.test(1, a); // Mark test point number and measured variable
  BenchmarkN.test(2, c);
}
```

Output：

```
1: 1 2
2: 3
```

Contributor:

- [malusamayo](https://github.com/malusamayo)
- [yuanye](https://github.com/yuanyehome)
- [vbcpascal](https://github.com/vbcpascal)