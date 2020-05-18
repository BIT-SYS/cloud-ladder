package interpreter.builtIn;

import interpreter.ExternalProcedureTemplate;
import interpreter.Interpreter;

import java.util.ArrayList;
import java.util.Scanner;

public class BuiltInInput extends ExternalProcedureTemplate {
    public BuiltInInput() {
        super("input", new ArrayList<>());
    }

    @Override
    public interpreter.Value external(Interpreter context) {
        Scanner scanner = new Scanner(System.in);
        return interpreter.Value.valueOf(scanner.nextLine());
    }
}