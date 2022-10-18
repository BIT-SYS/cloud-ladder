package cloudladder.std.global;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;
import cloudladder.core.object.CLAudio;
import cloudladder.core.object.CLNumber;
import cloudladder.core.object.CLObject;
import cloudladder.core.object.CLString;
import cloudladder.core.runtime.CLRtFrame;
import cloudladder.std.CLBuiltinFuncAnnotation;

import javax.sound.sampled.*;
import java.io.*;
import java.nio.file.Path;

public class CLStdAudio {
    @CLBuiltinFuncAnnotation(params = {"path"}, name = "new")
    public static CLAudio __new__(CLRtFrame frame) throws CLRuntimeError {
        CLObject path = frame.scope.getOwnVariable("path");
        if (!(path instanceof CLString)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting string, got `" + path.getTypeIdentifier() + "`");
        }

        String str = ((CLString) path).value;
        Path p = frame.vm.cwd.resolve(str);
        CLAudio audio;
        try {
            audio = new CLAudio(p);
        } catch (Exception e) {
            throw new CLRuntimeError(CLRuntimeErrorType.Unexpected, "create audio error");
        }

        return audio;
    }

    @CLBuiltinFuncAnnotation(value={"audio"}, name="binary")
    public static CLString __binary__(CLRtFrame frame) throws CLRuntimeError {
        CLObject maybeAudio = frame.scope.getOwnVariable("audio");
        if (!(maybeAudio instanceof CLAudio audio)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting audio, got `" + maybeAudio.getTypeIdentifier() + "`");
        }
        String s = audio.binary();

        return new CLString(s);
    }

    @CLBuiltinFuncAnnotation(value={"audio", "start", "end"}, name="cutAudio")
    public static CLAudio __cutAudio__(CLRtFrame frame) throws CLRuntimeError {
        CLObject p1 = frame.scope.getOwnVariable("start");
        CLObject p2 = frame.scope.getOwnVariable("end");

        if (!(p1 instanceof CLNumber number1)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting number, got `" + p1.getTypeIdentifier() + "`");
        }
        if (!(p2 instanceof CLNumber number2)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting number, got `" + p2.getTypeIdentifier() + "`");
        }

        if (!number1.isInteger() || !number2.isInteger()) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting integer, got float");
        }

        int start = (int) number1.value;
        int end = (int) number2.value;

        CLObject maybeAudio = frame.scope.getOwnVariable("audio");
        if (!(maybeAudio instanceof CLAudio audio)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting audio, got `" + maybeAudio.getTypeIdentifier() + "`");
        }

        try {
            return audio.cutAudio(start, end);
        } catch (UnsupportedAudioFileException e) {
            throw new CLRuntimeError(CLRuntimeErrorType.UnsupportedAudioFile, "unsupported audio file");
        } catch (IOException e) {
            throw new CLRuntimeError(CLRuntimeErrorType.IOError, "io exception");
        }
    }
