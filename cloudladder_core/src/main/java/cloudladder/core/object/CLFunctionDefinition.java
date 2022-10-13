package cloudladder.core.object;

import lombok.AllArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@CLObjectAnnotation(typeIdentifier = "function_definition")
public class CLFunctionDefinition extends CLObject {
    public CLCodeObject codeObject;
    public ArrayList<String> params;
    public boolean catchAll;

    @Override
    public String getTypeIdentifier() {
        return "function_definition";
    }

    @Override
    public String defaultStringify() {
        return "function_definition@" + this.id;
    }
}
