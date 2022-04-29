package sampleImplementation;

import api.StreamEncryptor;

import javax.crypto.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class StreamEncryptorImpl implements StreamEncryptor {
    private static final String transformation = "AES";
    private SecretKey key;
    private Cipher cipher;

    public StreamEncryptorImpl() throws NoSuchPaddingException, NoSuchAlgorithmException {
        cipher = Cipher.getInstance(transformation);
    }

    @Override
    public void encrypt(InputStream inputStream, OutputStream outputStream) throws InvalidKeyException,
            IOException,
            IllegalBlockSizeException,
            BadPaddingException {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] buffer = cipher.getIV();
        //System.out.println("" + buffer.length);
        //CipherOutputStream cipherOut = new CipherOutputStream(outputStream, cipher);
        //outputStream.write(buffer);
        buffer = new byte[4096];
        for (int n = inputStream.read(buffer); n > 0; n = inputStream.read(buffer)) {
            System.out.println("" + n);
            //cipherOut.write(buffer, 0, n);
            byte[] outputBytes = cipher.update(buffer, 0, n);
            outputStream.write(outputBytes);
        }
        byte[] outputBytes = cipher.doFinal();
        System.out.println("" + outputBytes.length);
        outputStream.write(outputBytes);
    }

    @Override
    public void setKey(Key key) {
        this.key = (SecretKey) key;
    }
}
