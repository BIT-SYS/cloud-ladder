package org.example;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;
import cloudladder.core.object.CLAudio;
import cloudladder.core.object.CLObject;
import cloudladder.core.object.CLString;
import cloudladder.core.runtime.CLRtFrame;
import cloudladder.std.CLBuiltinFuncAnnotation;
import okhttp3.*;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.InputStream;

public class Baidu {
    public static void main(String[] args) {
//        Baidu.__aip__()
    }

    @CLBuiltinFuncAnnotation(params = {"text", "token", "cuid"}, name = "aip")
    public static CLObject __aip__(CLRtFrame frame) throws CLRuntimeError {
        CLObject maybeText = frame.scope.getOwnVariable("text");
        CLObject maybeToken = frame.scope.getOwnVariable("token");
        CLObject maybeCuid = frame.scope.getOwnVariable("cuid");

        if (!(maybeText instanceof CLString text)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting text, got `" + maybeText.getTypeIdentifier() + "`");
        }
        if (!(maybeToken instanceof CLString token)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting text, got `" + maybeToken.getTypeIdentifier() + "`");
        }
        if (!(maybeCuid instanceof CLString cuid)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting text, got `" + maybeCuid.getTypeIdentifier() + "`");
        }

        URLCodec codec = new URLCodec();
        String encode;
        try {
            encode = codec.encode(text.value);
            encode = codec.encode(encode);
        } catch (EncoderException e) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "encoder error");
        }

        HttpUrl.Builder httpBuilder = HttpUrl.parse("https://tsn.baidu.com/text2audio").newBuilder();
        httpBuilder.addQueryParameter("tex", encode);
        httpBuilder.addQueryParameter("lan", "zh");
        httpBuilder.addQueryParameter("cuid", cuid.value);
        httpBuilder.addQueryParameter("ctp", "1");
        httpBuilder.addQueryParameter("aue", "3");
        httpBuilder.addQueryParameter("tok", token.value);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .post(RequestBody.create(null, ""))
                .build();
        try {
            Response response = client.newCall(request).execute();
            return new CLString(response.body().string());

//            InputStream stream = response.body().byteStream();
//            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(stream);
//            CLAudio audio = new CLAudio(audioInputStream);
//            return audio;
        } catch (IOException e) {
            throw new CLRuntimeError(CLRuntimeErrorType.Unexpected, "io exception when calling url");
        }
//        catch (UnsupportedAudioFileException e) {
//            throw new CLRuntimeError(CLRuntimeErrorType.UnsupportedAudioFile, "unsupported audio type");
//        }
    }
}
