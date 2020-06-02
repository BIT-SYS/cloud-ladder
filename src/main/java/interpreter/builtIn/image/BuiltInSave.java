package interpreter.builtIn.image;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import interpreter.ExternalProcedureTemplate;
import interpreter.Interpreter;
import ir.Value;
import okhttp3.*;
import okhttp3.internal.connection.RouteDatabase;
import symboltable.SimpleType;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;

public class BuiltInSave extends ExternalProcedureTemplate {
    public BuiltInSave() {
        super("save", new ArrayList<Value>() {{
            add(Value.Symbol("self", new SimpleType("Image")));
            add(Value.Symbol("path", new SimpleType("String")));
        }});
    }

    @Override
    public interpreter.Value external(Interpreter context) {
        interpreter.Value self = context.current_scope.resolve("self");
        interpreter.Value path = context.current_scope.resolve("path");
        String image = Base64.getEncoder().encodeToString(self.getBytes());
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(new File(path.getString()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            outputStream.write(self.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return interpreter.Value.valueOf(true);
    }
}
