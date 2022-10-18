package cloudladder;

import cloudladder.core.compiler.CLCompileContext;
import cloudladder.core.compiler.CLCompiler;
import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.object.CLCodeObject;
import cloudladder.core.runtime.CLRtFrame;
import cloudladder.core.vm.CLVM;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException, CLRuntimeError {
////        Path p = Paths.get("C:/Users/wuzirui/Desktop/cloud-ladder");
//        Path p = Paths.get("E:/cloudladder/cloud-ladder");
//        p = p.resolve("cloudladder");
//
//        System.out.println("CLVM running at " + p.toAbsolutePath());
//
//        CLVM vm = new CLVM(p);
////        vm.execute("example/audio_example.cl");
//        vm.execute("example/SpeechRecognition.cl");
////        vm.execute("example/アニメ化.cl");
////        vm.execute("example/plant_recognition.cl");
////        vm.execute("example/discrete_prob_example.cl");
////        vm.execute("example/batch_plant_recognition.cl");
////        vm.execute("example/im2text.cl");
////        vm.execute("example/dfs.cl");

//        InputStream is = Main.class.getClassLoader().getResourceAsStream("test.cl");
//        String source = new String(is.readAllBytes());

//        CLCompiler compiler = new CLCompiler();
//        CLCompileContext context = new CLCompileContext();
//        compiler.compileFile(source, context);

//        CLCodeObject codeObject = context.getCodeObject();
//        System.out.println(codeObject.beautify());

        CLVM vm = new CLVM(Paths.get("E:/cloudladder/cloud-ladder/cloudladder_core/cloudladder"));
        vm.runFile("test2.cl");
//        CLRtFrame frame = vm.runCodeObject(codeObject);
//        System.out.println(frame.scope.getNameMapping());
    }
}
