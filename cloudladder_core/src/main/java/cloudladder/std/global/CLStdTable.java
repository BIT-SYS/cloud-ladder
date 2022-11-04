package cloudladder.std.global;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;
import cloudladder.core.object.*;
import cloudladder.core.runtime.CLRtFrame;
import cloudladder.std.CLBuiltinFuncAnnotation;
import cloudladder.std.CLStdLibAnnotation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

@CLStdLibAnnotation(name = "Table")
public class CLStdTable {
    @CLBuiltinFuncAnnotation(params = {"filename"}, name = "load_csv")
    public static CLTable __load_csv__(CLRtFrame frame) throws CLRuntimeError {
        CLObject maybeFilename = frame.scope.getOwnVariable("filename");
        if (!(maybeFilename instanceof CLString filename)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting string, found `" + maybeFilename.getTypeIdentifier() + "`");
        }

        Path path = frame.vm.cwd.resolve(filename.value);
        try {
            FileInputStream fileInputStream = new FileInputStream(path.toFile());
            String str = new String(fileInputStream.readAllBytes());
            CLTable table = new CLTable();
            table.loadCsv(str);
            return table;
        } catch (FileNotFoundException e) {
            throw new CLRuntimeError(CLRuntimeErrorType.FileNotFound, "file `" + path.toString() + "` not found");
        } catch (IOException e) {
            throw new CLRuntimeError(CLRuntimeErrorType.Unexpected, "io exception while reading file");
        }
    }

    @CLBuiltinFuncAnnotation(params = {"csv_str"}, name = "load_csv_string")
    public static CLTable __load_csv_string__(CLRtFrame frame) throws CLRuntimeError {
        CLObject maybeString = frame.scope.getOwnVariable("csv_str");
        if (!(maybeString instanceof CLString s)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting string, found `" + maybeString.getTypeIdentifier() + "`");
        }

        CLTable table = new CLTable();
        table.loadCsv(s.value);
        return table;
    }

    @CLBuiltinFuncAnnotation(params = {"table", "index"}, name = "row")
    public static CLArray __row__(CLRtFrame frame) throws CLRuntimeError {
        CLObject maybeTable = frame.scope.getOwnVariable("table");
        if (!(maybeTable instanceof CLTable table)) {
            throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting table, found `" + maybeTable.getTypeIdentifier() + "`");
        }

        CLNumber number = frame.scope.requireOwnVariableWithType("index", CLNumber.class);

        return table.getRow(number.getAsInt());
    }
}
