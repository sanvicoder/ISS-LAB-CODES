import java.math.BigInteger;
import java.util.Random;
public class PollardsRho {
    public static BigInteger gcd(BigInteger a, BigInteger b) {
        return a.gcd(b);
    }
    public static BigInteger pollardsRho(BigInteger n) {
        if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            return BigInteger.TWO;
        }
        Random rand = new Random();
        BigInteger x = new BigInteger(n.bitLength(), rand).mod(n.subtract(BigInteger.ONE)).add(BigInteger.ONE);
        BigInteger y = x;
        BigInteger c = new BigInteger(n.bitLength(), rand).mod(n.subtract(BigInteger.ONE)).add(BigInteger.ONE);
        BigInteger d = BigInteger.ONE;

        while (d.equals(BigInteger.ONE)) {
            x = x.multiply(x).mod(n).add(c).mod(n);
            y = y.multiply(y).mod(n).add(c).mod(n);
            y = y.multiply(y).mod(n).add(c).mod(n);
            d = gcd(x.subtract(y).abs(), n);
            if (d.equals(n)) {
                return null; 
            }
        }
        return d;
    }
    public static boolean isPrimeUsingPollardsRho(BigInteger n, int attempts) {
        if (n.compareTo(BigInteger.ONE) <= 0)
            return false;
        for (int i = 0; i < attempts; i++) {
            BigInteger factor = pollardsRho(n);
            if (factor != null && !factor.equals(BigInteger.ONE) && !factor.equals(n)) {
                return false;
            }
        }
        return true;
    }
    public static void factorize(BigInteger n) {
        if (n.compareTo(BigInteger.ONE) == 0) {
            return;
        }
        if (n.isProbablePrime(20)) {
            System.out.print(n + " ");
            return;
        }
        BigInteger factor = pollardsRho(n);
        if (factor == null || factor.equals(n)) {
            System.out.print(n + " ");
            return;
        }
        factorize(factor);
        factorize(n.divide(factor));
    }

    public static void main(String[] args) {
        BigInteger testPrime = new BigInteger("14536232");
        BigInteger testComposite = new BigInteger("26082004");

        System.out.println(testPrime + " is prime? " + isPrimeUsingPollardsRho(testPrime, 5));
        System.out.println(testComposite + " is prime? " + isPrimeUsingPollardsRho(testComposite, 5));
        System.out.print("Factors of " + testPrime + ": ");
        factorize(testPrime);
        System.out.println();
        System.out.print("Factors of " + testComposite + ": ");
        factorize(testComposite);
        System.out.println();
    }
}

