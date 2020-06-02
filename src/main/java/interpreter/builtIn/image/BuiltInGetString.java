package interpreter.builtIn.image;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import interpreter.ExternalProcedureTemplate;
import interpreter.Interpreter;
import ir.Value;
import symboltable.SimpleType;
import util.ApiState;
import util.NetWork;

import java.util.ArrayList;
import java.util.Base64;

public class BuiltInGetString extends ExternalProcedureTemplate {
    public BuiltInGetString() {
        super("getString", new ArrayList<Value>() {{
            add(Value.Symbol("self", new SimpleType("Image")));
        }});
    }

    @Override
    public interpreter.Value external(Interpreter context) {
        // test: print(im_read("guess.bmp").getString())
        interpreter.Value self = context.current_scope.resolve("self");
        String image = Base64.getEncoder().encodeToString(self.getBytes());

        ApiState api = ApiState.getSingleton();

        if (api.image_provider.equals("baidu")) {
            JsonObject json = NetWork.post(
                    "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic?access_token=" + api.image_token,
                    "image", image
            );

            StringBuilder sb = new StringBuilder();

            JsonElement words = json.get("words_result");

            assert words.isJsonArray();
            for (JsonElement word :
                    words.getAsJsonArray()) {
                sb.append(word.getAsJsonObject().get("words").getAsString());
            }

            return interpreter.Value.valueOf(sb.toString());
        } else {
            return null;
        }
    }
}
