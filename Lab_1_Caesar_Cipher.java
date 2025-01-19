import java.util.*;

public class Lab_1_Caesar_Cipher {
    // Method to encrypt text using Caesar Cipher
    public static String encrypt(String plaintext, int shift) {
        StringBuilder encrypted = new StringBuilder();
        shift = shift % 26; // To handle shifts greater than 26

        for (char c : plaintext.toCharArray()) {
            if (Character.isUpperCase(c)) {
                char encryptedChar = (char) ((c - 'A' + shift) % 26 + 'A');
                encrypted.append(encryptedChar);
            } else if (Character.isLowerCase(c)) {
                char encryptedChar = (char) ((c - 'a' + shift) % 26 + 'a');
                encrypted.append(encryptedChar);
            } else {
                encrypted.append(c); // Non-alphabetic characters remain unchanged
            }
        }
        return encrypted.toString();
    }

    // Method to decrypt text using Caesar Cipher
    public static String decrypt(String ciphertext, int shift) {
        return encrypt(ciphertext, 26 - (shift % 26)); // Decrypt by reversing the shift
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the plaintext:");
        String plaintext = scanner.nextLine();

        System.out.println("Enter the shift key:");
        int shift = scanner.nextInt();

        String encryptedText = encrypt(plaintext, shift);
        System.out.println("Encrypted Text: " + encryptedText);

        String decryptedText = decrypt(encryptedText, shift);
        System.out.println("Decrypted Text: " + decryptedText);

        scanner.close();
    }
}
