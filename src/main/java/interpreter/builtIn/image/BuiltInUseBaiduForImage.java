package interpreter.builtIn.image;

import interpreter.ExternalProcedureTemplate;
import interpreter.Interpreter;
import ir.Value;
import symboltable.SimpleType;
import util.ApiState;

import java.util.ArrayList;

public class BuiltInUseBaiduForImage extends ExternalProcedureTemplate {
    public BuiltInUseBaiduForImage() {
        super("useBaiduForImage", new ArrayList<Value>() {{
            add(Value.Symbol("token", new SimpleType("String")));
        }});
    }

    @Override
    public interpreter.Value external(Interpreter context) {
        interpreter.Value token = context.current_scope.resolve("token");
        ApiState state = ApiState.getSingleton();
        state.image_provider = "baidu";
        state.image_token = token.getString();
        return interpreter.Value.valueOf(true);
    }
}
