package com.fsck.k9.ecdsa;

import java.io.IOException;
import java.math.BigInteger;
import java.security.PublicKey;

public class DigitalVerifying extends EllipticalCurveCryptography {
    private Point rawPublicKey;

    public DigitalVerifying(EllipticalCurve curve, Point basePoint, BigInteger n, PublicKey publicKey)
        throws IOException {
        super(curve, basePoint, n);

        byte[] encodedPrivateKey = publicKey.getEncoded();

        this.rawPublicKey = new Point(encodedPrivateKey);
    }

    public boolean verifySigning(byte[] hash, byte[] sign) throws IOException {
        Point point = new Point(sign);
        BigInteger hashedValue = new BigInteger(1, hash);

        BigInteger r = point.getX();
        BigInteger s = point.getY();

        BigInteger w = s.modInverse(n);
        BigInteger u1 = hashedValue.multiply(w).mod(n);
        BigInteger u2 = r.multiply(w).mod(n);

        Point P = this.curve.addPoint(
            this.curve.multiplyPoint(this.basePoint, u1),
            this.curve.multiplyPoint(this.rawPublicKey, u2));

        if (P.isZero()) {
            return false;
        }

        BigInteger v = P.getX().mod(n);

        return v.equals(r);
    }
}
