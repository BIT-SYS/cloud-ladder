package interpreter;

import java.util.List;

public class Stack {
  java.util.Stack<Value> stack;

  public void push(Value v) {
    stack.push(v);
  }

  public Value pop() {
    return stack.pop();
  }
}
