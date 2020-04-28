package symboltable;

import static symboltable.Utils.mkprmtr;
import static symboltable.Utils.mkproc;

public class PredefinedScope extends BaseScope {
    public PredefinedScope(Scope enclosingScope) {
        super(enclosingScope);

        define(mkproc(this, "input", "String"));
        define(mkproc(this, "split", "List<String>", mkprmtr("String", "self")));
        define(mkproc(this, "print", "?", mkprmtr("String"))); //todo 返回什么？

        define(mkproc(this, "toString", "String", mkprmtr("Number", "self")));
        define(mkproc(this, "toNumber", "Number", mkprmtr("String", "self")));

        //todo
        define(mkproc(this, "filter", "List<Number>", mkprmtr("List<Number>", "self"), mkprmtr("Proc")));
        define(mkproc(this, "map", "List<Number>", mkprmtr("List<Number>", "self"), mkprmtr("Proc")));
        define(mkproc(this, "map", "List<String>", mkprmtr("List<String>", "self"), mkprmtr("Proc")));
        define(mkproc(this, "forEach", "?", mkprmtr("List<String>", "self"), mkprmtr("Proc")));
        define(mkproc(this, "reduce", "Number", mkprmtr("Proc")));
    }

    @Override
    public String getScopeName() {
        return "stdlib";
    }
}
