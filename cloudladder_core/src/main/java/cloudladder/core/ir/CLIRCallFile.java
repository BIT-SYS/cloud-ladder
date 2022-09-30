package cloudladder.core.ir;

import cloudladder.core.compiler.CLCompileContext;
import cloudladder.core.compiler.CLCompiler;
import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;
import cloudladder.core.object.*;
import cloudladder.core.runtime.CLRtFrame;
import cloudladder.std.Loader;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.Objects;

public class CLIRCallFile extends CLIR {
    private final static OkHttpClient client;

    static {
        client = new OkHttpClient();
    }

    private InputStream downloadRemoteJar(String remoteName) throws CLRuntimeError {
        Request request = new Request.Builder().url(remoteName).build();
        try {
            Response response = CLIRCallFile.client.newCall(request).execute();
            ResponseBody body = response.body();
            if (body == null) {
                throw new CLRuntimeError(CLRuntimeErrorType.NetworkError, "cannot find package " + remoteName);
            }

//            double length = Double.parseDouble(Objects.requireNonNull(response.header("content-length"), "1"));
            return body.byteStream();
        } catch (IOException ioException) {
            throw new CLRuntimeError(CLRuntimeErrorType.NetworkError, "cannot find package " + remoteName);
        }
    }

    @Override
    public void execute(CLRtFrame frame) throws CLRuntimeError {
        CLObject pathO = frame.vm.stack.pop();
        if (!(pathO instanceof CLString s)) {
            throw new CLRuntimeError(CLRuntimeErrorType.Unexpected, "import path has to be a string");
        }

        String path = s.value;
        boolean isBuiltin = path.startsWith(":");
        boolean isRemote = path.startsWith("@");

        if (isBuiltin) {
            if (frame.vm.moduleCache.containsKey(path)) {
                frame.vm.stack.push(frame.vm.moduleCache.get(path));
                return;
            }

            String libName = path.substring(1);
            CLDict exports = Loader.loadLib(libName);

            frame.vm.stack.push(exports);
            frame.vm.moduleCache.put(path, exports);
        } else if (isRemote) {
            String remotePath = path.substring(1);

            URL url = null;
            try {
                url = new URL(remotePath);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return;
            }

            URLClassLoader loader = new URLClassLoader(
                    new URL[] {url},
                    CLIR.class.getClassLoader()
            );
//            System.out.println(loader);

            try {
                Class c = Class.forName("org.example.DynamicTest", true, loader);
                CLDict dict = Loader.loadClass(c);
//                System.out.println(dict);

                frame.vm.stack.push(dict);
                frame.vm.moduleCache.put(remotePath, dict);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }


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
