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
        OkHttpClient client = new OkHttpClient();

        if (api.image_provider.equals("baidu")) {
            RequestBody body = new FormBody.Builder()
                    .add("image", image)
                    .build();

            Request request = null;
            request = new Request.Builder()
                    .url("https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic?access_token="
                            + api.image_token)
                    .post(body)
                    .build();

            StringBuilder sb = new StringBuilder();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                String string = response.body().string();

                JsonElement jsonElement = JsonParser.parseString(string);
                assert jsonElement.isJsonObject() && !jsonElement.isJsonArray();

                JsonElement words = jsonElement.getAsJsonObject().get("words_result");

                assert words.isJsonArray();
                for (JsonElement word :
                        words.getAsJsonArray()) {
                    sb.append(word.getAsJsonObject().get("words").getAsString());
                }

            } catch (IOException e) {
                System.err.println("兄啊是不是你的网不行？");
                e.printStackTrace();
            }

            return interpreter.Value.valueOf(sb.toString());
        } else {
            return null;
        }
    }
}
