package rsa;

import api.StreamEncryptor;

import javax.crypto.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public class RSAStreamEncryptor implements StreamEncryptor {
    private final Cipher cipher;
    private static final String transformation = "RSA/ECB/PKCS1Padding";
    private static final Class<PublicKey> keyType = PublicKey.class;

    public RSAStreamEncryptor() {
        try {
            cipher = Cipher.getInstance(transformation);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Could not instatiate Cipher object.");
        }
    }

    @Override
    public void encrypt(InputStream inputStream, OutputStream outputStream) throws InvalidKeyException, IOException {
        byte[] buffer = new byte[245];

        byte[] processed;

        try {
            for (int n = inputStream.read(buffer); n > 0; n = inputStream.read(buffer)) {
                processed = cipher.doFinal(buffer, 0, n);
                outputStream.write(processed, 0, processed.length);
            }
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setKey(Key key) throws InvalidKeyException {
        cipher.init(Cipher.ENCRYPT_MODE, key);
    }

    @Override
    public Class<? extends Key> keyType() {
        return keyType;
    }

    @Override
    public String getAlgorithm() {
        return "RSA 2048bit";
    }

    @Override
    public String toString() {
        return getAlgorithm();
    }
}
