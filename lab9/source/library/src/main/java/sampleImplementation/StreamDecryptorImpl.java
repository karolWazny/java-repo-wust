package sampleImplementation;

import api.StreamDecryptor;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class StreamDecryptorImpl implements StreamDecryptor {
    private static final String transformation = "AES";
    private SecretKey key;
    private Cipher cipher;

    public StreamDecryptorImpl() throws NoSuchPaddingException, NoSuchAlgorithmException {
        cipher = Cipher.getInstance(transformation);
    }

    @Override
    public void decrypt(InputStream inputStream, OutputStream outputStream) throws IOException,
            InvalidAlgorithmParameterException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] buffer = new byte[16];
        //inputStream.read(buffer);
        cipher.init(Cipher.DECRYPT_MODE, key);
        //cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(buffer));
        //CipherInputStream cipherStream = new CipherInputStream(inputStream, cipher);

        buffer = new byte[1024];
        for (int readBytes = inputStream.read(buffer); readBytes > -1; readBytes = inputStream.read(buffer)) {
            outputStream.write(cipher.update(buffer, 0, readBytes));
        }
        outputStream.write(cipher.doFinal());
    }

    @Override
    public void setKey(Key key) {
        this.key = (SecretKey) key;
    }
}
