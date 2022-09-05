//package cloudladder.std.http;
//
//import cloudladder.core.runtime.data.*;
//import cloudladder.core.runtime.env.CLRtEnvironment;
//import cloudladder.core.runtime.env.CLRtScope;
//import cloudladder.std.CLBuiltinFuncAnnotation;
//import cloudladder.utils.CLDataUtils;
//import okhttp3.*;
//
//import java.io.InputStream;
//import java.lang.reflect.Method;
//
//public class CLStdHttp {
//    private static final OkHttpClient client = new OkHttpClient();
//    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
//
//    @CLBuiltinFuncAnnotation(value={"url", "params", "headers"}, name="post")
//    public static void __post__(CLRtEnvironment env) {
//        CLData p1 = env.getVariable("url").getReferee();
//        CLData params = env.getVariable("params").getReferee();
//        CLData headers = env.getOwnVariable("headers").getReferee();
//
//        if (!(p1 instanceof CLString)) {
//            // todo
//            return;
//        }
//
//        String url = ((CLString) p1).getValue();
//
//        Request.Builder builder = new Request.Builder()
//                .url(url);
//
//        if (params != null) {
//            String json = CLDataUtils.toJson(params);
//            RequestBody requestBody = RequestBody.create(json, JSON);
//            builder.post(requestBody);
//        } else {
//            builder.method("post", null);
//        }
//
//        if (headers != null) {
//            for (String key: headers.getStringRefers().keySet()) {
//                CLString data = (CLString) headers.getStringRefers().get(key).getReferee();
//
//                builder.addHeader(key, data.getValue());
//            }
//        }
//
//        Request request = builder.build();
//
//        try {
//            Response response = client.newCall(request).execute();
//            String text = response.body().string();
//            CLString ret = env.newString(text);
//            env.ret(ret.wrap());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @CLBuiltinFuncAnnotation(value={"url"}, name="get")
//    public static void __get__(CLRtEnvironment env) {
//        CLData p1 = env.getVariable("url").getReferee();
//        if (!(p1 instanceof CLString)) {
//            // todo
//            return;
//        }
//
//        String url = ((CLString) p1).getValue();
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//        try {
//            Response response = client.newCall(request).execute();
//            String text = response.body().string();
//            CLString ret = env.newString(text);
//            env.ret(ret.wrap());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @CLBuiltinFuncAnnotation(value={"url", "method", "body", "headers"}, name="raw")
//    public static void __raw__(CLRtEnvironment env) {
//        CLString url = (CLString) env.getOwnVariable("url").getReferee();
//        CLString method = (CLString) env.getOwnVariable("method").getReferee();
//        CLString data = (CLString) env.getOwnVariable("body").getReferee();
//        CLData headers = env.getOwnVariable("headers").getReferee();
//
//        Request.Builder builder = new Request.Builder();
//        builder.url(url.getValue());
//        RequestBody body = RequestBody.create(data.getValue().getBytes());
//        builder.method(method.getValue(), body);
//
//        if (headers != null) {
//            for (String key: headers.getStringRefers().keySet()) {
//                CLString value = (CLString) headers.getStringRefers().get(key).getReferee();
//
//                builder.addHeader(key, value.getValue());
//            }
//        }
//
//        Request request = builder.build();
//
//        try {
//            Response response = client.newCall(request).execute();
//
//            String text = response.body().string();
//            CLString ret = env.newString(text);
//            env.ret(ret.wrap());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @CLBuiltinFuncAnnotation(value={"url", "headers"}, name="stream_get")
//    public static void __streamGet__(CLRtEnvironment env) {
//        CLString url = (CLString) env.getOwnVariable("url").getReferee();
//        CLData headers = env.getOwnVariable("headers").getReferee();
//
//        Request.Builder builder = new Request.Builder();
//        builder.url(url.getValue());
//        builder.method("GET", null);
//
//        if (headers != null) {
//            for (String key: headers.getStringRefers().keySet()) {
//                CLString value = (CLString) headers.getStringRefers().get(key).getReferee();
//
//                builder.addHeader(key, value.getValue());
//            }
//        }
//
//        Request request = builder.build();
//
//        try {
//            Response response = client.newCall(request).execute();
//            InputStream stream = response.body().byteStream();
//
//            CLStream ret = new CLStream(stream);
//            env.ret(ret.wrap());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void run(CLRtEnvironment env) throws Exception {
//        CLRtScope scope = env.getCurrentScope();
//        Class clazz = CLStdHttp.class;
//        CLObject obj = env.newObject();
//
//        for (Method method: clazz.getMethods()) {
//            if (method.isAnnotationPresent(CLBuiltinFuncAnnotation.class)) {
//                CLBuiltinFuncAnnotation annotation = method.getAnnotation(CLBuiltinFuncAnnotation.class);
//                String[] params = annotation.value();
//                String name = annotation.name();
//
//                CLBuiltinFunction func = new CLBuiltinFunction(method, scope, params);
//                obj.addStringRefer(name, func.wrap());
//            }
//        }
//
//        env.addVariable("$exports", obj.wrap());
//    }
//}
