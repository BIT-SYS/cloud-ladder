package interpreter;

import symboltable.CompositeType;
import symboltable.SimpleType;
import symboltable.Type;

import static util.Type.getType;


public class Value {
  Type type;
  Object value;

  Object buildValue(Type t, String raw_value){
    if (t.equals(new SimpleType("Boolean"))) {
        return Boolean.valueOf(raw_value);
    } else if (t.equals(new SimpleType("String"))) {
        return raw_value;
    } else if (t.equals(new SimpleType("Number"))) {
      raw_value = raw_value.replace("_","");
      if (raw_value.startsWith("0x") || raw_value.startsWith("0X")) {
        raw_value = raw_value.substring(2);
        Long l= Long.parseLong(raw_value, 16);
        return Float.valueOf(l);
      } else if (raw_value.startsWith("0b")) {
        raw_value = raw_value.substring(2);
        Long l= Long.parseLong(raw_value, 2);
        return Float.valueOf(l);
      } else if (raw_value.startsWith("0") && raw_value.length() >= 2 && !raw_value.startsWith("00")) {
        // oct
        Long l= Long.parseLong(raw_value, 8);
        return Float.valueOf(l);
      } else {
        return Float.valueOf(raw_value);
      }
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

  public Value( ProcSignature p) {
    type = new SimpleType("Proc");
    value = p;
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
      if (((SimpleType) type).name.equals("Number")) {
        return Value.valueOf(this.getFloat() + v.getFloat());
      } else if (((SimpleType) type).name.equals("String")) {
        return Value.valueOf(this.getString() + v.getString());
      }
    }
    throw new RuntimeException("type error when add.");
  }

  public Value sub(Value v) {
    if (type instanceof SimpleType) {
      if (((SimpleType) type).name.equals("Number")) {
        return Value.valueOf(this.getFloat() - v.getFloat());
      }
    }
    throw new RuntimeException("type error when sub.");
  }

  public Value mul(Value v) {
    if (type.equals(getType("Number"))) {
      return Value.valueOf(getFloat() * v.getFloat());
    }
    throw new RuntimeException("type error when mul.");
  }

  public Value div(Value v) {
    if (type.equals(getType("Number"))) {
      if (v.getFloat() == 0) {
        throw new RuntimeException("? error #DIV/0!");
      }
      return Value.valueOf(getFloat() / v.getFloat());
    }
    throw new RuntimeException("type error when div.");
  }

  @Override
  public String toString() {
    return  type +
            ":" + value;
  }
}
