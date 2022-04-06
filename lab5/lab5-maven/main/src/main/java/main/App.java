package main;

import lib.AnalysisServiceLoader;

import java.nio.file.Path;

public class App {
    public static void main(String[] args){
        System.out.println(Path.of("").toAbsolutePath());
        System.out.println(AnalysisServiceLoader.loadAvailableServices());
    }
}
