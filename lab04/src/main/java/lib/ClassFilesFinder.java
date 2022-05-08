package lib;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ClassFilesFinder {
    public List<Path> classesUnder(Path dir) {
        try {
            return Files.walk(dir)
                    .filter(s -> s.toString().endsWith(".class"))
                    .collect(Collectors.toList());
        } catch (IOException ignored) {
            //ignored.printStackTrace();
        }
        return new LinkedList<>();
    }

    public static void main(String[] args){
        new ClassFilesFinder().classesUnder(Paths.get(""))
                .forEach(System.out::println);
    }
}
