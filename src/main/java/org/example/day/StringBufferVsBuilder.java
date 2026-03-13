package org.example.day;

public class StringBufferVsBuilder {

    /**
     * String 创建的是一个不可变对象，在每次发生修改的时候，都会创建一个新对象，然后重新指向这个对象，这在多次，循环修改字符串的场景下会带来
     * 额外的性能损耗，且会创建大量临时对象，也容易触发GC。
     * StringBuffer和StringBuilder创建的则是一个可变对象，每次修改都是直接操作已有的对象，相比之下损耗小，效率高。
     * 区别在于StringBuffer为了保证线程安全，在其相关方法上使用了synchronized关键字修饰，这使得多线程场景下，每个线程必须先获得锁，才能执行
     * 相关方法，这就导致了线程为了获取锁产生的等待，而单线程情况下，锁的空转和维持也会带来损耗，所以在性能方面，StringBuilder要更优越一些，、
     * 但是在多线程场景下，为了保证变量不因多线程操作而出现数据异常应该采用StringBuffer，无需考虑线程安全的场景就选择StringBuilder。、
     * 此外在常量，非循环少量的字符串修改场景下，“+”直接拼接字符串并不会有性能损耗，因为编译阶段jvm会直接对其进行优化，而对于变量，循环多次的字符串修改
     * 场景，jvm由于无法预知最终结果和用户输入，无法进行优化，就需要避免“+”的使用。
     * String 本身线程安全：核心是不可变性，对象内容一旦创建就无法修改，多线程读取无错乱；
     * 多线程操作 String 出问题的根源：不是 String 对象不安全，而是String 类型的变量引用被多线程修改（指向新对象），导致最终引用指向不可控；
     * 区分关键：“String 对象” ≠ “String 变量”—— 对象不可变（安全），变量引用可改（多线程修改引用会出问题）；
     * StringBuffer 解决的是：多线程修改对象内容的安全问题（直接操作内部数组，无需改引用），而 String 因不可变，根本不存在 “修改对象内容” 的场景。
     */

    // 测试 StringBuffer（线程安全）
    private static void testStringBuffer() throws InterruptedException {
        StringBuffer sb = new StringBuffer();
        // 启动10个线程，每个线程追加1000次"a"
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    sb.append("a");
                }
            }).start();
        }
        // 等待所有线程执行完
        Thread.sleep(1000);
        System.out.println("StringBuffer 最终长度：" + sb.length()); // 预期 10000
    }

    // 测试 StringBuilder（线程不安全）
    private static void testStringBuilder() throws InterruptedException {
        StringBuilder sb = new StringBuilder();
        // 同样启动10个线程，每个线程追加1000次"a"
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    sb.append("a");
                }
            }).start();
        }
        Thread.sleep(1000);
        System.out.println("StringBuilder 最终长度：" + sb.length()); // 大概率小于10000
    }

    public static void main(String[] args) throws InterruptedException {
        testStringBuffer();
        testStringBuilder();
    }
}