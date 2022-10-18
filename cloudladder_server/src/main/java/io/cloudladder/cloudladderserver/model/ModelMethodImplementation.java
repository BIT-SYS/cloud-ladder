package io.cloudladder.cloudladderserver.model;

import cloudladder.core.knowledge.implementation.HTTPMethodImplementation;
import cloudladder.core.knowledge.implementation.JarMethodImplementation;
import cloudladder.core.knowledge.implementation.MethodImplementation;
import com.google.gson.Gson;

public class ModelMethodImplementation {
    public Integer id;

    // for implementation
    public String type;
    public String data;

    // for localization
    public String scope;
    public String name;
    public String from;

    public MethodImplementation toMethodImplementation() {
        Gson gson = new Gson();
        if (this.type.equals("http")) {
            HTTPMethodImplementation impl = gson.fromJson(this.data, HTTPMethodImplementation.class);
            return impl;
        } else if (this.type.equals("jar")) {
            JarMethodImplementation impl = gson.fromJson(this.data, JarMethodImplementation.class);
            return impl;
        }
        return null;
    }
}
