package cloudladder.core.object;

import cloudladder.converter.Converter;
import cloudladder.converter.ConverterFactory;
import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;
import cloudladder.core.runtime.CLRtFrame;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CLHTTPFunction extends CLFunction {
    public String url;
    public String method;
//    public ArrayList<String> inputTypes;
//    public String outputType;
    public ArrayList<String> converterTo;
    public String converterBack;
    public String bodyComposer;

    public CLHTTPFunction(ArrayList<String> paramNames, boolean catchAll, String url, String method, ArrayList<String> converterTo, String converterBack, String bodyComposer) {
        super(paramNames, catchAll);
        this.url = url;
        this.method = method;
//        this.inputTypes = inputTypes;
//        this.outputType = outputType;
        this.converterTo = converterTo;
        this.converterBack = converterBack;
        this.bodyComposer = bodyComposer;
    }

    public CLObject execute(CLRtFrame frame) throws CLRuntimeError {
        HashMap<String, byte[]> body = new HashMap<>();

        for (int i = 0; i < this.paramsNames.size(); i++) {
            String varName = this.paramsNames.get(i);
            CLObject arg = frame.scope.getOwnVariable(varName);
            Converter converter = ConverterFactory.getConverter(this.converterTo.get(i));
            byte[] converted = converter.convertBack(arg);
            body.put(varName, converted);
        }

        // todo assemble request body
//        RequestBody requestBody = RequestBody.create()

        Request request = new Request.Builder()
                .url(this.url)
                .method(this.method, null)
                .build();
        OkHttpClient client = new OkHttpClient();

        try {
            Response response = client.newCall(request).execute();
            // todo convert to any type
            String content = response.body().string();
            CLString newString = new CLString(content);
            return newString;
        } catch (IOException e) {
            throw new CLRuntimeError(CLRuntimeErrorType.NetworkError, "cannot send request to `" + this.url + "`");
        }
    }
}
