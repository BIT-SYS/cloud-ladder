package symboltable;

import interpreter.builtIn.BuiltInInput;
import interpreter.builtIn.BuiltInPrint;
import interpreter.builtIn.image.BuiltInGetString;
import interpreter.builtIn.image.BuiltInImRead;

import static util.Symbol.*;

public class PredefinedScope extends BaseScope {
    public PredefinedScope(Scope enclosingScope) {
        super(enclosingScope);

        define(mkproc(this, "split", "List<String>", mkprmtr("String", "self")));

        define(mkproc(this, "toString", "String", mkprmtr("TypeA", "self")));
        define(mkproc(this, "toNumber", "Number", mkprmtr("String", "self")));

        //todo
        define(mkproc(this, "filter", "List<TypeA>", mkprmtr("List<TypeA>", "self"), mkprmtr("Proc")));
        define(mkproc(this, "map", "List<TypeA>", mkprmtr("List<TypeA>", "self"), mkprmtr("Proc")));
        define(mkproc(this, "forEach", "?", mkprmtr("List<TypeA>", "self"), mkprmtr("Proc")));
        define(mkproc(this, "reduce", "Number", mkprmtr("Proc"), mkprmtr("List<TypeA>"), mkprmtr("TypeA")));

        define(builtin(this, new BuiltInImRead(), new SimpleType("Image")));
        define(builtin(this, new BuiltInGetString(), new SimpleType("String")));
        define(builtin(this, new BuiltInInput(), new SimpleType("String")));
        define(builtin(this, new BuiltInPrint(), new SimpleType("String")));
        define(builtin(this, new BuiltInPrint("Number"), new SimpleType("String")));
    }

    @Override
    public String getScopeName() {
        return "stdlib";
    }
}
