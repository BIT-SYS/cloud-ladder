package interpreter;

import ir.IRNodeInterface;
import ir.Value;

import java.util.Stack;

public class CallStack {
  public static class CallFrame {
    IRNodeInterface next_ir;
    Value result;

    public CallFrame(IRNodeInterface next_ir, Value result) {
      this.next_ir = next_ir;
      this.result = result;
    }
  }
  Stack<CallFrame> call_stack;
  CallStack(){
    call_stack = new Stack<>();
  }

  public void push(IRNodeInterface next_ir, Value result) {
    call_stack.push(new CallFrame(next_ir, result));
  }

  public IRNodeInterface getNextIR() {
    return call_stack.peek().next_ir;
  }

  public Value getResult() {
    return call_stack.peek().result;
  }

  public CallFrame pop() {
    return call_stack.pop();
  }
}
