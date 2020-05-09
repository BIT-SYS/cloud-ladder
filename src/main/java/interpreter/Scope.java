package interpreter;

import symboltable.Type;

import java.util.HashMap;

public class Scope {
  Scope PrevScope;

  // Name: value
  HashMap<String, Value> scope;

  Value resolve_local(String name) {

    return null;
  }

  // recursively find Symbol's value
  public Value resolve(String name) {

    return null;
  }

  public void Insert(String name, Value value) {

  }

  public void Update(String name, Value value) {

  }
}
