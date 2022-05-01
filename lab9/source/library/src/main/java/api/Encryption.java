package api;

import sampleImplementation.StreamDecryptorImpl;
import sampleImplementation.StreamEncryptorImpl;

import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import java.util.LinkedList;
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
    public abstract void setKeystore(Path pathToKeystore, char[] password) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException;
    public abstract Enumeration<String> keysAliases() throws KeyStoreException;
    public abstract Key retrieveKey(String alias, char[] keyPass) throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException;

    public abstract SecretKey retrieveSecretKey(String alias, char[] keyPass) throws UnrecoverableEntryException,
            NoSuchAlgorithmException, KeyStoreException;

    public abstract SecretKey retrieveSecretKey(String alias) throws UnrecoverableEntryException,
            NoSuchAlgorithmException, KeyStoreException;

    public abstract Key retrieveKey(String alias) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException;
}

class EncryptionImplementation extends Encryption{
    private KeyStore keyStore;
    @Override
    public List<StreamDecryptor> availableStreamDecryptors() {
        List<StreamDecryptor> output =  new LinkedList<>();
        output.add(new StreamDecryptorImpl());
        return output;
    }

    @Override
    public List<StreamEncryptor> availableStreamEncryptors() {
        List<StreamEncryptor> output =  new LinkedList<>();
        output.add(new StreamEncryptorImpl());
        return output;
    }

    @Override
    public void setKeystore(Path pathToKeystore, char[] password) throws KeyStoreException,
            IOException, CertificateException, NoSuchAlgorithmException {
        keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        FileInputStream inputStream = new FileInputStream(String.valueOf(pathToKeystore));
        keyStore.load(inputStream, password);
    }

    @Override
    public Enumeration<String> keysAliases() throws KeyStoreException {
        return keyStore.aliases();
    }

    @Override
    public Key retrieveKey(String alias, char[] keyPass) throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException {
        return keyStore.getKey(alias, keyPass);
    }

    @Override
    public SecretKey retrieveSecretKey(String alias, char[] keyPass) throws UnrecoverableEntryException,
            NoSuchAlgorithmException, KeyStoreException {
        KeyStore.ProtectionParameter protectionParam
                = new KeyStore.PasswordProtection(keyPass);
        KeyStore.SecretKeyEntry secretKeyEnt
                = (KeyStore.SecretKeyEntry)keyStore.getEntry(alias, protectionParam);
        return secretKeyEnt.getSecretKey();
    }

    @Override
    public SecretKey retrieveSecretKey(String alias) throws UnrecoverableEntryException,
            NoSuchAlgorithmException, KeyStoreException {
        KeyStore.ProtectionParameter protectionParam
                = new KeyStore.PasswordProtection(null);
        KeyStore.SecretKeyEntry secretKeyEnt
                = (KeyStore.SecretKeyEntry)keyStore.getEntry(alias, protectionParam);
        return secretKeyEnt.getSecretKey();
    }

    @Override
    public Key retrieveKey(String alias) throws UnrecoverableKeyException,
            KeyStoreException,
            NoSuchAlgorithmException {
        return keyStore.getKey(alias, null);
    }
}