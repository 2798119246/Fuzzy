package org.example.licode;

import java.util.ArrayList;
import java.util.List;

/**
 *  最小栈，用list模仿栈操作，用第二个栈来存最小值，也可以直接用Deque<>来写，相对简洁一点
 */
public class MinStack {

    private List<Integer> stack;
    private List<Integer> minStack;

    public MinStack() {
        stack = new ArrayList<>();
        minStack = new ArrayList<>();
        minStack.add(Integer.MAX_VALUE);
    }

    public void push(int val) {
        stack.add(val);
        minStack
                .add(minStack.get(minStack.size() - 1) > val ? val : minStack.get(minStack.size() - 1));

    }

    public void pop() {
        if (!stack.isEmpty()) {
            stack.remove(stack.size() - 1);
        }
        if (!minStack.isEmpty()) {
            minStack.remove(minStack.size() - 1);
        }
    }

    public int top() {
        return stack.get(stack.size() - 1);
    }

    public int getMin() {
        return minStack.get(minStack.size() - 1);
    }
}
