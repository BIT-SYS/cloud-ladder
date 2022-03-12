package cloudladder;

import cloudladder.core.vm.CLVM;
import cloudladder.core.ir.CLIRCall;
import cloudladder.core.ir.CLIRDefString;
import cloudladder.core.ir.CLIRImport;
import cloudladder.core.misc.CLUtilIRList;
import cloudladder.core.runtime.env.CLRtFileStackFrame;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Path p = Paths.get("e://cloudLadder/cloud-ladder/");
        p = p.resolve("cloudladder");

        System.out.println("CLVM running at " + p.toAbsolutePath());

        CLVM vm = new CLVM(p);
//        vm.execute("example/audio_example.cl");
//        vm.execute("example/アニメ化.cl");
//        vm.execute("example/plant_recognition.cl");
//        vm.execute("example/discrete_prob_example.cl");
//        vm.execute("example/batch_plant_recognition.cl");
        vm.execute("tests/hard_test3.cl");
    }
}
