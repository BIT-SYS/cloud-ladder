package cloudladder.core.knowledge.implementation;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MethodImplementationUtil {
    public static MethodImplementation getImplementation(String serverBase) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(serverBase + "/method-implementation/get-method")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String json = response.body().string();
            Gson gson = new Gson();

            ArrayList<Object> arr = gson.fromJson(json, ArrayList.class);
            String type = (String) arr.get(0);

            if (type.equals("http")) {
                String x = gson.toJson(arr.get(1));
                HTTPMethodImplementation implementation = gson.fromJson(x, HTTPMethodImplementation.class);
                return implementation;
            } else if (type.equals("jar")) {
                String x = gson.toJson(arr.get(1));
                JarMethodImplementation implementation = gson.fromJson(x, JarMethodImplementation.class);
                return implementation;
            }

            return null;
        } catch (IOException e) {
            return null;
        }
    }
}
