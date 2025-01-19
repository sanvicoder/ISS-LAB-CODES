import java.util.Scanner;

public class Lab_2_PlayFair_Cipher {
    private char[][] keyMatrix = new char[5][5];
    
    // Generate the key matrix
    private void generateKeyMatrix(String key) {
        boolean[] isPresent = new boolean[26];
        key = key.toUpperCase().replace('J', 'I').replaceAll("[^A-Z]", "");
        int index = 0;

        for (char c : key.toCharArray()) {
            if (!isPresent[c - 'A']) {
                keyMatrix[index / 5][index % 5] = c;
                isPresent[c - 'A'] = true;
                index++;
            }
        }

        for (char c = 'A'; c <= 'Z'; c++) {
            if (!isPresent[c - 'A'] && c != 'J') {
                keyMatrix[index / 5][index % 5] = c;
                index++;
            }
        }
    }

    // Prepare the plaintext for encryption
    private String prepareText(String text) {
        text = text.toUpperCase().replace('J', 'I').replaceAll("[^A-Z]", "");
        StringBuilder preparedText = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char current = text.charAt(i);
            if (i + 1 < text.length() && current == text.charAt(i + 1)) {
                preparedText.append(current).append('X');
            } else {
                preparedText.append(current);
            }
        }

        if (preparedText.length() % 2 != 0) {
            preparedText.append('X');
        }

        return preparedText.toString();
    }

    // Find the position of a character in the key matrix
    private int[] findPosition(char c) {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (keyMatrix[row][col] == c) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }

    // Encrypt or decrypt a digraph
    private String processDigraph(String digraph, boolean encrypt) {
        int[] pos1 = findPosition(digraph.charAt(0));
        int[] pos2 = findPosition(digraph.charAt(1));
        char char1, char2;

        if (pos1[0] == pos2[0]) { // Same row
            char1 = keyMatrix[pos1[0]][(pos1[1] + (encrypt ? 1 : 4)) % 5];
            char2 = keyMatrix[pos2[0]][(pos2[1] + (encrypt ? 1 : 4)) % 5];
        } else if (pos1[1] == pos2[1]) { // Same column
            char1 = keyMatrix[(pos1[0] + (encrypt ? 1 : 4)) % 5][pos1[1]];
            char2 = keyMatrix[(pos2[0] + (encrypt ? 1 : 4)) % 5][pos2[1]];
        } else { // Rectangle
            char1 = keyMatrix[pos1[0]][pos2[1]];
            char2 = keyMatrix[pos2[0]][pos1[1]];
        }

        return "" + char1 + char2;
    }

    // Encrypt or decrypt the text
    private String processText(String text, boolean encrypt) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i += 2) {
            result.append(processDigraph(text.substring(i, i + 2), encrypt));
        }

        return result.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Lab_2_PlayFair_Cipher playfairCipher = new Lab_2_PlayFair_Cipher();

        System.out.println("Enter the key:");
        String key = scanner.nextLine();

        playfairCipher.generateKeyMatrix(key);

        System.out.println("Enter the plaintext:");
        String plaintext = scanner.nextLine();
        String preparedText = playfairCipher.prepareText(plaintext);

        String encryptedText = playfairCipher.processText(preparedText, true);
        System.out.println("Encrypted Text: " + encryptedText);

        String decryptedText = playfairCipher.processText(encryptedText, false);
        System.out.println("Decrypted Text: " + decryptedText);

        scanner.close();
    }
}

