package api;

import javax.crypto.SecretKey;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.List;

public abstract class Encryption {
    private static Encryption instance;

    public static Encryption getInstance() {
        if(instance == null)
            instance = new EncryptionImplementation();
        return instance;
    }

    public abstract List<StreamDecryptor> availableStreamDecryptors();
    public abstract List<StreamEncryptor> availableStreamEncryptors();
    public abstract void setKeystore(Path pathToKeystore, String password);
    public abstract Enumeration<String> keysAliases();
    public abstract SecretKey retrieveKey(String alias, String keyPass);
    public abstract SecretKey retrieveKey(String alias);
}

class EncryptionImplementation extends Encryption{

    @Override
    public List<StreamDecryptor> availableStreamDecryptors() {
        return null;
    }

    @Override
    public List<StreamEncryptor> availableStreamEncryptors() {
        return null;
    }

    @Override
    public void setKeystore(Path pathToKeystore, String password) {

    }

    @Override
    public Enumeration<String> keysAliases() {
        return null;
    }

    @Override
    public SecretKey retrieveKey(String alias, String keyPass) {
        return null;
    }

    @Override
    public SecretKey retrieveKey(String alias) {
        return null;
    }
}