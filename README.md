# 指针分析大作业 

软件分析技术课程作业，基于 [Soot 程序分析框架](sable.github.io/soot)。

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

使用方法：

-   请使用`JAVA8`进行编译和运行；
-   `code/`目录下为测试代码，其中`mytest1700012821.java`应可通过；
-   `src/`目录下为主代码，`core.MyPointerAnalysis`为程序运行起点；
-   `cd code; ./compile_test.sh`对`test`代码进行编译；
-   将生成的`jar`文件放在主目录下，执行`java -jar ***.jar code test.***`；
-   详细内容参考[report](./report.pdf)

贡献者：

- [malusamayo](https://github.com/malusamayo)
- [yuanye](https://github.com/yuanyehome)
- [vbcpascal](https://github.com/vbcpascal)

# Pointer Analysis

Homework of *Software Analysis*, based on [Soot](sable.github.io/soot).

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

Usage:

-   Please use `JAVA8` to compile and run;
-   `code/` contains test code, and `mytest1700012821.java` is my final test;
-   `src/` contains project source code, and program starts from `core.MyPointerAnalysis`;
-   `cd code; ./compile_test.sh`  to compile test code;
-   Build the `jar` file, and run `java -jar ***.jar code test.***`;
-   For more details, please refer to [report](./report.pdf)

Contributor:

- [malusamayo](https://github.com/malusamayo)
- [yuanye](https://github.com/yuanyehome)
- [vbcpascal](https://github.com/vbcpascal)
