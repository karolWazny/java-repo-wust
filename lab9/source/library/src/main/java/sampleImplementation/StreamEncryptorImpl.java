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
    private static final String transformation = "AES/CBC/PKCS5Padding";
    private SecretKey key;
    private final Cipher cipher;

    public StreamEncryptorImpl(){
        try {
            cipher = Cipher.getInstance(transformation);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Could not instatiate Cipher object.");
        }
    }

    @Override
    public void encrypt(InputStream inputStream, OutputStream outputStream) throws InvalidKeyException, IOException {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] buffer = cipher.getIV();
        outputStream.write(buffer);

        CipherOutputStream cipherOut = new CipherOutputStream(outputStream, cipher);
        buffer = new byte[4096];
        for (int n = inputStream.read(buffer); n > 0; n = inputStream.read(buffer)) {
            cipherOut.write(buffer, 0, n);
        }
        cipherOut.close();
    }

    @Override
    public void setKey(Key key) {
        this.key = (SecretKey) key;
    }

    @Override
    public String getAlgorithm() {
        return "AES";
    }
}
