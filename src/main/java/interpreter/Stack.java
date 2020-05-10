package interpreter;

import java.util.List;

public class Stack {
  java.util.Stack<Value> stack;

  public Stack() {
    stack = new java.util.Stack<>();
  }

  public void push(Value v) {
    stack.push(v);
  }

  public Value pop() {
    return stack.pop();
  }

  @Override
  public String toString() {
    return stack.toString();
  }
}
