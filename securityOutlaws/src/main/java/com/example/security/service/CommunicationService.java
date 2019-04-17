package com.example.security.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.entity.PrivateKeyNCertificate;
import com.example.security.repository.StoreNLoadCertificate;

@Service
public class CommunicationService {
	
    @Autowired
    private StoreNLoadCertificate certificateStorage;

    public void sendCertificate(PrivateKeyNCertificate[] cks, String destination, boolean user) {
    	PrivateKeyNCertificate leaf = cks[0];
        Path storage, trustStorage;
        if (user) {
            storage = Paths.get("src", "main", "resources", "users",
                    leaf.getCertificate().getSerialNumber().toString());
            trustStorage = Paths.get("src", "main", "resources", "users",
                    leaf.getCertificate().getSerialNumber().toString(), "trust");
        } else {
            storage = Paths.get("src", "main", "resources", "servers", destination,
                    leaf.getCertificate().getSerialNumber().toString());
            trustStorage = Paths.get("src", "main", "resources", "servers", destination,
                    leaf.getCertificate().getSerialNumber().toString(), "trust");
        }
        try {

            Files.createDirectories(trustStorage);
            FileOutputStream out = new FileOutputStream(Paths.get(storage.toString(),
                    "cer_" + leaf.getCertificate().getSerialNumber() + ".cer").toString());
            out.write(leaf.getCertificate().getEncoded());
            out.close();
            storePrivateKey("keys", "zgadija".toCharArray(),
                    Paths.get(trustStorage.toString(), "storage.jks").toString(), leaf.getpKey(),
                    PrivateKeyNCertificate.toChain(cks));
        } catch (Exception e) {
        }
    }

    public void sendTrustStorage(KeyStore keyStore, String destination) {
        try {
            keyStore.store(new FileOutputStream(destination), "zgadija".toCharArray());
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
        }
    }

    private void storePrivateKey(String alias, char[] password, String keystore, Key key, X509Certificate[] chain)
            throws Exception {
        KeyStore keyStore = KeyStore.getInstance("jks");
        keyStore.load(null, null);

        keyStore.setKeyEntry(alias, key, password, chain);
        keyStore.store(new FileOutputStream(keystore), password);
    }

}
