package lib.processors;

import java.io.*;

public class Sample {
    static String readInput() {
        StringBuffer buffer = new StringBuffer();
        try {
            FileInputStream fis = new FileInputStream("test.txt");
            InputStreamReader isr = new InputStreamReader(fis, "UTF8");
            Reader in = new BufferedReader(isr);
            int ch;
            while ((ch = in.read()) > -1) {
                buffer.append((char)ch);
            }
            in.close();
            return buffer.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    static void writeOutput(String str) {
        try {
            FileOutputStream fos = new FileOutputStream("test.txt");
            Writer out = new OutputStreamWriter(fos, "UTF8");
            out.write(str);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String jaString = "\u65e5\u672c\u8a9e\u6587\u5b57\u5217";
        writeOutput(jaString);
        String inputString = readInput();
        String displayString = jaString + " " + inputString;
        System.out.println(displayString);
    }
}
