package ast.node;

import ast.AstVisitor;
import grammar.CLParserParser;

public class ProcDef extends Node {
    public ProcDef(CLParserParser.ProcedureDeclarationContext ctx) {
        super(ctx);
    }

    @Override
    public String toString() {
        return "proc-def";
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}
