package cloudladder.core.ir;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.object.*;
import cloudladder.core.runtime.CLRtFrame;
import lombok.AllArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
public class CLIRCall extends CLIR {
    private int paramCount;

    @Override
    public void execute(CLRtFrame frame) throws CLRuntimeError {
        CLObject function = frame.vm.stack.pop();
        ArrayList<CLObject> params = new ArrayList<>();

        for (int i = 0; i < this.paramCount; i++) {
            params.add(frame.vm.stack.pop());
        }

        CLRtFrame newFrame = null;

        if (!(function instanceof CLFunction)) {
            return;
        }

        // create new frame
        if (function instanceof CLUserFunction uf) {
            newFrame = new CLRtFrame(frame.vm, frame, uf.codeObject, "user function frame");
            newFrame.caughtVariables = uf.caughtObjects;
        } else {
            newFrame = new CLRtFrame(frame.vm, frame, null, "other function frame");
        }

        // collect params
        ArrayList<String> paramNames = ((CLFunction) function).paramsNames;
        if (((CLFunction) function).catchAllParam) {
            for (int i = 0; i < paramNames.size() - 1; i++) {
                newFrame.scope.addVariable(paramNames.get(i), params.get(i));
            }
            if (this.paramCount >= paramNames.size()) {
                CLArray arr = new CLArray();
                for (int i = paramNames.size() - 1; i < params.size(); i++) {
                    arr.addItem(params.get(i));
                }
                newFrame.scope.addVariable(paramNames.get(paramNames.size() - 1), arr);
            }
        } else {
            for (int i = 0; i < paramNames.size(); i++) {
                newFrame.scope.addVariable(paramNames.get(i), params.get(i));
            }
        }

        CLObject retValue = ((CLFunction) function).execute(newFrame);
        frame.vm.stack.push(retValue);

//        if (function instanceof CLBuiltinFunction) {
//            CLObject returnValue = ((CLBuiltinFunction) function).execute(newFrame);
//            frame.vm.stack.push(returnValue);
//        } else if (function instanceof CLUserFunction) {
//            newFrame.execute();
//        } else if (function instanceof CLHTTPFunction) {
//            CLObject returnValue = ((CLHTTPFunction) function).execute(newFrame);
//            frame.vm.stack.push(returnValue);
//        }
    }

    @Override
    public String toString() {
        return "call " + this.paramCount;
    }
}