//    @CLBuiltinFuncAnnotation(value={"audio1","audio2"},name="mergeAudio")
//    public static void __mergeAudio__(CLRtEnvironment env) throws IOException {
//        CLAudio audio1 = (CLAudio) env.getVariable("audio1").getReferee();
//        CLAudio audio2 = (CLAudio) env.getVariable("audio2").getReferee();
//        CLAudio ret = audio1.mergeAudio(audio1,audio2);
//        ret.setExts(env.getVariable("Audio"));
//        env.ret(ret.wrap());
//    }
//    @CLBuiltinFuncAnnotation(value={"audio1","audio2"},name="mixAudio")
//    public static void __mixAudio__(CLRtEnvironment env) throws UnsupportedAudioFileException, IOException{
//        CLAudio audio1 = (CLAudio) env.getVariable("audio1").getReferee();
//        CLAudio audio2 = (CLAudio) env.getVariable("audio2").getReferee();
//        CLAudio ret = audio1.mixAudio(audio1,audio2);
//        ret.setExts(env.getVariable("Audio"));
//        env.ret(ret.wrap());
//    }
//    @CLBuiltinFuncAnnotation(value={"self"}, name="play")
//    public static void __play__(CLRtEnvironment env) throws LineUnavailableException {
//        CLAudio self = (CLAudio) env.getVariable("self").getReferee();
//        self.play();
//        env.ret(self.wrap());
//    }
//    @CLBuiltinFuncAnnotation(value={"self"}, name="stop")
//    public static void __stop__(CLRtEnvironment env)
//    {
//        CLAudio self = (CLAudio) env.getVariable("self").getReferee();
//        self.stop();
//        env.ret(self.wrap());
//    }
//    @CLBuiltinFuncAnnotation(value={"self"}, name="continues")
//    public static void __continues__(CLRtEnvironment env)
//    {
//        CLAudio self = (CLAudio) env.getVariable("self").getReferee();
//        self.continues();
//        env.ret(self.wrap());
//    }
//    @CLBuiltinFuncAnnotation(value={"self","lang"}, name="toSpeechRecognition")
//    public static void __toSpeechRecognition__(CLRtEnvironment env) throws IOException {
//        CLAudio audio = (CLAudio) env.getVariable("self").getReferee();
//        CLString language = (CLString)env.getVariable("lang").getReferee();
//        File file = null;
//        if (audio.isFromFile()) {
//            Path path = audio.getPath();
//            file = new File(path.toString());
//        }
//        else {
//            file = File.createTempFile(UUID.randomUUID().toString(), ".wav");
//            audio.save(Path.of(file.getPath()));
//        }
////        Response response = audio.postToUrl("http://localhost:5000/speech_recognition", file, language.toString());
//        Response response = audio.postToUrl("http://39.103.135.92:5000/speech_recognition", file, language.toString());
////        System.out.println(response.toString());
//        CLString str = new CLString(response.body().string());
////        System.out.println(str);
//        env.ret(str.wrap());
//    }
//    @CLBuiltinFuncAnnotation(value={"self","type"}, name="toVoiceConversion")
//    public static void __toVoiceConversion__(CLRtEnvironment env) throws IOException, UnsupportedAudioFileException {
//        CLAudio audio = (CLAudio) env.getVariable("self").getReferee();
//        CLString type = (CLString)env.getVariable("type").getReferee();
//        File file = null;
//        if (audio.isFromFile()) {
//            Path path = audio.getPath();
//            file = new File(path.toString());
//        }
//        else {
//            file = File.createTempFile(UUID.randomUUID().toString(), ".wav");
//            audio.save(Path.of(file.getPath()));
//        }
//        Response response = audio.postToUrl("http://39.103.135.92:5000/voice_conversion", file, type.toString());
//        byte[] allBytes = response.body().byteStream().readAllBytes();
//        ByteArrayInputStream bis = new ByteArrayInputStream(allBytes);
//        AudioFormat format = new AudioFormat(16000, 16, 1, true, false);
//        AudioInputStream ais = new AudioInputStream(bis,format, allBytes.length/ format.getFrameSize());
//        CLAudio ret = new CLAudio(ais);
//        env.ret(ret.wrap());
//    }
//    @CLBuiltinFuncAnnotation(value={"self","path"}, name="wavToMp3")
//    public static void __wavToMp3__(CLRtEnvironment env) throws IOException, EncoderException {
//        CLAudio audio = (CLAudio) env.getVariable("self").getReferee();
//        CLString path = (CLString)env.getVariable("path").getReferee();
//        audio.wavToMp3(path.toString());
//        env.ret(audio.wrap());
//    }
//    public static void run(CLRtEnvironment env) throws Exception {
//        Class clazz = CLStdAudio.class;
//        CLObject obj = new CLObject();
//        CLRtScope scope = env.getCurrentScope();
//
//        for (Method method : clazz.getMethods()) {
//            if (method.getName().startsWith("__")) {
//                CLBuiltinFuncAnnotation annotation = method.getAnnotation(CLBuiltinFuncAnnotation.class);
//                String[] params = annotation.value();
//                String name = annotation.name();
//
//                CLBuiltinFunction func = new CLBuiltinFunction(method, scope, params);
//                obj.addStringRefer(name, func.wrap());
//            }
//        }
//
//        env.addVariable("Audio", obj.wrap());
//    }
}

