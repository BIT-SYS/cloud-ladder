package util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.IOException;

public class NetWork {
    final private static OkHttpClient client = new OkHttpClient();

    public static JsonObject post(String url, String name, String value) {

        RequestBody body = new FormBody.Builder()
                .add(name, value)
                .build();

        Request request = new Request.Builder()
                .url(url)
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
        return jsonElement.getAsJsonObject();
    }
}
