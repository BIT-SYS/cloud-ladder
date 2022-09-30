package cloudladder.core.compiler.ast;

import com.google.gson.Gson;

public abstract class AST {
    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
