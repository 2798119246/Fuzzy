package org.example.day;

import java.io.IOException;
import java.util.Arrays;

public class TryCatch {

    static class C extends Exception {
    }

    static class B extends C {
    }

    static class A extends B {
    }
    /**
     * try-catch-finally 基础规则：
     * 是 Java 异常处理的核心结构，单独try编译报错，try-catch/try-finally均合法（catch/finally 可按需省略其一）；
     * try块存放可能抛异常的代码，catch捕获异常并处理（如记录栈信息、打印日志）；finally（除 JVM 退出外）必执行，常用于释放 IO 流、数据库连接等关键资源。
     * 异常体系核心：
     * 顶层父类为Throwable，分为Error和Exception：
     * Error（如StackOverflowError、OutOfMemoryError）：JVM 级严重错误，程序无法处理，需编码规避（数组越界属于运行时异常，非 Error）；
     * Exception分为两类：
     * ✅ 运行时异常（RuntimeException，如 NPE、数组越界、类转换异常）：编译不强制处理，运行时未捕获会终止线程；
     * ✅ 受检异常（如IOException、SQLException）：编译强制要求捕获 / 声明抛出，否则编译报错。
     * JVM 异常处理底层：
     * 异常处理表存储在 Class 文件的方法元数据中（非栈帧内），JVM 通过它匹配异常处理器；
     * 异常抛出后，JVM 从当前栈帧向上回溯查找处理器：找到则执行 finally+catch，未找到则销毁栈帧（自动释放栈内存）继续回溯；
     * 全程未找到处理器时，JVM 记录完整栈轨迹（StackTrace）并终止当前线程；异常对象本身存储完整栈链路，printStackTrace()默认展示 “抛出点到捕获点” 的栈帧，可通过getStackTrace()获取完整信息。
     * 关键修正（你原总结中的小偏差）
     * ❶ 数组越界是RuntimeException（运行时异常），而非Error；
     * ❷ 异常处理表存在 Class 文件（方法元数据），而非栈帧中；
     * ❸ printStackTrace()不是 “仅打印到捕获点”，而是展示形式上只显式输出到捕获点，但异常对象内部保留完整栈链路；
     * ❹ 栈帧销毁释放的是栈内存（局部变量等），堆中资源（如 IO 流）需手动 / 自动关闭，而非栈帧销毁自动释放。
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            System.out.println("抛出异常");
            throw new IOException();
        } catch (Exception e) {
            System.out.println("捕获异常");
            System.out.println(Arrays.toString(e.getStackTrace()));
        } finally {
            System.out.println("一定会执行的部分");
        }

        // 测试1：catch C 能捕获A、B、C
        try {
            throw new A(); // 抛子类A
        } catch (C e) {
            System.out.println("捕获到A（C的子类）"); // 执行
        }

        try {
            throw new B(); // 抛子类B
        } catch (C e) {
            System.out.println("捕获到B（C的子类）"); // 执行
        }

        try {
            throw new C(); // 抛父类C
        } catch (C e) {
            System.out.println("捕获到C"); // 执行
        }

        // 测试2：catch A 无法捕获B、C
        try {
            throw new B(); // 抛B
        } catch (A e) {
            System.out.println("捕获到B"); // 不执行
        } catch (Exception e) {
            System.out.println("catch A 无法捕获B，最终被Exception捕获"); // 执行
        }

        // 测试3：catch B 无法捕获C
        try {
            throw new C(); // 抛C
        } catch (B e) {
            System.out.println("捕获到C"); // 不执行
        } catch (Exception e) {
            System.out.println("catch B 无法捕获C，最终被Exception捕获"); // 执行
        }
        //JVM 会按catch块的编写顺序依次判断，父类C的 catch 会先匹配到A类型异常，导致子类A的 catch 永远无法执行，因此编译器直接报错（避免无效代码）。
        //总结
        //核心判断：JVM 通过抛出的异常对象 instanceof catch声明的类型来匹配异常处理器，这是异常捕获的底层逻辑；
        //继承影响：因为子类对象 instanceof 父类类型 → true，所以父类 catch 能捕获子类异常，反之则不行；
        //面试要点：答出 “instanceof 判断”+“继承链遍历”，就能精准解释异常捕获的匹配规则，体现对 JVM 底层的理解。
    }

}

/**
 * Error（JVM 级错误，无需捕获，编码规避）
 * Error 是 JVM 自身的严重错误，程序无法恢复，以下是最常见的几种：
 * 表格
 * 异常类型	中文名称	出现场景	规避方式
 * StackOverflowError	栈溢出错误	1. 无限递归调用（如方法自身无终止条件调用）
 * 2. 方法调用层级过深	1. 检查递归终止条件
 * 2. 优化调用层级
 * OutOfMemoryError (OOM)	内存溢出错误	1. 创建大量大对象且不释放（如 List 无限添加元素）
 * 2. 内存泄漏（如静态集合持有对象）
 * 3. 堆内存配置过小	1. 优化对象生命周期
 * 2. 调整 JVM 堆内存参数
 * 3. 排查内存泄漏
 * NoClassDefFoundError	类未找到错误	1. 编译后删除了类文件
 * 2. 类加载器加载类失败
 * 3. 依赖包缺失	1. 检查依赖包是否完整
 * 2. 确认类路径配置
 * VirtualMachineError	虚拟机错误	JVM 内部故障（如 GC 失败、JVM 崩溃）	重启 JVM，排查 JVM 配置 / 硬件问题
 * UnsupportedClassVersionError	不支持的类版本错误	编译时用高版本 JDK，运行时用低版本 JDK	统一编译和运行的 JDK 版本
 * <p>
 * <p>
 * 二、运行时异常（Unchecked，编译不强制处理，优先编码规避）
 * 这类异常是程序逻辑错误导致，编译器不强制处理，但运行时未捕获会终止程序，以下是面试 / 开发中最常见的：
 * 表格
 * 异常类型	中文名称	出现场景	处理建议
 * NullPointerException (NPE)	空指针异常	调用 null 对象的方法 / 属性（如 String str = null; str.length();）	1. 判空（if (obj != null)）
 * 2. 使用 Optional 类
 * ArrayIndexOutOfBoundsException	数组下标越界异常	访问数组时下标超出范围（如 int[] arr = {1}; arr[1];）	检查下标范围，用arr.length判断
 * IndexOutOfBoundsException	索引越界异常	List/Set 等集合访问索引越界（父类，包含数组下标越界）	同上，集合用size()判断
 * ArithmeticException	算术异常	除数为 0（int a = 1 / 0;）	计算前检查除数是否为 0
 * ClassCastException	类型转换异常	强制转换不兼容的类型（如 Object obj = "abc"; Integer num = (Integer)obj;）	1. 用instanceof判断类型
 * 2. 避免强制类型转换
 * IllegalArgumentException	非法参数异常	方法传入不符合规则的参数（如传负数给要求正数的方法）	方法入参校验（如判断参数范围）
 * IllegalStateException	非法状态异常	对象处于不适合的状态时调用方法（如关闭流后继续读写）	检查对象状态，避免非法操作
 * ConcurrentModificationException	并发修改异常	遍历集合时修改集合（如 foreach 遍历 List 时 add/remove 元素）	1. 用迭代器的 remove ()
 * 2. 用并发集合（如 CopyOnWriteArrayList）
 * NumberFormatException	数字格式异常	字符串转数字失败（如 Integer.parseInt("abc");）	1. 校验字符串格式
 * 2. 捕获异常提示用户
 * UnsupportedOperationException	不支持的操作异常	调用不可变集合的修改方法（如 List list = Collections.unmodifiableList(new ArrayList()); list.add(1);）	避免修改不可变对象
 * <p>
 * <p>
 * 三、受检异常（Checked，编译强制处理，必须捕获 / 声明抛出）
 * 这类异常是程序外部环境导致（如文件不存在、网络断开），编译器强制要求处理（try-catch 或 throws），以下是高频例子：
 * 表格
 * 异常类型	中文名称	出现场景	处理建议
 * IOException	IO 异常	父类，包含所有 IO 相关异常（文件读写、流操作失败）	try-catch 捕获，释放 IO 资源（或用 try-with-resources）
 * FileNotFoundException	文件未找到异常	读取 / 写入不存在的文件（new FileInputStream("不存在的文件.txt");）	1. 检查文件路径
 * 2. 捕获异常提示文件不存在
 * EOFException	文件结束异常	读取文件时提前到达文件末尾	检查文件完整性，捕获异常处理
 * SQLException	SQL 异常	数据库操作失败（如连接失败、SQL 语法错误、表不存在）	1. 捕获异常记录日志
 * 2. 回滚事务（如有）
 * 3. 提示用户数据库错误
 * ClassNotFoundException	类未找到异常	动态加载类失败（如 Class.forName("com.mysql.cj.jdbc.Driver"); 驱动类缺失）	检查依赖包，确认类全限定名正确
 * InterruptedException	中断异常	线程休眠 / 等待时被中断（Thread.sleep(1000); 被其他线程 interrupt ()）	1. 捕获后恢复中断状态（Thread.currentThread().interrupt();）
 * 2. 处理中断逻辑
 * ParseException	解析异常	日期 / 字符串解析失败（如 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); sdf.parse("2024/01/01");）	1. 校验解析格式
 * 2. 捕获异常提示格式错误
 * CloneNotSupportedException	克隆不支持异常	调用 clone () 但类未实现 Cloneable 接口	实现 Cloneable 接口，或改用拷贝构造函数
 * 总结
 * Error：核心是StackOverflowError、OutOfMemoryError，属于 JVM 层面，无需捕获，重点在编码 / 配置规避；
 * 运行时异常：核心是NullPointerException、ArrayIndexOutOfBoundsException，属于逻辑错误，优先编码规避（如判空、校验下标），而非单纯 try-catch；
 * 受检异常：核心是IOException、SQLException，属于外部环境错误，编译强制处理，需捕获或声明抛出，同时要处理资源释放 / 用户提示。
 * 这些例子覆盖了面试中 90% 以上的异常考点，记住 “场景 + 处理建议”，能快速区分异常类型并给出合理的处理方案。
 */

