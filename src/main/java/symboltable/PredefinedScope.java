package symboltable;

import interpreter.builtIn.image.BuiltInGetString;
import interpreter.builtIn.image.BuiltInImRead;

import static util.Symbol.*;

public class PredefinedScope extends BaseScope {
    public PredefinedScope(Scope enclosingScope) {
        super(enclosingScope);

        define(mkproc(this, "input", "String"));
        define(mkproc(this, "split", "List<String>", mkprmtr("String", "self")));
        define(mkproc(this, "print", "String", mkprmtr("String")));
        define(mkproc(this, "print", "String", mkprmtr("Number")));

        define(mkproc(this, "toString", "String", mkprmtr("TypeA", "self")));
        define(mkproc(this, "toNumber", "Number", mkprmtr("String", "self")));

        //todo
        define(mkproc(this, "filter", "List<TypeA>", mkprmtr("List<TypeA>", "self"), mkprmtr("Proc")));
        define(mkproc(this, "map", "List<TypeA>", mkprmtr("List<TypeA>", "self"), mkprmtr("Proc")));
        define(mkproc(this, "forEach", "?", mkprmtr("List<TypeA>", "self"), mkprmtr("Proc")));
        define(mkproc(this, "reduce", "Number", mkprmtr("Proc"), mkprmtr("List<TypeA>"), mkprmtr("TypeA")));

        define(builtin(this, new BuiltInImRead(), new SimpleType("Image")));
        define(builtin(this, new BuiltInGetString(), new SimpleType("String")));
    }

    @Override
    public String getScopeName() {
        return "stdlib";
    }
}
