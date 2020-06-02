package interpreter.builtIn.image;

import com.google.gson.JsonObject;
import interpreter.ExternalProcedureTemplate;
import interpreter.Interpreter;
import ir.Value;
import symboltable.SimpleType;
import util.ApiState;
import util.NetWork;

import java.util.ArrayList;
import java.util.Base64;

public class BuiltInGetAnime extends ExternalProcedureTemplate {
    public BuiltInGetAnime() {
        super("getAnime", new ArrayList<Value>() {{
            add(Value.Symbol("self", new SimpleType("Image")));
        }});
    }

    @Override
    public interpreter.Value external(Interpreter context) {
        // test: im_read("xxx.jpg").getAnime().save("aaa.jpg")
        interpreter.Value self = context.current_scope.resolve("self");
        assert null != self.getBytes();
        String image = Base64.getEncoder().encodeToString(self.getBytes());

        ApiState api = ApiState.getSingleton();

        if (api.image_provider.equals("baidu")) {
            JsonObject json = NetWork.post(
                    "https://aip.baidubce.com/rest/2.0/image-process/v1/selfie_anime?access_token=" + api.image_token,
                    "image", image
            );

            return interpreter.Value.valueOf(Base64.getDecoder().decode(json.get("image").getAsString()));
        } else {
            return null;
        }
    }
}
