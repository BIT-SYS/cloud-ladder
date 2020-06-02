package interpreter.builtIn.image;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import interpreter.ExternalProcedureTemplate;
import interpreter.Interpreter;
import ir.Value;
import okhttp3.*;
import symboltable.SimpleType;
import util.ApiState;
import util.NetWork;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class BuiltInDetect extends ExternalProcedureTemplate {
    private final static List<String> cats =
            Arrays.asList("plant", "animal", "car");

    public BuiltInDetect() {
        super("detect", new ArrayList<Value>() {{
            add(Value.Symbol("self", new SimpleType("Image")));
            add(Value.Symbol("cat", new SimpleType("String")));
        }});
    }

    @Override
    public interpreter.Value external(Interpreter context) {
        // test: im_read("xxx.jpg").getAnime().save("aaa.jpg")
        interpreter.Value self = context.current_scope.resolve("self");
        String cat = context.current_scope.resolve("cat").getString();
        assert cats.contains(cat);
        String image = Base64.getEncoder().encodeToString(self.getBytes());

        ApiState api = ApiState.getSingleton();

        if (api.image_provider.equals("baidu")) {

            JsonObject json = NetWork.post(
                    "https://aip.baidubce.com/rest/2.0/image-classify/v1/" + cat + "?access_token=" + api.image_token,
                    "image", image
            );

            return interpreter.Value.valueOf(json.get("result").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString());
        } else {
            return null;
        }
    }
}
