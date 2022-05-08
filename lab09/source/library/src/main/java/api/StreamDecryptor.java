package api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;

public interface StreamDecryptor extends CryptographicDevice {
    void decrypt(InputStream inputStream, OutputStream outputStream) throws IOException, InvalidAlgorithmParameterException, InvalidKeyException;
}
