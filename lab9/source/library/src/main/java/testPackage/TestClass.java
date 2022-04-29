package testPackage;

import api.StreamDecryptor;
import api.StreamEncryptor;
import sampleImplementation.StreamDecryptorImpl;
import sampleImplementation.StreamEncryptorImpl;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class TestClass {
    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        StreamEncryptor encryptor = new StreamEncryptorImpl();
        StreamDecryptor decryptor = new StreamDecryptorImpl();
        InputStream inputStream = new FileInputStream("file.docx");
        OutputStream outputStream = new FileOutputStream("file.docx.enc");
        Key key = KeyGenerator.getInstance("AES").generateKey();
        encryptor.setKey(key);
        encryptor.encrypt(inputStream, outputStream);
        inputStream.close();
        outputStream.close();


        inputStream = new FileInputStream("file.docx.enc");
        outputStream = new FileOutputStream("decrypted-file.docx");
        decryptor.setKey(key);
        decryptor.decrypt(inputStream, outputStream);
    }
}
