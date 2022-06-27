package cloudladder.std.global;

import cloudladder.core.ir.CLIRDefArray;
import cloudladder.core.runtime.data.*;
import cloudladder.core.runtime.env.CLRtEnvironment;
import cloudladder.core.runtime.env.CLRtScope;
import cloudladder.std.CLBuiltinFuncAnnotation;
import cloudladder.utils.CLDataUtils;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.ArrayList;

public class CLStdGlobalFunction {
    @CLBuiltinFuncAnnotation(value={"op1", "op2"}, name="[[eql]]")
    public static void __eql__(CLRtEnvironment env) {
        CLData op1 = env.getVariable("op1").getReferee();
        CLData op2 = env.getVariable("op2").getReferee();

        CLBoolean temp;
        boolean ret = false;

        if (op1 instanceof CLBoolean && op2 instanceof CLBoolean) {
            boolean v1 = ((CLBoolean) op1).getValue();
            boolean v2 = ((CLBoolean) op2).getValue();
            ret = v1 == v2;
        } else if (op1 instanceof CLNumber && op2 instanceof CLNumber) {
            double v1 = ((CLNumber) op1).getValue();
            double v2 = ((CLNumber) op2).getValue();
            ret = v1 == v2;
        } else if (op1 instanceof CLString && op2 instanceof CLString) {
            String v1 = ((CLString) op1).getValue();
            String v2 = ((CLString) op2).getValue();
            ret = v1.equals(v2);
        } else {
            // todo
        }

        if (ret) {
            temp = (CLBoolean) env.getVariable("$true").getReferee();
        } else {
            temp = (CLBoolean) env.getVariable("$false").getReferee();
        }
        env.ret(temp.wrap());
    }

    @CLBuiltinFuncAnnotation(value={"op1", "op2"}, name="[[gt]]")
    public static void __gt__(CLRtEnvironment env) {
        CLData op1 = env.getVariable("op1").getReferee();
        CLData op2 = env.getVariable("op2").getReferee();

        CLBoolean temp;
        boolean ret = false;

        if (op1 instanceof CLNumber && op2 instanceof CLNumber) {
            double v1 = ((CLNumber) op1).getValue();
            double v2 = ((CLNumber) op2).getValue();
            ret = v1 > v2;
        } else {
            // todo
        }

        env.ret(ret);
    }

    @CLBuiltinFuncAnnotation(value={"op1", "op2"}, name="[[ge]]")
    public static void __ge__(CLRtEnvironment env) {
        CLData op1 = env.getVariable("op1").getReferee();
        CLData op2 = env.getVariable("op2").getReferee();

        CLBoolean temp;
        boolean ret = false;

        if (op1 instanceof CLNumber && op2 instanceof CLNumber) {
            double v1 = ((CLNumber) op1).getValue();
            double v2 = ((CLNumber) op2).getValue();
            ret = v1 >= v2;
        } else {
            // todo
        }

        env.ret(ret);
    }

    @CLBuiltinFuncAnnotation(value={"op1", "op2"}, name="[[lt]]")
    public static void __lt__(CLRtEnvironment env) {
        CLData op1 = env.getVariable("op1").getReferee();
        CLData op2 = env.getVariable("op2").getReferee();

        CLBoolean temp;
        boolean ret = false;

        if (op1 instanceof CLNumber && op2 instanceof CLNumber) {
            double v1 = ((CLNumber) op1).getValue();
            double v2 = ((CLNumber) op2).getValue();
            ret = v1 < v2;
        } else {
            // todo
        }

        env.ret(ret);
    }

    @CLBuiltinFuncAnnotation(value={"op1", "op2"}, name="[[le]]")
    public static void __le__(CLRtEnvironment env) {
        CLData op1 = env.getVariable("op1").getReferee();
        CLData op2 = env.getVariable("op2").getReferee();

        CLBoolean temp;
        boolean ret = false;

        if (op1 instanceof CLNumber && op2 instanceof CLNumber) {
            double v1 = ((CLNumber) op1).getValue();
            double v2 = ((CLNumber) op2).getValue();
            ret = v1 <= v2;
        } else {
            // todo
        }

        env.ret(ret);
    }

