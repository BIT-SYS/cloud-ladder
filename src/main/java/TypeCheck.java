import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.FileInputStream;
import java.io.InputStream;

public class TypeCheck extends CLParserBaseListener {

    public static class TypeChecker extends CLParserBaseListener {
        ParseTreeProperty<String> types = new ParseTreeProperty<>();

        public void exitIntegerLiteral(CLParserParser.IntegerLiteralContext ctx) {
            System.out.println(ctx.getText() + "'s type is Number");
            setType(ctx, "Number");
        }

        public void exitFloatLiteral(CLParserParser.FloatLiteralContext ctx) {
            System.out.println(ctx.getText() + "'s type is Number");
            setType(ctx, "Number");
        }

        public void exitLiteral(CLParserParser.LiteralContext ctx) {
            if (null != ctx.STRING_LITERAL()) {
                System.out.println(ctx.getText() + "'s type is String");
                setType(ctx, "String");
            } else if (null != ctx.BOOL_LITERAL()) {
                System.out.println(ctx.getText() + "'s type is Boolean");
                setType(ctx, "Boolean");
            } else {
                System.out.println(ctx.getText() + "'s type is " + getType(ctx.children.get(0)));
                setType(ctx, getType(ctx.children.get(0)));
            }
        }

        public void exitPrimary(CLParserParser.PrimaryContext ctx) {
            if (null != ctx.LPAREN()) {
                setType(ctx, getType(ctx.expression()));
            } else {
                setType(ctx, getType(ctx.children.get(0)));
            }
        }

        public void exitExpression(CLParserParser.ExpressionContext ctx) {
            if (null != ctx.primary()) {
                setType(ctx, getType(ctx.primary()));
            } else if (null != ctx.bop.getText()) {
                String type1 = getType(ctx.expression(0));
                String type2 = getType(ctx.expression(1));
                if (type1.equals(type2)) {
                    setType(ctx, type1);
                } else {
                    err();
                }
            } else if (null != ctx.listInitializer()) {
                setType(ctx, getType(ctx.listInitializer()));
            }
            //todo other expr...
        }

        public void exitVariableDeclaration(CLParserParser.VariableDeclarationContext ctx) {
            String typeType = ctx.typeType().getText().replace(" ", "");
            String exprType = getType(ctx.expression());

            System.out.println("typeType is " + typeType + " & expressionType is " + exprType);

            if (!typeType.equals(exprType)) {
                err();
            }
        }

        public void setType(ParseTree node, String type) {
            types.put(node, type);
        }

        public String getType(ParseTree node) {
            return types.get(node);
        }

        public void err() {
            System.out.println("I am ERROR!!!!!!");
            //todo
        }

    }

    public static void main(String[] args) throws Exception {
        String inputFile = null;
        if (args.length > 0) inputFile = args[0];
        InputStream is = System.in;
        if (inputFile != null) {
            is = new FileInputStream(inputFile);
        }

        System.out.println("started");
        ANTLRInputStream input = new ANTLRInputStream(is);
        CLParserLexer lexer = new CLParserLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CLParserParser parser = new CLParserParser(tokens);
        parser.setBuildParseTree(true);
        ParseTree tree = parser.program();
        System.out.println(tree.toStringTree(parser));

        ParseTreeWalker walker = new ParseTreeWalker();
        TypeChecker typeChecker = new TypeChecker();
        walker.walk(typeChecker, tree);
        System.out.println("Type checking finished.");
    }
}
