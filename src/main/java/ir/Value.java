package ir;

import ast.*;
import symboltable.SimpleType;
import symboltable.Type;

import static util.Type.getType;

public class Value {
  // 123 is not symbol -> literal is not symbol
  // till_now is symbol
  public boolean is_symbol;
  public boolean is_temp;
  public String value;
  public Type type;

  Value(boolean is_symbol, String value, Type type) {
    this.is_symbol = is_symbol;
    this.value = value;
    this.type = type;
  }

  // for `Temp` Symbol
  public Value(Temp t) {
    is_symbol = true;
    is_temp = true;
    this.value = t.toString();
  }

  public static Value Symbol(String name, Type type) {
    return new Value(true, name, type);
  }
  public static Value Symbol(String name, String type_name) {
    return Symbol(name, util.Type.getType(type_name));
  }

  public static Value Literal(String raw_value, Type type) {
    return new Value(false, raw_value, type);
  }

  public static Value LiteralNumber(String raw_value) {
    return Value.Literal(raw_value, new SimpleType("Number"));
  }

  public static Value LiteralBoolean(String true_or_false) {
    return Value.Literal(true_or_false, new SimpleType("Boolean"));
  }


  public static Value Procedure(String func_name) {
    return new Value(true,func_name,util.Type.getType("Proc"));
  }

  public Value(ExpressionNode exp) {
    if (exp instanceof Literal) {
      Literal l = (Literal) exp;
      this.is_symbol = false;
      this.type = l.evalType;
      this.value = l.raw;
    } else if (exp instanceof Temp) {
      this.is_symbol = true;
      this.is_temp = true;
      this.type = null;
      // name
      this.value = exp.toString();
    } else if (exp instanceof Identifier) {
      Identifier i = (Identifier) exp;
      this.is_symbol = true;
      this.type = i.evalType;
      this.value = i.name;
    } else if (exp instanceof  FunctionIdentifier) {
      FunctionIdentifier f = (FunctionIdentifier) exp;
      this.is_symbol = true;
      this.type = util.Type.getType("Proc");
      this.value = f.name;
    } else if (exp instanceof Parameter) {
      Parameter p = (Parameter) exp;
      this.is_symbol = true;
      this.type = getType(p.type);
      this.value = p.id.name;
    } else {
      if (exp != null)
        System.err.println(String.format("Error ExpressionNode: %s, %s in IR.Value constructor.", exp.getClass().getName(), exp));
      else {
        System.err.println("null pointer in constructor");
        exp.getChildren();
      }
    }
  }

  public interpreter.Value toInterpreterValue() {
    return new interpreter.Value(this);
  }



  @Override
  public String toString() {
    if (type !=null) {
      return String.format("%s:%s", type,value);
    } else {
      return value.toString();
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Value that = (Value) obj;
    return this.is_symbol == that.is_symbol && this.is_temp == that.is_temp && this.type.equals(that.type) && this.value.equals(that.value);
  }
}
