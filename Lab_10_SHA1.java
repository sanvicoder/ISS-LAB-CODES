import java.nio.charset.StandardCharsets;

public class Lab_10_SHA1 {

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
            padded[newLength - 8 + i] = (byte) ((bitLength >>> (56 - 8 * i)) & 0xff);
        }
        return padded;
    }

    public static String sha1(String input) {
        byte[] message = input.getBytes(StandardCharsets.UTF_8);
        byte[] padded = padMessage(message);

        int h0 = 0x67452301;
        int h1 = 0xefcdab89;
        int h2 = 0x98badcfe;
        int h3 = 0x10325476;
        int h4 = 0xc3d2e1f0;

        for (int i = 0; i < padded.length; i += 64) {
            int[] w = new int[80];
            for (int j = 0; j < 16; j++) {
                int index = i + j * 4;
                w[j] = ((padded[index] & 0xff) << 24) |
                       ((padded[index + 1] & 0xff) << 16) |
                       ((padded[index + 2] & 0xff) << 8) |
                       (padded[index + 3] & 0xff);
            }
 
            for (int j = 16; j < 80; j++) {
                w[j] = leftRotate(w[j - 3] ^ w[j - 8] ^ w[j - 14] ^ w[j - 16], 1);
            }

            int a = h0, b = h1, c = h2, d = h3, e = h4;
    
            for (int j = 0; j < 80; j++) {
                int f, k;
                if (j < 20) {
                    f = (b & c) | ((~b) & d);
                    k = 0x5a827999;
                } else if (j < 40) {
                    f = b ^ c ^ d;
                    k = 0x6ed9eba1;
                } else if (j < 60) {
                    f = (b & c) | (b & d) | (c & d);
                    k = 0x8f1bbcdc;
                } else {
                    f = b ^ c ^ d;
                    k = 0xca62c1d6;
                }
                int temp = leftRotate(a, 5) + f + e + k + w[j];
                e = d;
                d = c;
                c = leftRotate(b, 30);
                b = a;
                a = temp;
            }
        
            h0 += a;
            h1 += b;
            h2 += c;
            h3 += d;
            h4 += e;
        }
        return String.format("%08x%08x%08x%08x%08x", h0, h1, h2, h3, h4);
    }


    public static void main(String[] args) {
        String testMessage = "The quick brown fox jumps over the lazy dog";
        System.out.println("SHA-1: " + sha1(testMessage));
    }
}

