package api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.Key;

public interface StreamEncryptor {
    void encrypt(InputStream inputStream, OutputStream outputStream) throws InvalidKeyException, IOException;
    void setKey(Key key) throws InvalidKeyException;
}
