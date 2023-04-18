package com.fsck.k9.gka.keyexpansion;
import com.fsck.k9.gka.constants.Constants;
import com.fsck.k9.gka.utils.NumberGenerator;
import java.math.BigInteger;
import java.nio.ByteBuffer;

public class KeyExpansion {
    private byte[] external_key;
    public KeyExpansion(byte[] external_key) {
        if (external_key.length != Constants.BYTES_LENGTH) {
            byte[] padded_external_key = new byte[Constants.BYTES_LENGTH];
            System.arraycopy(external_key, 0, padded_external_key, 0, external_key.length);
            external_key = padded_external_key;
        }
        this.external_key = external_key;
    }
    public byte[] get_external_key() {
        return external_key;
    }
    public byte[] get_internal_key(int index) {
        ByteBuffer buffer = ByteBuffer.wrap(external_key);
        int int_external_key = buffer.getInt();
        BigInteger int_internal_key = NumberGenerator.generateNumberBBS(int_external_key, index);
        BigInteger truncated_internal_key = int_internal_key.mod(BigInteger.valueOf(1 << (Constants.BYTES_LENGTH * 8)));
        return truncated_internal_key.toByteArray();
    }
}
