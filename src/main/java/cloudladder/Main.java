package cloudladder;

import cloudladder.core.compiler.CLCompileContext;
import cloudladder.core.compiler.CLCompiler;
import cloudladder.core.object.CLCodeObject;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
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

        CLCompiler compiler = new CLCompiler();
        CLCompileContext context = new CLCompileContext();
        compiler.compileFile("let x = 10;\nlet y = x + 10 + z;", context);

        CLCodeObject codeObject = context.getCodeObject();
        System.out.println(codeObject.instructions);
    }
}
