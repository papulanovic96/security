package com.example.security.repository;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Repository;

import com.example.security.entity.PrivateKeyNCertificate;
import com.example.security.enumType.CertificateType;

@Repository
public class StoreNLoadCertificate {


    public PrivateKeyNCertificate[] load(String alias, String password, String keystore,
                            String server, CertificateType type) {
        try {
            KeyStore keyStore = KeyStore.getInstance("jks");
            Path storage;
            if (type == CertificateType.USER) {
                storage = Paths.get("src", "main", "resources", "users",
                        keystore, "trust", "storage.jks");
            } else {
                storage = Paths.get("src", "main", "resources", "servers", server,
                        keystore, "trust", "storage.jks");
            }
            keyStore.load(new FileInputStream(storage.toString()), password.toCharArray());
            Key key = keyStore.getKey(alias, password.toCharArray());

            if (key instanceof PrivateKey) {
                Certificate[] certs = keyStore.getCertificateChain(alias);
                return PrivateKeyNCertificate.toChain(certs, key);
            } else {
                return null;
            }
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException |
                CertificateException | UnrecoverableKeyException | ClassCastException e) {
        	return null;
        }
    }

    public void store(PrivateKeyNCertificate[] cks, String server, CertificateType type, String alias, String password) {
        PrivateKeyNCertificate leaf = cks[0];
        Path storage, trustStorage;
        if (type == CertificateType.USER) {
            storage = Paths.get("src", "main", "resources", "servers", server, "users",
                    leaf.getCertificate().getSerialNumber().toString());
            trustStorage = Paths.get("src", "main", "resources", "servers", server, "users",
                    leaf.getCertificate().getSerialNumber().toString(), "trust");
        } else if (type == CertificateType.MIDDLEMAN) {
            storage = Paths.get("src", "main", "resources", "servers", server, "sub",
                    leaf.getCertificate().getSerialNumber().toString());
            trustStorage = Paths.get("src", "main", "resources", "servers", server, "sub",
                    leaf.getCertificate().getSerialNumber().toString(), "trust");
        } else {
            storage = Paths.get("src", "main", "resources", "servers", server,
                    leaf.getCertificate().getSerialNumber().toString());
            trustStorage = Paths.get("src", "main", "resources", "servers", server,
                    leaf.getCertificate().getSerialNumber().toString(), "trust");
        }
        try {

            Files.createDirectories(trustStorage);
            FileOutputStream out = new FileOutputStream(Paths.get(storage.toString(),
                    "cer_" + leaf.getCertificate().getSerialNumber() + ".cer").toString());
            out.write(leaf.getCertificate().getEncoded());
            out.close();

            storePrivateKey(alias, password.toCharArray(),
                    Paths.get(trustStorage.toString(), "storage.jks").toString(), leaf.getpKey(),
                    PrivateKeyNCertificate.toChain(cks));
        } catch (Exception e) {
        }
    }

    public X509Certificate[] findCertificates(List<String> serialNumbers) {
        List<X509Certificate> result = new ArrayList<>();
        String rootPath = Paths.get("src", "main", "resources", "servers").toString();

        File root = new File(rootPath);
        try {
            for (File node : Objects.requireNonNull(root.listFiles())) {
                if (serialNumbers.isEmpty()) {
                    return result.toArray(new X509Certificate[0]);
                }
                for (File subNode : Objects.requireNonNull(node.listFiles())) {
                    if (serialNumbers.isEmpty()) {
                        return result.toArray(new X509Certificate[0]);
                    }
                    if (subNode.getName().equals("users") || subNode.getName().equals("sub")) {
                        for (File subSubNode : Objects.requireNonNull(subNode.listFiles())) {
                            if (serialNumbers.isEmpty()) {
                                return result.toArray(new X509Certificate[0]);
                            }
                            if (serialNumbers.contains(subSubNode.getName())) {
                                result.add(readCertificate(Paths.get(subSubNode.getPath(),
                                        "cer_" + subSubNode.getName() + ".cer").toString()));
                                serialNumbers.remove(subSubNode.getName());
                            }
                        }
                    } else if (serialNumbers.contains(subNode.getName())) {
                        result.add(readCertificate(Paths.get(subNode.getPath(),
                                "cer_" + subNode.getName() + ".cer").toString()));
                        serialNumbers.remove(subNode.getName());
                    }
                }
            }
            return result.toArray(new X509Certificate[0]);
        } catch (NullPointerException | FileNotFoundException | CertificateException e) {
        	return null;
        }
    }

    public String[] findTarget(String target) {
        List<String> found = new ArrayList<>();
        String rootPath = Paths.get("src", "main", "resources", "servers").toString();

        File root = new File(rootPath);
        try {
            for (File node : Objects.requireNonNull(root.listFiles())) {
                for (File subNode : Objects.requireNonNull(node.listFiles())) {
                    if (subNode.getName().equals("users")) {
                        for (File subSubNode : Objects.requireNonNull(subNode.listFiles())) {
                            if (target.equals(subSubNode.getName())) {
                                return new String[]{
                                        Paths.get(subSubNode.getPath(), "trust", "trust_storage.jks")
                                                .toString(), Paths.get("src", "main", "resources", "users",
                                        target, "trust", "trust_storage.jks").toString(),
                                };
                            }
                        }
                    } else if (subNode.getName().equals("sub")) {
                        for (File subSubNode : Objects.requireNonNull(subNode.listFiles())) {
                            if (target.equals(subSubNode.getName())) {
                                found.add(subSubNode.getPath());
                            }
                        }
                    } else if (target.equals(subNode.getName())) {
                        found.add(subNode.getPath());
                    }
                }
            }
            found.removeIf(path -> path.contains("/sub/"));
            return new String[]{Paths.get(found.get(0), "trust", "trust_storage.jks").toString()};
        } catch (NullPointerException e) {
            return new String[] {("Error!").toString()};
        }
    }

    private void storePrivateKey(String alias, char[] password, String keystore, Key key, X509Certificate[] chain)
            throws Exception {
        KeyStore keyStore = KeyStore.getInstance("jks");
        keyStore.load(null, null);

        keyStore.setKeyEntry(alias, key, password, chain);
        keyStore.store(new FileOutputStream(keystore), password);
    }

    private X509Certificate readCertificate(String name) throws FileNotFoundException, CertificateException {
        FileInputStream fis = new FileInputStream(name);
        BufferedInputStream bis = new BufferedInputStream(fis);

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate cert = cf.generateCertificate(bis);
        return (X509Certificate) cert;
    }
}
