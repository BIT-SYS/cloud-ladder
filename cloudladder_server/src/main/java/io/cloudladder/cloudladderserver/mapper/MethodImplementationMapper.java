package io.cloudladder.cloudladderserver.mapper;

import io.cloudladder.cloudladderserver.model.ModelMethodImplementation;

public interface MethodImplementationMapper {
    public ModelMethodImplementation getMethodImplementation(String scope, String name, String from);

    public ModelMethodImplementation getMethodImplementationWithoutScope(String name, String from);

    void createMethodImplementation(String scope, String name, String from, String type, String data);
}
