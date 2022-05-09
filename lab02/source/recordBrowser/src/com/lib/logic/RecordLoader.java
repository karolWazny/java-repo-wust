package com.lib.logic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class RecordLoader {
    private List<String> lines;
    private BufferedImage image;
    private Record output;

    public Record load(Path path) throws IOException {
        lines = Files.readAllLines(path.resolve("record.txt"));
        image = ImageIO.read(Files.newInputStream(path.resolve("image.png")));
        return buildRecord();
    }
    private Record buildRecord(){
        output = new Record();
        processLines();
        processImage();
        return output;
    }
    private void processLines(){
        for (String line:
                lines){
            int spaceIndex = line.indexOf(' ');
            String key = line.substring(0, spaceIndex);
            String value = line.substring(spaceIndex).trim();
            switch (key) {
                case "FIRST_NAME":
                    output.setFirstName(value);
                    break;
                case "LAST_NAME":
                    output.setLastName(value);
                    break;
                case "BIRTH_DATE":
                    output.setBirthDate(LocalDate.parse(value, formatter()));
                    break;
                case "EMAIL":
                    output.setEmail(value);
                    break;
            }
        }
    }

    private DateTimeFormatter formatter(){
        return DateTimeFormatter.ofPattern("dd-LLL-yyyy", Locale.US);
    }

    private void processImage(){
        output.setImage(image);
    }
}
