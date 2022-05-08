package lib.loader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MyClassLoader extends ClassLoader{
    private Path classRoot = Paths.get("");

    public MyClassLoader(ClassLoader parent){
        super(parent);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return getClass(name);
    }

    private Path nameToRelativePath(String name){
        return Paths.get(name.replace('.', File.separatorChar) + ".class");
    }

    private Class<?> getClass(String name) throws ClassNotFoundException {
        byte[] b = null;
        try {
            // This loads the byte code data from the file
            b = loadClassFileData(nameToRelativePath(name));
            // defineClass is inherited from the ClassLoader class
            // that converts byte array into a Class. defineClass is Final
            // so we cannot override it
            Class<?> c = defineClass(name, b, 0, b.length);
            resolveClass(c);
            return c;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] loadClassFileData(Path relativePath) throws IOException {
        return Files.readAllBytes(this.classRoot.resolve(relativePath));
    }

    public Path getClassRoot() {
        return classRoot;
    }

    public void setClassRoot(Path classRoot) {
        this.classRoot = classRoot;
    }
}
