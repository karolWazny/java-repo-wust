package main;

import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.RMISocketFactory;
import java.security.*;
import java.security.cert.CertificateException;

@Slf4j
public class SafeSocketFactory extends RMISocketFactory {
    private static final String KEY_STORE_PATH = "src/main/resources/keystore";
    private int serverPort;

    private SSLServerSocketFactory serverSocketFactory = null;

    public SafeSocketFactory() {
        this.serverPort = 10101;
        initServerSocketFactory();
    }

    private void initServerSocketFactory() {
        try {
            char[] password = "changeit".toCharArray();
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream(KEY_STORE_PATH), password);
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keyStore, password);

            SSLContext context = SSLContext.getInstance("TLS");
            context.init(keyManagerFactory.getKeyManagers(), null, null);
            serverSocketFactory = context.getServerSocketFactory();
            log.info("Successfully initialized server socket factory.");
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException {
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        return factory.createSocket(host, port);
    }

    @Override
    public ServerSocket createServerSocket(int port) throws IOException {
        if (serverSocketFactory == null)
            initServerSocketFactory();
        return serverSocketFactory.createServerSocket(port == 0 ? serverPort++ : port);
    }

}
