import java.nio.charset.StandardCharsets;

public class Lab_10_MD5 {

    private static int leftRotate(int x, int n) {
        return (x << n) | (x >>> (32 - n));
    }
    private static byte[] padMessage(byte[] input) {
        int originalLength = input.length;
        long bitLength = originalLength * 8L;

        int paddingLength = (56 - (originalLength + 1) % 64);
        if (paddingLength < 0) {
            paddingLength += 64;
        }
        int newLength = originalLength + 1 + paddingLength + 8;
        byte[] padded = new byte[newLength];
        System.arraycopy(input, 0, padded, 0, originalLength);
        padded[originalLength] = (byte) 0x80;
        for (int i = 0; i < 8; i++) {
            padded[newLength - 8 + i] = (byte) ((bitLength >>> (8 * i)) & 0xFF);
        }
        return padded;
    }

    public static String md5(String input) {
        byte[] message = input.getBytes(StandardCharsets.UTF_8);
        byte[] padded = padMessage(message);

        int a0 = 0x67452301;
        int b0 = 0xefcdab89;
        int c0 = 0x98badcfe;
        int d0 = 0x10325476;

        int[] s = {
            7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22,
            5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20,
            4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23,
            6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21
        };

        int[] T = new int[64];
        for (int i = 0; i < 64; i++) {
            T[i] = (int) Math.floor((1L << 32) * Math.abs(Math.sin(i + 1)));
        }

        for (int i = 0; i < padded.length; i += 64) {
            int[] M = new int[16];
            for (int j = 0; j < 16; j++) {
                int index = i + j * 4;
                M[j] = ((padded[index] & 0xff)) |
                       ((padded[index + 1] & 0xff) << 8) |
                       ((padded[index + 2] & 0xff) << 16) |
                       ((padded[index + 3] & 0xff) << 24);
            }

            int A = a0, B = b0, C = c0, D = d0;
            for (int k = 0; k < 64; k++) {
                int F = 0, g = 0;
                if (k < 16) {
                    F = (B & C) | ((~B) & D);
                    g = k;
                } else if (k < 32) {
                    F = (D & B) | ((~D) & C);
                    g = (5 * k + 1) % 16;
                } else if (k < 48) {
                    F = B ^ C ^ D;
                    g = (3 * k + 5) % 16;
                } else {
                    F = C ^ (B | (~D));
                    g = (7 * k) % 16;
                }
                int temp = D;
                D = C;
                C = B;
                B = B + leftRotate(A + F + T[k] + M[g], s[k]);
                A = temp;
            }

            a0 += A;
            b0 += B;
            c0 += C;
            d0 += D;
        }

        return toHexLE(a0) + toHexLE(b0) + toHexLE(c0) + toHexLE(d0);
    }

    private static String toHexLE(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(String.format("%02x", (n >> (8 * i)) & 0xff));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String testMessage = "The quick brown fox jumps over the lazy dog";
        System.out.println("MD5: " + md5(testMessage));
    }
}