    @CLBuiltinFuncAnnotation(value={"op1", "op2"}, name="[[or]]")
    public static void __or__(CLRtEnvironment env) {
        CLData op1 = env.getVariable("op1").getReferee();
        CLData op2 = env.getVariable("op2").getReferee();

        if (op1 instanceof CLBoolean && op2 instanceof CLBoolean) {
            boolean v1 = ((CLBoolean) op1).getValue();
            boolean v2 = ((CLBoolean) op2).getValue();
            CLBoolean t;
            if (v1 || v2) {
                t = (CLBoolean) env.getVariable("$true").getReferee();
            } else {
                t = (CLBoolean) env.getVariable("$false").getReferee();
            }
            env.ret(t.wrap());
        }
    }

    @CLBuiltinFuncAnnotation(value={"op1", "op2"}, name="[[and]]")
    public static void __and__(CLRtEnvironment env) {
        CLData op1 = env.getVariable("op1").getReferee();
        CLData op2 = env.getVariable("op2").getReferee();

        if (op1 instanceof CLBoolean && op2 instanceof CLBoolean) {
            boolean v1 = ((CLBoolean) op1).getValue();
            boolean v2 = ((CLBoolean) op2).getValue();
            CLBoolean t;
            if (v1 && v2) {
                t = (CLBoolean) env.getVariable("$true").getReferee();
            } else {
                t = (CLBoolean) env.getVariable("$false").getReferee();
            }
            env.ret(t.wrap());
        }
    }

    @CLBuiltinFuncAnnotation(value={"op"}, name="[[not]]")
    public static void __not__(CLRtEnvironment env) {
        CLData op1 = env.getVariable("op").getReferee();

        if (op1 instanceof CLBoolean) {
            boolean value = ((CLBoolean) op1).getValue();
            env.ret(!value);
        } else {
            env.ret();
        }
    }

    @CLBuiltinFuncAnnotation(value={"op"}, name="[[neg]]")
    public static void __neg__(CLRtEnvironment env) {
        CLData op1 = env.getVariable("op").getReferee();

        if (op1 instanceof CLNumber) {
            double value = ((CLNumber) op1).getValue();
            CLNumber num = env.newNumber(-value);
            env.ret(num.wrap());
        } else {
            env.ret();
        }
    }

    @CLBuiltinFuncAnnotation(value={"op1", "op2"}, name="[[add]]")
    public static void __add__(CLRtEnvironment env) {
        CLData op1 = env.getVariable("op1").getReferee();
        CLData op2 = env.getVariable("op2").getReferee();

        if (op1 instanceof CLNumber && op2 instanceof CLNumber) {
            double value = ((CLNumber) op1).getValue() + ((CLNumber) op2).getValue();
            CLNumber ret = new CLNumber(value);
            ret.setExts(env.getVariable("Number"));
            env.ret(new CLReference(ret));
        } else if (op1 instanceof CLString && op2 instanceof CLString) {
            String value = ((CLString) op1).getValue() + ((CLString) op2).getValue();
            CLString ret = new CLString(value);
            ret.setExts(env.getVariable("String"));
            env.ret(new CLReference(ret));
        } else {
            env.ret();
        }
    }

    @CLBuiltinFuncAnnotation(value={"op1", "op2"}, name="[[sub]]")
    public static void __sub__(CLRtEnvironment env) {
        CLData op1 = env.getVariable("op1").getReferee();
        CLData op2 = env.getVariable("op2").getReferee();

        if (op1 instanceof CLNumber && op2 instanceof CLNumber) {
            double value = ((CLNumber) op1).getValue() - ((CLNumber) op2).getValue();
            CLNumber ret = new CLNumber(value);
            ret.setExts(env.getVariable("Number"));
            env.ret(new CLReference(ret));
        }else {
            env.ret();
        }
    }

