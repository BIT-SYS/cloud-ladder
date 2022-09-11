package cloudladder.core.ir;

import cloudladder.core.compiler.CLCompileContext;
import cloudladder.core.compiler.CLCompiler;
import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;
import cloudladder.core.object.*;
import cloudladder.core.runtime.CLRtFrame;
import cloudladder.std.Loader;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

public class CLIRCallFile extends CLIR {
    @Override
    public void execute(CLRtFrame frame) throws CLRuntimeError {
        CLObject pathO = frame.vm.stack.pop();
        if (!(pathO instanceof CLString s)) {
            throw new CLRuntimeError(CLRuntimeErrorType.Unexpected, "import path has to be a string");
        }

        String path = s.value;
        boolean isBuiltin = path.startsWith(":");

        if (isBuiltin) {
            if (frame.vm.moduleCache.containsKey(path)) {
                frame.vm.stack.push(frame.vm.moduleCache.get(path));
                return;
            }

            String libName = path.substring(1);
            CLDict exports = Loader.loadLib(libName);

            frame.vm.stack.push(exports);
            frame.vm.moduleCache.put(path, exports);
        } else {
            Path absolutePath = frame.vm.cwd.resolve(path);
            if (frame.vm.moduleCache.containsKey(absolutePath.toString())) {
                frame.vm.stack.push(frame.vm.moduleCache.get(absolutePath.toString()));
                return;
            }

            CLRtFrame newFrame;

            // compile file
            try {
                FileInputStream fileInputStream = new FileInputStream(absolutePath.toFile());
                String content = new String(fileInputStream.readAllBytes());

                CLCompileContext compileContext = new CLCompileContext();
                CLCompiler compiler = new CLCompiler();
                compiler.compileFile(content, compileContext);
                CLCodeObject codeObject = compileContext.getCodeObject();

                newFrame = new CLRtFrame(frame.vm, frame, codeObject, "user file");

            } catch (IOException e) {
                throw new CLRuntimeError(CLRuntimeErrorType.FileNotFound, "file `" + absolutePath + "` not found");
            }

            newFrame.execute();
            assert(frame.vm.stack.size() == newFrame.previousStackLength);

            CLDict exports = newFrame.exportObjects;
            frame.vm.stack.push(exports);
            frame.vm.moduleCache.put(absolutePath.toString(), exports);
        }
    }
}
