package lib;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class FileHasher {
    public String hashValueOf(Path file) {
        try
        {
            InputStream is = Files.newInputStream(file);
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            DigestInputStream dis = new DigestInputStream(is, messageDigest);

            dis.readAllBytes();

            return bytesToHex(messageDigest.digest());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException ignored) {

        }

        throw new RuntimeException("Error calculating hash for specified file: " + file);
    }

    private static String bytesToHex(byte[] bytes) {
        char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static void main(String[] args){
        FileHasher hasher = new FileHasher();
        System.out.println(hasher.hashValueOf(Paths.get("D:\\test.txt")));
    }
}
