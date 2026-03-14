package org.example.day;

import java.util.ArrayList;
import java.util.LinkedList;

public class ArrListAndLinkedList {

    public static void main(String[] args) {
        testStringBuilder();
    }

    /**
     * ArrayList是在数组的基础上模拟出来的可扩容动态数组，由于数组的数据结构特性和其在内存中的存储方式，ArrayList的查找效率和尾插效率较高
     * 数组的内容存储在一片连续内存里，查找数据时直接使用索引就可以算出对应的内存地址，因此，如果要在其中间增删元素，
     * 这个元素后续的所有元素都需要前移或后移，开销较大。
     * LinkedList则是双向链表，它每一个节点都存储了上下节点的指针，以及节点内容，如此以来双向链表的所有元素都可以在内存中分散存储，无需一整片连续的内存
     * 此种特性下，他的增删效率就很高，只需要修改对应节点的指针信息，无需所有后续节点进行迁移，但是也因此，其查询效率就较低，它只能从头会从后一个个查找，虽然LinkedList
     * 对此做了优化，每次查询先判断从头查询快还是从后查询快，但是其查询效率依然不如ArrayList，其节点分散存储，不能直接通过索引查询到内存地址。
     * 所以  头尾增删修改多且中间增删修改多 以及查询少的场景LinkedList更好，而增删少，查询多，或者尾部增删多的场景，ArrayList更好。
     * LinkedList “增删效率高” 是有前提的：仅当「已找到目标节点」（如头尾操作）时才成立；若需先遍历找中间节点，增删整体效率（O (n)）与 ArrayList 接近，甚至略差（链表缓存命中率低）
     */
    static void testStringBuilder() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        for (String s : list) {
            System.out.println(s);
        }
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("a");
        linkedList.add("b");
        linkedList.add("c");
        linkedList.set(1, "d");
        for (String s : linkedList) {
            System.out.println(s);
        }
    }
}
