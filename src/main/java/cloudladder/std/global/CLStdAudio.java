package cloudladder.std.global;

import cloudladder.core.runtime.data.*;
import cloudladder.core.runtime.env.CLRtEnvironment;
import cloudladder.core.runtime.env.CLRtScope;
import cloudladder.std.CLBuiltinFuncAnnotation;
import it.sauronsoftware.jave.EncoderException;
import okhttp3.Response;
import okio.BufferedSource;

import javax.sound.sampled.*;
import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.UUID;

public class CLStdAudio {
    @CLBuiltinFuncAnnotation(value = {"path"}, name = "new")
    public static void __new__(CLRtEnvironment env) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        CLData path = env.getVariable("path").getReferee();
        if (!(path instanceof CLString)) {
            // todo err
        }

        String str = ((CLString) path).getValue();
        Path p = env.getWd().resolve(str);
        CLAudio audio = new CLAudio(p);
        audio.setExts(env.getVariable("Audio"));
        env.ret(audio.wrap());
    }
    @CLBuiltinFuncAnnotation(value={"self"}, name="binary")
    public static void __binary__(CLRtEnvironment env) {
        CLAudio self = (CLAudio) env.getVariable("self").getReferee();
        String s = self.binary();

        CLString str = new CLString(s);
        str.setExts(env.getVariable("String"));
        env.ret(str.wrap());
    }
    @CLBuiltinFuncAnnotation(value={"self","start","end"},name="cutAudio")
    public static void __cutAudio__(CLRtEnvironment env) throws UnsupportedAudioFileException, IOException{
        CLData p1 = env.getVariable("start").getReferee();
        CLData p2 = env.getVariable("end").getReferee();
        int start = Integer.parseInt(((CLString)p1).getValue());
        int end = Integer.parseInt(((CLString)p2).getValue());
        CLAudio self = (CLAudio) env.getVariable("self").getReferee();
        CLAudio ret = self.cutAudio(start,end);
        ret.setExts(env.getVariable("Audio"));
        env.ret(ret.wrap());
    }
    @CLBuiltinFuncAnnotation(value={"audio1","audio2"},name="mergeAudio")
    public static void __mergeAudio__(CLRtEnvironment env) throws IOException {
        CLAudio audio1 = (CLAudio) env.getVariable("audio1").getReferee();
        CLAudio audio2 = (CLAudio) env.getVariable("audio2").getReferee();
        CLAudio ret = audio1.mergeAudio(audio1,audio2);
        ret.setExts(env.getVariable("Audio"));
        env.ret(ret.wrap());
    }
    @CLBuiltinFuncAnnotation(value={"audio1","audio2"},name="mixAudio")
    public static void __mixAudio__(CLRtEnvironment env) throws UnsupportedAudioFileException, IOException{
        CLAudio audio1 = (CLAudio) env.getVariable("audio1").getReferee();
        CLAudio audio2 = (CLAudio) env.getVariable("audio2").getReferee();
        CLAudio ret = audio1.mixAudio(audio1,audio2);
        ret.setExts(env.getVariable("Audio"));
        env.ret(ret.wrap());
    }
    @CLBuiltinFuncAnnotation(value={"self"}, name="play")
    public static void __play__(CLRtEnvironment env) throws LineUnavailableException {
        CLAudio self = (CLAudio) env.getVariable("self").getReferee();
        self.play();
        env.ret(self.wrap());
    }
    @CLBuiltinFuncAnnotation(value={"self"}, name="stop")
    public static void __stop__(CLRtEnvironment env)
    {
        CLAudio self = (CLAudio) env.getVariable("self").getReferee();
        self.stop();
        env.ret(self.wrap());
    }
    @CLBuiltinFuncAnnotation(value={"self"}, name="continues")
    public static void __continues__(CLRtEnvironment env)
    {
        CLAudio self = (CLAudio) env.getVariable("self").getReferee();
        self.continues();
        env.ret(self.wrap());
    }
    @CLBuiltinFuncAnnotation(value={"self","lang"}, name="toSpeechRecognition")
    public static void __toSpeechRecognition__(CLRtEnvironment env) throws IOException {
        CLAudio audio = (CLAudio) env.getVariable("self").getReferee();
        CLString language = (CLString)env.getVariable("lang").getReferee();
        File file = null;
        if (audio.isFromFile()) {
            Path path = audio.getPath();
            file = new File(path.toString());
        }
        else {
            file = File.createTempFile(UUID.randomUUID().toString(), ".wav");
            audio.save(Path.of(file.getPath()));
        }
        Response response = audio.postToUrl("http://39.103.135.92:5000/speech_recognition", file, language.toString());
        CLString str = new CLString(response.body().toString());
        env.ret(str.wrap());
    }
    @CLBuiltinFuncAnnotation(value={"self","type"}, name="toVoiceConversion")
    public static void __toVoiceConversion__(CLRtEnvironment env) throws IOException, UnsupportedAudioFileException {
        CLAudio audio = (CLAudio) env.getVariable("self").getReferee();
        CLString type = (CLString)env.getVariable("type").getReferee();
        File file = null;
        if (audio.isFromFile()) {
            Path path = audio.getPath();
            file = new File(path.toString());
        }
        else {
            file = File.createTempFile(UUID.randomUUID().toString(), ".wav");
            audio.save(Path.of(file.getPath()));
        }
        Response response = audio.postToUrl("http://39.103.135.92:5000/voice_conversion", file, type.toString());
        byte[] allBytes = response.body().byteStream().readAllBytes();
        ByteArrayInputStream bis = new ByteArrayInputStream(allBytes);
        AudioFormat format = new AudioFormat(16000, 16, 1, true, false);
        AudioInputStream ais = new AudioInputStream(bis,format, allBytes.length/ format.getFrameSize());
        CLAudio ret = new CLAudio(ais);
        env.ret(ret.wrap());
    }
    @CLBuiltinFuncAnnotation(value={"self","path"}, name="wavToMp3")
    public static void __wavToMp3__(CLRtEnvironment env) throws IOException, EncoderException {
        CLAudio audio = (CLAudio) env.getVariable("self").getReferee();
        CLString path = (CLString)env.getVariable("path").getReferee();
        audio.wavToMp3(path.toString());
        env.ret(audio.wrap());
    }
    public static void run(CLRtEnvironment env) throws Exception {
        Class clazz = CLStdAudio.class;
        CLObject obj = new CLObject();
        CLRtScope scope = env.getCurrentScope();

        for (Method method : clazz.getMethods()) {
            if (method.getName().startsWith("__")) {
                CLBuiltinFuncAnnotation annotation = method.getAnnotation(CLBuiltinFuncAnnotation.class);
                String[] params = annotation.value();
                String name = annotation.name();

                CLBuiltinFunction func = new CLBuiltinFunction(method, scope, params);
                obj.addStringRefer(name, func.wrap());
            }
        }

        env.addVariable("Audio", obj.wrap());
    }
}

