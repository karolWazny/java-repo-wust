package crypto;

import aes.AESStreamDecryptor;
import aes.AESStreamEncryptor;
import api.Encryption;
import api.StreamDecryptor;
import api.StreamEncryptor;
import rsa.RSAStreamDecryptor;
import rsa.RSAStreamEncryptor;

import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

public class DefaultEncryption  extends Encryption {
    private final List<StreamEncryptor> streamEncryptors = new LinkedList<>();
    private KeyStore keyStore;
    private List<StreamDecryptor> streamDecryptors = new LinkedList<>();

    public DefaultEncryption() {
        streamDecryptors.add(new AESStreamDecryptor());
        streamDecryptors.add(new RSAStreamDecryptor());

        streamEncryptors.add(new AESStreamEncryptor());
        streamEncryptors.add(new RSAStreamEncryptor());
    }

    @Override
    public List<StreamDecryptor> availableStreamDecryptors() {
        return streamDecryptors;
    }

    @Override
    public List<StreamEncryptor> availableStreamEncryptors() {
        return streamEncryptors;
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
    public <T> T retrieveKey(String alias, char[] keyPass, Class<T> keyType)
            throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException {
        switch(keyType.getName()){
            case "javax.crypto.SecretKey":
                return (T) retrieveSecretKey(alias, keyPass);
            case "java.security.PrivateKey":
                return (T) retrievePrivateKey(alias, keyPass);
            case "java.security.PublicKey":
                return (T) retrievePublicKey(alias);
            case "java.security.Key":
                return (T) retrieveKey(alias, keyPass);
            default:
                throw new RuntimeException("Unknown key type!");
        }
    }

    private PublicKey retrievePublicKey(String alias) throws KeyStoreException {
        return keyStore.getCertificate(alias).getPublicKey();
    }

    private PrivateKey retrievePrivateKey(String alias, char[] keyPass)
            throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException {
        KeyStore.ProtectionParameter protectionParam
                = new KeyStore.PasswordProtection(keyPass);
        KeyStore.PrivateKeyEntry secretKeyEnt
                = (KeyStore.PrivateKeyEntry)keyStore.getEntry(alias, protectionParam);
        return secretKeyEnt.getPrivateKey();
    }

    private Key retrieveKey(String alias, char[] keyPass) throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException {
        return keyStore.getKey(alias, keyPass);
    }

    private SecretKey retrieveSecretKey(String alias, char[] keyPass) throws UnrecoverableEntryException,
            NoSuchAlgorithmException, KeyStoreException {
        KeyStore.ProtectionParameter protectionParam
                = new KeyStore.PasswordProtection(keyPass);
        KeyStore.SecretKeyEntry secretKeyEnt
                = (KeyStore.SecretKeyEntry)keyStore.getEntry(alias, protectionParam);
        return secretKeyEnt.getSecretKey();
    }
}