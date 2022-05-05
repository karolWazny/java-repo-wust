package api;

import java.security.InvalidKeyException;
import java.security.Key;

public interface CryptographicDevice {
    Class<? extends Key> keyType();
    String getAlgorithm();
    void setKey(Key key) throws InvalidKeyException;
}
