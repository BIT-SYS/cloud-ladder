package interpreter.builtIn.image;

import interpreter.ExternalProcedureTemplate;
import interpreter.Interpreter;
import ir.Value;
import symboltable.SimpleType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class BuiltInImRead extends ExternalProcedureTemplate {
    public BuiltInImRead() {
        super("ImageRead", new ArrayList<Value>() {{
            add(Value.Symbol("path", new SimpleType("String")));
        }});
    }

    @Override
    public interpreter.Value external(Interpreter context) {
        interpreter.Value v = context.current_scope.resolve("path");
        File file = new File(v.getString());
        byte[] bytes = null;

        if (file.exists()) {
            try {
                bytes = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else System.err.println("Image file " + file.getAbsolutePath() + " not exists.");
        return interpreter.Value.valueOf(bytes);
    }
}
