package com.fsck.k9.ecdsa;

import java.security.PublicKey;
import android.util.Base64;

public class EmailParser {
    public static String exportPublicKey(PublicKey publicKey) {
        byte[] encodedPublicKey = publicKey.getEncoded();

        return Base64.encodeToString(encodedPublicKey, Base64.NO_WRAP | Base64.URL_SAFE);
    }

    public static PublicKey importPublicKey(String rawPublicKey) {
        byte[] decodedPublicKey = Base64.decode(rawPublicKey, Base64.NO_WRAP | Base64.URL_SAFE);

        return new PublicKey() {
            @Override
            public String getAlgorithm() {
                return "ECDSA";
            }

            @Override
            public String getFormat() {
                return "X.509";
            }

            @Override
            public byte[] getEncoded() {
                return decodedPublicKey;
            }
        };
    }

    public static String exportSignature(byte[] signature) {
        return Base64.encodeToString(signature, Base64.NO_WRAP | Base64.URL_SAFE);
    }

    public static byte[] importSignature(String rawSignature) {
        byte[] decodedSignature = Base64.decode(rawSignature, Base64.NO_WRAP | Base64.URL_SAFE);

        return decodedSignature;
    }
}
