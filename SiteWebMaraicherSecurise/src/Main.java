import Modele.JsonHandler;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.*;
import java.security.cert.CertificateException;
import java.sql.SQLException;
public class Main {
    public static void main(String[] args) {
        HttpsServer serveur = null;
        try
        {
            JsonHandler.CreateJson();

            //initialiser le serveur HTTPS
            serveur = HttpsServer.create(new InetSocketAddress(8443),0);
            SSLContext sslContext = SSLContext.getInstance("SSLv3");

            //initialiser le keystore
            char[] password = "passwordKeystore".toCharArray();
            KeyStore ks = KeyStore.getInstance("JKS");
            FileInputStream fis = new FileInputStream("serverKeystoreMaraicher.jks");
            ks.load(fis, password);

            //setup du key manager factory
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, password);

            //setup pour trustmanager
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ks);

            //ajout au context ssl
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            serveur.setHttpsConfigurator(new HttpsConfigurator(sslContext) {
                public void configure(HttpsParameters params) {
                    try {
                        //mettres les paramettre pour ssl
                        params.setNeedClientAuth(false);
                        SSLContext context = getSSLContext();
                        SSLEngine engine = context.createSSLEngine();
                        params.setCipherSuites(engine.getEnabledCipherSuites());
                        params.setProtocols(engine.getEnabledProtocols());

                        //les parametre mis explicitement pour SSLParameters
                        params.setSSLParameters(context.getSupportedSSLParameters());

                    } catch (Exception ex) {
                        System.out.println("Failed to create HTTPS port");
                        ex.printStackTrace();
                    }
                }
            });

            serveur.createContext("/",new HandlerHtml());
            serveur.createContext("/css",new HandlerCss());
            serveur.createContext("/Javascript",new HandlerJavascript());
            serveur.createContext("/images",new HandlerImages());
            serveur.createContext("/Fichiers",new HandlerFichiers());
            serveur.createContext("/FormArticle",new HandlerFormulaire());
            System.out.println("Demarrage du serveur HTTP...");
            serveur.start();
        }
        catch (IOException e)
        {
            System.out.println("Erreur: " + e.getMessage());
        } catch (SQLException | NoSuchAlgorithmException | CertificateException | KeyStoreException |
                 UnrecoverableKeyException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }
}