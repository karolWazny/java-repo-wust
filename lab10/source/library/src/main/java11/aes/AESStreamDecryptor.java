package aes;

import api.StreamDecryptor;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.*;

public class AESStreamDecryptor implements StreamDecryptor {
    private static final String transformation = "AES/CBC/PKCS5Padding";
    private SecretKey key;
    private final Cipher cipher;
    private final static Class<SecretKey> keyType = SecretKey.class;

    public AESStreamDecryptor() {
        try {
            cipher = Cipher.getInstance(transformation);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Could not instatiate Cipher object.");
        }
    }

    @Override
    public void decrypt(InputStream inputStream, OutputStream outputStream) throws IOException,
            InvalidAlgorithmParameterException, InvalidKeyException {
        byte[] buffer = new byte[16];
        inputStream.read(buffer);
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(buffer));

        CipherInputStream cipherStream = new CipherInputStream(inputStream, cipher);
        buffer = new byte[1024];
        for (int readBytes = cipherStream.read(buffer); readBytes > -1; readBytes = cipherStream.read(buffer)) {
            outputStream.write(buffer, 0, readBytes);
        }
    }

    @Override
    public void setKey(Key key) {
        this.key = (SecretKey) key;
    }

    @Override
    public Class<? extends Key> keyType() {
        return keyType;
    }

    @Override
    public String getAlgorithm() {
        return "AES";
    }

    @Override
    public String toString() {
        return getAlgorithm();
    }
}
