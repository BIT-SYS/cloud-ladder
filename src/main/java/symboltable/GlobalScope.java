package symboltable;

public class GlobalScope extends BaseScope {
    public GlobalScope(Scope enclosingScope) {
        super(enclosingScope);
    }

    public String getScopeName() {
        return "globals";
    }
}