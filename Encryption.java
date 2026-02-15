package it311p;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class IT311P{

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("\u001B[47m"+"Welcome! we are glad that you are using our algorith <3: ");//The \u001B[47m" just to color the output


        System.out.print("Enter the message that you want encrypt please: ");
        String Emessage = input.nextLine();

        System.out.print("Enter the key please: ");
        String Ekey = input.nextLine();

        String d = encryption(Emessage, Ekey);

        System.out.print("Enter the cipher please: ");
        String Dmessage = input.nextLine();

        System.out.print("Enter the same key please: ");
        String Dkey = input.nextLine();

        decryption(Dmessage, Dkey, d);
    }

    //Hashing using SHA-256
    public static String hashSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    public static String encryption(String massage, String key) {
        String binaryMassage = convertToBinary(massage);
        String binarykey = convertToBinary(key);
        String digest = hashSHA256(binaryMassage);
        System.out.println("The digest is: " + digest);
        String massageComplement = binaryComplement(binaryMassage);
        String LSH = leftRotation(massageComplement);
        String cipher = xorWithKey(LSH, binarykey);
        System.out.println("The massage after encryption is : " + cipher);
        return digest;

    }

    public static void decryption(String cipher, String key, String oldDigest) {

        String binarykey = convertToBinary(key);
        String xor = xorWithKey(cipher, binarykey);
        String RSH = rightRotation(xor);
        String plaintext = binaryComplement(RSH);
        String St = convertToCharacter(plaintext);
        System.out.println("The massage is: " + St);

        String newdigest = hashSHA256(plaintext);
        System.out.println("The digest : " + newdigest);

        if (newdigest.equals(oldDigest)) {
            System.out.println("\u001B[47m"+"Congrats!! the encryption process went successfully");

        } else {
            System.out.println("\u001B[41m"+"error,you should repeat the process ");

        }

    }

    //Method to convert a string to binary 
    public static String convertToBinary(String input) {
        String binaryString = ""; // to store the final result
        // traverse every letter 
        for (int i = 0; i < input.length(); i++) {
            char character = input.charAt(i); // get the current letter
            int ascii = (int) character;
            String binaryChar = ""; // to store the binary form for every letter

            // convert to binary
            while (ascii > 0) {

                binaryChar = (ascii % 2) + binaryChar;
                ascii = ascii / 2;
            }

            while (binaryChar.length() < 8) {
                binaryChar = "0" + binaryChar;
            }

            binaryString += binaryChar;
        }

        return binaryString;
    }

    // Method to convert a binary string back to a regular string
    public static String convertToCharacter(String binary) {
        String result = "";
        for (int i = 0; i < binary.length(); i += 8) {
            if (i + 8 > binary.length()) {
                break;
            }
            String byteString = binary.substring(i, i + 8);
            int charCode = Integer.parseInt(byteString, 2);
            result += (char) charCode;
        }

        return result;
    }

    // Method to compute the complement of a binary string
    public static String binaryComplement(String binary) {
        String result = "";//for storing the result
        // traverse every bit
        for (int i = 0; i < binary.length(); i++) {
            char bit = binary.charAt(i); //to get the current bit

            if (bit == '0') {
                result += '1';
            } else {
                result += '0';
            }
        }

        return result;
    }
    
// Method to perform a 3 left rotations on a binary string (for encryption)
  public static String leftRotation(String binary) {
    String result = ""; 
    for (int i = 0; i < binary.length(); i += 8) {
        String binaryChar = binary.substring(i, Math.min(i + 8, binary.length()));
        String shiftedChar = binaryChar.substring(3) + binaryChar.substring(0, 3);
                result += shiftedChar;
    }
    return result;
}


// Method to perform a 3 right rotations on a binary string (for decryption)
    public static String rightRotation(String binaryInput) {
        String result = "";

        for (int i = 0; i < binaryInput.length(); i += 8) {
            String binaryChar = binaryInput.substring(i, Math.min(i + 8, binaryInput.length()));
            String shiftedChar = binaryChar.substring(binaryChar.length() - 3) + binaryChar.substring(0, binaryChar.length() - 3);
            result += shiftedChar;
        }
        return result;
    }

//Method to preform xor between the binary massage and binary key
    public static String xorWithKey(String Message, String Key) {
        String result = "";

        String extendedKey = Key;
        while (extendedKey.length() < Message.length()) {
            extendedKey += Key;
        }
        for (int i = 0; i < Message.length(); i++) {
            char bit1 = Message.charAt(i);
            char bit2 = extendedKey.charAt(i);
            if (bit1 == bit2) {
                result += '0';
            } else {
                result += '1';
            }
        }

        return result;
    }


}

