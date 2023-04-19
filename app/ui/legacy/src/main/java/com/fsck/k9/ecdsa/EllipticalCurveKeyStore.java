package com.fsck.k9.ecdsa;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import javax.crypto.SecretKey;

public class EllipticalCurveKeyStore {
    public KeyStore keyStore;

    public EllipticalCurveKeyStore() throws KeyStoreException, NoSuchProviderException {
        this.keyStore = KeyStore.getInstance( "AndroidKeyStore");
    }

    public void store() throws KeyStoreException, IOException, NoSuchAlgorithmException,
        CertificateException {
        this.keyStore.store(null);
    }

    public void load() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        this.keyStore.load(null);
    }

    public void save(PrivateKey privateKey) throws KeyStoreException {
        SecretKey secretKey = EllipticalCurveKey.convertToSecretKey(privateKey);
        KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(secretKey);

        this.keyStore.setEntry("private-key", secretKeyEntry, null);
    }

    public PrivateKey read() throws NoSuchAlgorithmException, UnrecoverableEntryException, KeyStoreException {
        KeyStore.Entry entry = this.keyStore.getEntry("private-key", null);

        if (!(entry instanceof KeyStore.SecretKeyEntry)) {
            return null;
        }

        KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry) entry;
        SecretKey secretKey = secretKeyEntry.getSecretKey();

        return EllipticalCurveKey.convertToPrivateKey(secretKey);
    }
}
