package ast;

import grammar.CLParserParser;

public class ProcDef extends Node {
    public ProcDef(CLParserParser.ProcedureDeclarationContext ctx) {
        super(ctx);
    }

    @Override
    public String printNode() {
        return "ProcDef";
    }
}
