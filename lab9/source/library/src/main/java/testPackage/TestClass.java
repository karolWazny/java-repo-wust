package testPackage;

import api.StreamDecryptor;
import api.StreamEncryptor;
import rsa.RSAStreamDecryptor;
import rsa.RSAStreamEncryptor;

import javax.crypto.*;
import java.io.*;
import java.security.*;

public class TestClass {
    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        StreamEncryptor encryptor = new RSAStreamEncryptor();
        StreamDecryptor decryptor = new RSAStreamDecryptor();
        InputStream inputStream = new FileInputStream("file.docx");
        OutputStream outputStream = new FileOutputStream("file.docx.enc");
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);

        KeyPair keyPair = generator.generateKeyPair();
        encryptor.setKey(keyPair.getPublic());
        encryptor.encrypt(inputStream, outputStream);
        inputStream.close();
        outputStream.close();

        inputStream = new FileInputStream("file.docx.enc");
        outputStream = new FileOutputStream("decrypted-file.docx");
        decryptor.setKey(keyPair.getPrivate());
        decryptor.decrypt(inputStream, outputStream);
    }
}
