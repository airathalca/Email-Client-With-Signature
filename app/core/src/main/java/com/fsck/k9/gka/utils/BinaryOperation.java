package com.fsck.k9.gka.utils;

public class BinaryOperation {
    public static byte[] bytes_xor(byte[] a, byte[] b) {
        byte[] result = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = (byte) (a[i] ^ b[i]);
        }
        return result;
    }

    public static int access_bit(byte[] content, int index) {
        int base = index / 8;
        int shift = index % 8;
        return (content[base] >> shift) & 0x1;
    }

    public static byte[] change_bit(byte[] content, int index, int bit) {
        byte[] arr = content.clone();
        int base = index / 8;
        int shift = index % 8;
        if (bit == 1) {
            arr[base] |= (1 << shift);
        } else {
            arr[base] &= ~(1 << shift);
        }
        return arr;
    }
}