    @CLBuiltinFuncAnnotation(value={"op1", "op2"}, name="[[mul]]")
    public static void __mul__(CLRtEnvironment env) {
        CLData op1 = env.getVariable("op1").getReferee();
        CLData op2 = env.getVariable("op2").getReferee();

        if (op1 instanceof CLNumber && op2 instanceof CLNumber) {
            double value = ((CLNumber) op1).getValue() * ((CLNumber) op2).getValue();
            CLNumber ret = new CLNumber(value);
            ret.setExts(env.getVariable("Number"));
            env.ret(new CLReference(ret));
        } else {
            env.ret();
        }
    }

    @CLBuiltinFuncAnnotation(value={"op1", "op2"}, name="[[div]]")
    public static void __div__(CLRtEnvironment env) {
        CLData op1 = env.getVariable("op1").getReferee();
        CLData op2 = env.getVariable("op2").getReferee();

        if (op1 instanceof CLNumber && op2 instanceof CLNumber) {
            if (((CLNumber) op2).getValue() == 0) {
                // todo
                env.ret();
                return;
            }
            double value = ((CLNumber) op1).getValue() / ((CLNumber) op2).getValue();
            CLNumber ret = new CLNumber(value);
            ret.setExts(env.getVariable("Number"));
            env.ret(new CLReference(ret));
        } else {
            env.ret();
        }
    }

    @CLBuiltinFuncAnnotation(value={"obj", "key"}, name="[[access]]")
    public static void __access__(CLRtEnvironment env) {
        CLData self = env.getVariable("obj").getReferee();
        CLData key = env.getVariable("key").getReferee();

        if (key instanceof CLString) {
            CLReference field = self.getReferee(((CLString) key).getValue());
            env.ret(field);
        } else if (key instanceof CLNumber) {
            int index = ((CLNumber) key).getIntegralValue();
            CLReference field = self.getReferee(index);
            env.ret(field);
        }
    }

    @CLBuiltinFuncAnnotation(value={"data"}, name="print")
    public static void __print__(CLRtEnvironment env) {
        CLData data = env.getVariable("data").getReferee();

//        String str = toString(data);
        String str = data.toString();
        System.out.println("CL: " + str);

        env.ret(data.wrap());
    }

    @CLBuiltinFuncAnnotation(value={"data"}, name="typeof")
    public static void __typeof__(CLRtEnvironment env) {
        CLData data = env.getVariable("data").getReferee();

        String ret = "unrecognized";
        if (data instanceof CLString) {
            ret = "string";
        } else if (data instanceof CLArray) {
            ret = "array";
        } else if (data instanceof CLBoolean) {
            ret = "boolean";
        } else if (data instanceof CLNumber) {
            ret = "number";
        } else if (data instanceof CLObject) {
            ret = "object";
        } else if (data instanceof CLFunction) {
            ret = "function";
        } else if (data instanceof CLImage) {
            ret = "image";
        }else if (data instanceof CLAudio){
            ret = "audio";
        } else if (data instanceof CLDiscreteProbability) {
            ret = "prob";
        }

        CLString str = env.newString(ret);
        env.ret(str.wrap());
    }

    @CLBuiltinFuncAnnotation(value={"prob", "index"}, name="top")
    public static void __top__(CLRtEnvironment env) {
        CLData prob = env.getVariable("prob").getReferee();

        if (!(prob instanceof CLDiscreteProbability)) {
            env.ret(prob.wrap());
            return;
        }

        if (!env.hasOwnVariable("index")) {
            CLData max = ((CLDiscreteProbability) prob).getMax();
            env.ret(max.wrap());
        } else {
            CLReference index = env.getVariable("index");
            int n = ((CLNumber) index.getReferee()).getIntegralValue();
            ArrayList<CLData> arr = ((CLDiscreteProbability) prob).topN(n);

            CLArray ret = env.newArray();
            for (CLData data : arr) {
                ret.addNumberRefer(data.wrap());
            }

            env.ret(ret.wrap());
        }
    }

    @CLBuiltinFuncAnnotation(value={"dir"}, name="load_dir")
    public static void __loadDir__(CLRtEnvironment env) {
        CLString dir = (CLString) env.getVariable("dir").getReferee();

        Path wd = env.getWd();
        Path path = wd.resolve(dir.getValue());
        File folder = new File(path.toString());
        CLArray arr = env.newArray();
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                continue;
            }

