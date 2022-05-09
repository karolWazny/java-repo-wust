package lib;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileHasher {
    private Path filepath;
    private DigestInputStream digestInputStream;
    private MessageDigest messageDigest;
    private byte[] hashValue;

    public String hashValueOf(Path file) {
        this.filepath = file;
        try
        {
            initializeMembers();
            calculateHashBytes();
            return buildHashRepresentation();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Error calculating hash for specified file: " + file);
    }

    private void initializeMembers() throws IOException {
        InputStream is = Files.newInputStream(filepath);
        messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ignored) {

        }
        digestInputStream = new DigestInputStream(is, messageDigest);
    }

    private void calculateHashBytes() throws IOException {
        this.digestInputStream.readAllBytes();
        this.hashValue = messageDigest.digest();
    }

    private String buildHashRepresentation(){
        return bytesToHex(this.hashValue);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder output = new StringBuilder();
        for(byte singleByte : bytes){
            output.append(String.format("%02x", singleByte));
        }
        return output.toString();
    }

    public static void main(String[] args){
        FileHasher hasher = new FileHasher();
        System.out.println(hasher.hashValueOf(Paths.get("D:\\test.txt")));
    }
}
