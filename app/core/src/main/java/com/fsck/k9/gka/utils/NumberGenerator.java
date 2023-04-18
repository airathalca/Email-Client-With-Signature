package com.fsck.k9.gka.utils;
import java.math.BigInteger;
import com.fsck.k9.gka.constants.*;

public class NumberGenerator {

    public static BigInteger binaryExponentiation(int a, BigInteger b, BigInteger m) {
        if (a == 0) {
            return BigInteger.ZERO;
        }
        if (b.equals(BigInteger.ZERO)) {
            return BigInteger.ONE;
        }
        BigInteger result;
        if (b.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
            result = binaryExponentiation(a, b.divide(BigInteger.valueOf(2)), m);
            result = result.multiply(result).mod(m);
        } else {
            result = BigInteger.valueOf(a).mod(m);
            result = result.multiply(binaryExponentiation(a, b.subtract(BigInteger.ONE), m)).mod(m);
        }
        return result.add(m).mod(m);
    }

    public static BigInteger lcm(BigInteger a, BigInteger b) {
        return a.multiply(b).divide(a.gcd(b));
    }

    public static BigInteger generateNumberBBS(int seed, int index) {
        BigInteger exponent = binaryExponentiation(2, BigInteger.valueOf(index + 1), lcm(Constants.FIRST_PRIME.subtract(BigInteger.valueOf(1)), Constants.SECOND_PRIME.subtract(BigInteger.valueOf(1))));
        BigInteger result = binaryExponentiation(seed, exponent, Constants.FIRST_PRIME.multiply(Constants.SECOND_PRIME));
        return result;
    }

    public static BigInteger[] generateListBM(int seed, int n) {
        BigInteger[] list_of_random_int = new BigInteger[n];
        list_of_random_int[0] = BigInteger.valueOf(seed);
        for (int i = 1; i < n; i++) {
            list_of_random_int[i] = binaryExponentiation(Constants.PRIMITIVE_ROOT, list_of_random_int[i - 1], Constants.LARGE_PRIME);
        }
        return list_of_random_int;
    }

    public static byte[] substituteBytes(byte[] content, int seed) {
        byte[] arr = content;
        int length = content.length;
        BigInteger[] random_int = generateListBM(seed, Constants.SUBSTITUTION_BIT);
        int[] s_box = new int[Constants.SUBSTITUTION_BIT];
        for (int i = 0; i < Constants.SUBSTITUTION_BIT; i++) {
            s_box[i] = i;
        }
        int s_box_size = (int) Math.sqrt(Constants.SUBSTITUTION_BIT);
        for (int i = Constants.SUBSTITUTION_BIT - 1; i > 0; i--) {
            int j = random_int[i].mod(BigInteger.valueOf(i + 1)).intValue();
            int temp = s_box[i];
            s_box[i] = s_box[j];
            s_box[j] = temp;
        }
        for (int i = 0; i < length; i++) {
            int left = (arr[i] & 0xFF) >> 4;
            int right = arr[i] & 0x0F;
            int new_int = s_box[left * s_box_size + right] % Constants.SUBSTITUTION_BIT;
            arr[i] = (byte) new_int;
        }
        return arr;
    }

    public static byte[] shuffleBytes(byte[] content, int seed) {
        byte[] arr = content;
        int length = content.length;
        BigInteger[] random_int = generateListBM(seed, length);
        for (int i = length - 1; i > 0; i--) {
            int j = random_int[i].mod(BigInteger.valueOf(i + 1)).intValue();
            byte temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        return arr;
    }

    public static byte[] shuffleBits(byte[] content, int seed) {
        byte[] arr = content;
        BigInteger[] random_int = generateListBM(seed, Constants.BYTES_LENGTH * 8);
        for (int i = Constants.BYTES_LENGTH * 8 - 1; i > 0; i--) {
            int j = random_int[i].mod(BigInteger.valueOf(i + 1)).intValue();
            int first_bit = BinaryOperation.access_bit(arr, i);
            int second_bit = BinaryOperation.access_bit(arr, j);

            arr = BinaryOperation.change_bit(arr, i, second_bit);
            arr = BinaryOperation.change_bit(arr, j, first_bit);
        }
        return arr;
    }

    public static byte[] unshuffleBits(byte[] content, int seed) {
        byte[] arr = content;
        BigInteger[] random_int = generateListBM(seed, Constants.BYTES_LENGTH * 8);
        for (int i = 1; i < Constants.BYTES_LENGTH * 8; i++) {
            int j = random_int[i].mod(BigInteger.valueOf(i + 1)).intValue();
            int first_bit = BinaryOperation.access_bit(arr, i);
            int second_bit = BinaryOperation.access_bit(arr, j);

            arr = BinaryOperation.change_bit(arr, i, second_bit);
            arr = BinaryOperation.change_bit(arr, j, first_bit);
        }
        return arr;
    }
}
