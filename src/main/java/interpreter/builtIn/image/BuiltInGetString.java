package interpreter.builtIn.image;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import interpreter.ExternalProcedureTemplate;
import interpreter.Interpreter;
import ir.Value;
import okhttp3.*;
import symboltable.SimpleType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
        // test: print(im_read("erciyuan.jpg").getString())
        interpreter.Value self = context.current_scope.resolve("self");
        String image = Base64.getEncoder().encodeToString(self.getBytes());

        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("image", image)
                .build();

        Request request = null;
        try {
            request = new Request.Builder()
                    .url("https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic?access_token="
                            //todo 换一种给access_token的方式
                            + Files.readAllLines(new File("bdat").toPath()).get(0))
                    .post(body)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            String string = response.body().string();

            JsonElement jsonElement = JsonParser.parseString(string);
            assert jsonElement.isJsonObject() && !jsonElement.isJsonArray();

            JsonElement words = jsonElement.getAsJsonObject().get("words_result");

            assert words.isJsonObject() && words.isJsonArray();
            for (JsonElement word :
                    words.getAsJsonArray()) {
                sb.append(word.getAsJsonObject().get("words").getAsString());
            }

        } catch (IOException e) {
            System.err.println("兄啊是不是你的网不行？");
            e.printStackTrace();
        }

        return interpreter.Value.valueOf(sb.toString());
    }
}
