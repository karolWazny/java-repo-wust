package api;

import crypto.DefaultEncryption;
import java.io.IOException;
import java.nio.file.Path;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import java.util.List;

public abstract class Encryption {
    private static Encryption instance;

    public static Encryption getInstance() {
        if(instance == null)
            instance = new DefaultEncryption();
        return instance;
    }

    public abstract List<StreamDecryptor> availableStreamDecryptors();
    public abstract List<StreamEncryptor> availableStreamEncryptors();
    public abstract void setKeystore(Path pathToKeystore, char[] password) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException;
    public abstract Enumeration<String> keysAliases() throws KeyStoreException;

    public abstract <T> T retrieveKey(String alias, char[] keyPass, Class<T> keyType) throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException;
}
