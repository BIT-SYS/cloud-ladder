package io.cloudladder.cloudladderserver.knowledge;

import cloudladder.core.knowledge.RelationShipMeta;
import cloudladder.core.object.CLObjectAnnotation;
import cloudladder.std.CLBuiltinFuncAnnotation;
import cloudladder.std.CLStdLibAnnotation;
import io.cloudladder.cloudladderserver.config.PackageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class KnowledgeGraphBuilder {
    public String dir;

    public KnowledgeGraphBuilder(String dir) {
        this.dir = dir;
    }

    public ArrayList<RelationShipMeta> getAllRelationShipMeta() {
        ArrayList<Method> methods = this.getAllLibMethods();

        ArrayList<RelationShipMeta> result = new ArrayList<>();

        for (Method method: methods) {
            ArrayList<Class> args = new ArrayList<>();
            Class ret = method.getReturnType();

            RelationShipMeta meta = new RelationShipMeta();
            ArrayList<String> argsString = new ArrayList<>();
            ArrayList<String> retString = new ArrayList<>();
            meta.args = argsString;
            meta.rets = retString;

            for (Class c: args) {
                CLObjectAnnotation annotation = (CLObjectAnnotation) c.getDeclaredAnnotation(CLObjectAnnotation.class);
                argsString.add(annotation.typeIdentifier());
            }

            CLObjectAnnotation annotation = (CLObjectAnnotation) ret.getDeclaredAnnotation(CLObjectAnnotation.class);
            retString.add(annotation.typeIdentifier());

            result.add(meta);
        }
//        System.out.println(result);

        return result;
    }

    public ArrayList<Method> getAllLibMethods() {
        ArrayList<Class> libClasses = this.getAllLibClasses();

        ArrayList<Method> result = new ArrayList<>();
        for (Class c: libClasses) {
            for (Method method: c.getMethods()) {
                if (method.isAnnotationPresent(CLBuiltinFuncAnnotation.class)) {
                    result.add(method);
                }
            }
        }

        return result;
    }

    public ArrayList<Class> getAllLibClasses() {
        File dir = new File(this.dir);
        if (!dir.isDirectory()) {
            return new ArrayList<>();
        }

        ArrayList<String> classNames = new ArrayList<>();
        URL[] urls = new URL[dir.listFiles().length];
        int urlIter = 0;

        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                if (file.getAbsolutePath().endsWith(".jar")) {
                    try {
                        URL url = file.toURI().toURL();
                        urls[urlIter] = url;
                        urlIter++;

                        ZipInputStream inputStream = new ZipInputStream(new FileInputStream(file));
                        for (ZipEntry entry = inputStream.getNextEntry(); entry != null; entry = inputStream.getNextEntry()) {
                            if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                                String className = entry.getName().replace("/", ".");
                                classNames.add(className.substring(0, className.length() - ".class".length()));
                            }
                        }
                    } catch (MalformedURLException | FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        URLClassLoader classLoader = new URLClassLoader(
                urls, this.getClass().getClassLoader()
        );

        ArrayList<Class> result = new ArrayList<>();

        try {
            for (String className: classNames) {
                Class c = classLoader.loadClass(className);

                if (c.isAnnotationPresent(CLStdLibAnnotation.class)) {
                    result.add(c);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }
}
