package util;

import grammar.CLParserParser;

public class Antlr {
    static public String getSource(CLParserParser.ProgramContext ctx) {
        return ctx.start.getInputStream().getText(ctx.getSourceInterval());
    }
}