            String relativeFilename = wd.relativize(file.toPath()).toString();
            CLString name = env.newString(relativeFilename);
            arr.addNumberRefer(name.wrap());
        }

        env.ret(arr.wrap());
    }

    @CLBuiltinFuncAnnotation(value={"p1"}, name="image")
    public static void __image__(CLRtEnvironment env) {
        CLData p1 = env.getOwnVariable("p1").getReferee();

        if (p1 instanceof CLString) {
            Path p = env.getWd().resolve(((CLString) p1).getValue());
            CLImage image = new CLImage(p);
            env.ret(image.wrap());
        } else if (p1 instanceof CLArray) {
            CLArray arr = env.newArray();

            for (CLReference ref : p1.getNumberRefers()) {
                String value = ((CLString) ref.getReferee()).getValue();
                Path path = env.getWd().resolve(value);

                CLImage image = new CLImage(path);
                arr.addNumberRefer(image.wrap());
            }

            env.ret(arr.wrap());
        }
    }
    
    @CLBuiltinFuncAnnotation(value={"p1"}, name="audio")
    public static void __audio__(CLRtEnvironment env) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        CLData p1 = env.getOwnVariable("p1").getReferee();

        if (p1 instanceof CLString) {
            Path p = env.getWd().resolve(((CLString) p1).getValue());
            CLAudio audio = new CLAudio(p);
            env.ret(audio.wrap());
        } else if (p1 instanceof CLArray) {
            CLArray arr = env.newArray();

            for (CLReference ref : p1.getNumberRefers()) {
                String value = ((CLString) ref.getReferee()).getValue();
                Path path = env.getWd().resolve(value);

                CLAudio audio = new CLAudio(path);
                arr.addNumberRefer(audio.wrap());
            }

            env.ret(arr.wrap());
        }
    }
    
    @CLBuiltinFuncAnnotation(value={"obj", "filename"}, name="save")
    public static void __save__(CLRtEnvironment env) {
        CLData data = env.getVariable("obj").getReferee();
        CLString filename = (CLString) env.getVariable("filename").getReferee();

        Path path = env.getWd().resolve(filename.getValue());

        if (data instanceof CLImage) {
            CLImage image = (CLImage) data;
            image.save(path);
        } else if (data instanceof CLStream) {
            ((CLStream) data).save(path);
        } else if (data instanceof CLAudio)
        {
            CLAudio audio = (CLAudio) data;
            audio.save(path);
        }
    }

//    @CLBuiltinFuncAnnotation(value={"arr", "filter"}, name="filter")
//    public static void __filter__(CLRtEnvironment env) {
//        CLData data = env.getVariable("arr").getReferee();
//        if (!(data instanceof CLArray)) {
//            env.error("first param of 'filter' must be an array");
//            return;
//        }
//        CLArray arr = (CLArray) data;
//
//        CLData filter = env.getVariable("filter").getReferee();
//        if (filter instanceof CLString) {
//            CLArray newArray = env.newArray();
//
//            for (CLReference ref : arr.getNumberRefers()) {
//                CLString str = (CLString) ref.getReferee();
//                if (str.getValue().endsWith(""))
//            }
//        }
//    }

    public static void run(CLRtEnvironment env) throws Exception {
        Class clazz = CLStdGlobalFunction.class;
        CLRtScope scope = env.getCurrentScope();

        // add true and false
        CLBoolean clTrue = new CLBoolean(true);
        env.addVariable("$true", clTrue.wrap());
        CLBoolean clFalse = new CLBoolean(false);
        env.addVariable("$false", clFalse.wrap());

        for (Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(CLBuiltinFuncAnnotation.class)) {
                CLBuiltinFuncAnnotation annotation = method.getAnnotation(CLBuiltinFuncAnnotation.class);
                String[] params = annotation.value();
                String name = annotation.name();

                CLBuiltinFunction func = new CLBuiltinFunction(method, scope, params);
                env.addVariable(name, func.wrap());
            }
        }
    }
}
