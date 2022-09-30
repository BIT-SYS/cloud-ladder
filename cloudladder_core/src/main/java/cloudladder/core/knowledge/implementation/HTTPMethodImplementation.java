package cloudladder.core.knowledge.implementation;

import cloudladder.core.object.CLFunction;
import cloudladder.core.object.CLHTTPFunction;

import java.util.ArrayList;

public class HTTPMethodImplementation extends MethodImplementation {
    public String url;
    public String method;
    public ArrayList<String> argConverter;
    public String retConverter = null;
    public String bodyComposer = "json";

    public HTTPMethodImplementation(String alias, boolean catchAll) {
        super(alias, catchAll);

        this.argConverter = new ArrayList<>();
    }

    @Override
    public CLFunction buildFunction() {
        CLHTTPFunction function = new CLHTTPFunction(
                this.paramNames,
                this.catchAll,
                this.url,
                this.method,
//                this.paramNames,
//                this.outputType,
                this.argConverter,
                this.retConverter,
                this.bodyComposer
        );

        return function;
    }
}
