package com.fsck.k9.gka.feistel;
import java.nio.ByteBuffer;

import com.fsck.k9.gka.roundfunction.*;
import com.fsck.k9.gka.utils.*;
import com.fsck.k9.gka.keyexpansion.*;
import com.fsck.k9.gka.constants.*;

public class FeistelNetwork {
    private RoundFunction round_function;
    private KeyExpansion key_expansion;
    public FeistelNetwork(RoundFunction round_function, KeyExpansion key_expansion) {
        this.round_function = round_function;
        this.key_expansion = key_expansion;
    }
    public byte[] encrypt(byte[] content, int num_of_iteration) {
        if (content.length % Constants.BYTES_LENGTH != 0) {
            // add padding to the content to make it multiple of 16 bytes
            byte[] padded_content = new byte[content.length + Constants.BYTES_LENGTH - content.length % Constants.BYTES_LENGTH];
            System.arraycopy(content, 0, padded_content, 0, content.length);
            content = padded_content;
        }
        int int_external_key = ByteBuffer.wrap(key_expansion.get_external_key()).getInt();
        byte[] shuffled = NumberGenerator.shuffleBits(content, int_external_key);
        byte[] iterated = iteration_encrypt(shuffled, num_of_iteration, 0);
        byte[] unshuffled = NumberGenerator.unshuffleBits(iterated, int_external_key);
        return unshuffled;
    }
    public byte[] iteration_encrypt(byte[] content, int num_of_iteration, int current_index) {
        if (current_index == num_of_iteration) {
            return content;
        }
        else {
            int length = content.length;
            byte[] left = new byte[length / 2];
            byte[] right = new byte[length / 2];
            System.arraycopy(content, 0, left, 0, length / 2);
            System.arraycopy(content, length / 2, right, 0, length / 2);
            byte[] internal_key = key_expansion.get_internal_key(current_index);
            byte[] hash = round_function.hash_function(internal_key, right);
            byte[] temp = left;
            left = right;
            right = BinaryOperation.bytes_xor(temp, hash);
            byte[] combined = new byte[left.length + right.length];
            System.arraycopy(left, 0, combined, 0, left.length);
            System.arraycopy(right, 0, combined, left.length, right.length);
            return iteration_encrypt(combined, num_of_iteration, current_index + 1);
        }
    }

    public byte[] decrypt(byte[] content, int num_of_iteration) {
        int int_external_key = ByteBuffer.wrap(key_expansion.get_external_key()).getInt();
        byte[] shuffled = NumberGenerator.shuffleBits(content, int_external_key);
        byte[] iterated = iteration_decrypt(shuffled, num_of_iteration, 0);
        byte[] unshuffled = NumberGenerator.unshuffleBits(iterated, int_external_key);
        return unshuffled;
    }
    public byte[] iteration_decrypt(byte[] content, int num_of_iteration, int current_index) {
        if (current_index == num_of_iteration) {
            return content;
        }
        else {
            int length = content.length;
            byte[] left = new byte[length / 2];
            byte[] right = new byte[length / 2];
            System.arraycopy(content, 0, left, 0, length / 2);
            System.arraycopy(content, length / 2, right, 0, length / 2);
            byte[] internal_key = key_expansion.get_internal_key(num_of_iteration - current_index - 1);
            byte[] hash = round_function.hash_function(internal_key, left);
            byte[] temp = right;
            right = left;
            left = BinaryOperation.bytes_xor(temp, hash);
            byte[] combined = new byte[left.length + right.length];
            System.arraycopy(left, 0, combined, 0, left.length);
            System.arraycopy(right, 0, combined, left.length, right.length);
            return iteration_decrypt(combined, num_of_iteration, current_index + 1);
        }
    }

}
