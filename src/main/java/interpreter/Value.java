package interpreter;

import symboltable.CompositeType;
import symboltable.SimpleType;
import symboltable.Type;


public class Value {
  Type type;
  Object value;

  Object buildValue(Type t, String raw_value){
    if (t.equals(new SimpleType("Boolean"))) {
        return Boolean.valueOf(raw_value);
    } else if (t.equals(new SimpleType("String"))) {
        return raw_value;
    } else if (t.equals(new SimpleType("Number"))) {
        return Float.valueOf(raw_value);
    } else if (t instanceof CompositeType) {
        if(((CompositeType) t).container.equals("List")) {
          // ???
//          Object v =  buildValue(((CompositeType) t).element, raw_value)

        } else {
          throw new RuntimeException("Invalid Type");
        }
    } else {
      throw new RuntimeException("Invalid Type");
    }
    /// ???
    return null;
  }

  public Value(Type t, Object v) {
    type = t;
    value = v;
  }

  public Value(ir.Value v) {
    this.type = v.type;
    value = buildValue(v.type, v.value);
  }

  // must be String simple type.
  public String getString() {
    return (String)value;
  }

  public Float getFloat() {
    return (Float)value;
  }

  public Boolean getBoolean() {
    return (Boolean) value;
  }

  static public Value valueOf(Float a) {
    return new Value(new SimpleType("Number"), a);
  }

  static public Value valueOf(String a) {
    return new Value(new SimpleType("String"), a);
  }

  static public Value valueOf(Boolean a) {
    return new Value(new SimpleType("Boolean"), a);
  }

  public Value add(Value v,Interpreter context) {
    if (type instanceof SimpleType) {
      if (((SimpleType) type).name ==  "Number") {
        return Value.valueOf(this.getFloat() + v.getFloat());
      } else if (((SimpleType) type).name == "String") {
        return Value.valueOf(this.getString() + v.getString());
      }
    }
    throw new RuntimeException("type error when add.");
  }

  public Value sub(Value v) {
    if (type instanceof SimpleType) {
      if (((SimpleType) type).name ==  "Number") {
        return Value.valueOf(this.getFloat() - v.getFloat());
      }
    }
    throw new RuntimeException("type error when sub.");
  }


  @Override
  public String toString() {
    return  type +
            ":" + value;
  }
}
