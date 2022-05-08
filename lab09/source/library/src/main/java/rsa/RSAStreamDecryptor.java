package rsa;

import api.StreamDecryptor;

import javax.crypto.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.*;

public class RSAStreamDecryptor implements StreamDecryptor {
    private final Cipher cipher;
    private static final String transformation = "RSA/ECB/PKCS1Padding";
    private static final Class<PrivateKey> keyType = PrivateKey.class;

    public RSAStreamDecryptor() {
        try {
            this.cipher = Cipher.getInstance(transformation);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Could not instatiate Cipher object.");
        }
    }

    @Override
    public void decrypt(InputStream inputStream, OutputStream outputStream) throws IOException, InvalidAlgorithmParameterException, InvalidKeyException {
        byte[] buffer = new byte[256];
        for (int readBytes = inputStream.read(buffer); readBytes > -1; readBytes = inputStream.read(buffer)) {
            try {
                byte[] processed = cipher.doFinal(buffer);
                outputStream.write(processed, 0, processed.length);
            } catch (IllegalBlockSizeException | BadPaddingException e) {
                e.printStackTrace();
                throw new RuntimeException("Encountered an error decrypting file.\n");
            }
        }
    }

    @Override
    public void setKey(Key key) throws InvalidKeyException {
        cipher.init(Cipher.DECRYPT_MODE, key);
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
