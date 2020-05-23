package interpreter.builtIn.image;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import interpreter.ExternalProcedureTemplate;
import interpreter.Interpreter;
import ir.Value;
import okhttp3.*;
import symboltable.SimpleType;
import util.ApiState;

import java.io.IOException;
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
        OkHttpClient client = new OkHttpClient();

        if (api.image_provider.equals("baidu")) {
            RequestBody body = new FormBody.Builder()
                    .add("image", image)
                    .build();

            Request request = new Request.Builder()
                    .url("https://aip.baidubce.com/rest/2.0/image-process/v1/selfie_anime?access_token="
                            + api.image_token)
                    .post(body)
                    .build();

            String string = null;
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                string = response.body().string();

            } catch (IOException e) {
                System.err.println("兄啊是不是你的网不行？");
                e.printStackTrace();
            }

            JsonElement jsonElement = JsonParser.parseString(string);
            assert jsonElement.isJsonObject() && !jsonElement.isJsonArray();
            return interpreter.Value.valueOf(Base64.getDecoder().decode(jsonElement.getAsJsonObject().get("image").getAsString()));
        } else {
            return null;
        }
    }
}
