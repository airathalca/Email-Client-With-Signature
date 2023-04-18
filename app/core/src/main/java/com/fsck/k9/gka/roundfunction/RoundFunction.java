package com.fsck.k9.gka.roundfunction;
import com.fsck.k9.gka.utils.NumberGenerator;
import java.nio.ByteBuffer;


public class RoundFunction {

    public byte[] hash_function(byte[] key, byte[] content) {
        ByteBuffer buffer = ByteBuffer.wrap(key);
        int int_internal_key = buffer.getInt();
        return NumberGenerator.shuffleBytes(NumberGenerator.substituteBytes(content, int_internal_key), int_internal_key);
    }
}
