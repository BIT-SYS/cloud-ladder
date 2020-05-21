import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;


public class CallBaiduTest {

    public static void main(String[] args) throws IOException {
        File file = new File("meisuowei.jpg");
        String image = Base64.getEncoder().encodeToString(Files.readAllBytes(file.toPath()));


        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("image", image)
                .build();

        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic?access_token="
                        + Files.readAllLines(new File("bdat").toPath()).get(0))
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            String string = response.body().string();
            System.out.println(string);

            JsonElement jsonElement = JsonParser.parseString(string);
            assert jsonElement.isJsonObject() && !jsonElement.isJsonArray();
            System.out.println(jsonElement.getAsJsonObject().get("words_result"));

            JsonElement words = jsonElement.getAsJsonObject().get("words_result");

            assert words.isJsonObject() && words.isJsonArray();
            for (JsonElement word :
                    words.getAsJsonArray()) {
                System.out.println(word.getAsJsonObject().get("words"));
            }
        }
    }
}
