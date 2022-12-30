package com.kelmorgan.generatekeypair.service.impl;

import com.kelmorgan.generatekeypair.service.GenerateKeys;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Service
public class GenerateKeysImpl implements GenerateKeys {

    private static final int KEY_SIZE = 2048;

    @Override
    public void generate() {
        try {
            Security.addProvider(new BouncyCastleProvider());

            KeyPair keyPair = generateRSAKeyPair();
            RSAPrivateKey priv = (RSAPrivateKey) keyPair.getPrivate();
            RSAPublicKey pub = (RSAPublicKey) keyPair.getPublic();

            System.out.println("Private Key format: "+ priv.getFormat());
            System.out.println("Public Key format: "+ pub.getFormat());

            writePemFile(priv, "PRIVATE KEY", "id_rsa");
            writePemFile(pub, "PUBLIC KEY", "id_rsa.pub");
        } catch (Exception e) {
            System.out.println("Exception occurred: "+ e.getMessage());
        }
    }


    private KeyPair generateRSAKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
        generator.initialize(KEY_SIZE);

        KeyPair keyPair = generator.generateKeyPair();
        System.out.println("RSA key pair generated.");
        return keyPair;
    }

    private void writePemFile(Key key, String description, String filename)
            throws FileNotFoundException, IOException {
        PemFile pemFile = new PemFile(key, description);
        pemFile.write(filename);


    }


    private class PemFile {
        private final PemObject pemObject;

        public PemFile(Key key, String description) {
            this.pemObject = new PemObject(description, key.getEncoded());
        }

        public void write(String filename) throws FileNotFoundException, IOException {
            PemWriter pemWriter = new PemWriter(new OutputStreamWriter(new FileOutputStream(filename)));
            try {
                pemWriter.writeObject(this.pemObject);
            } finally {
                pemWriter.close();
            }
        }
    }
}
