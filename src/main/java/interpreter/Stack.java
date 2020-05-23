package interpreter;

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

  public Value peek() {
    return stack.peek();
  }

  public Value get(int i) {return stack.get(stack.size() - 1 - i);}

  @Override
  public String toString() {
    return stack.toString();
  }
}
