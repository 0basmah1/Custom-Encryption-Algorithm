# Custom Encryption Algorithm (Java)

This project implements a secure data transmission flow using multiple layers of transformation.

## How it Works
The algorithm follows these specific steps to secure data:
1. **Hashing:** Uses **SHA-256** to create an integrity check.
2. **Binary Conversion:** Converts plaintext into a binary string.
3. **Bitwise Operations:**
   - **Bitwise Complement:** Flips the bits.
   - **3-Bit Rotation:** Shifts bits for added complexity.
   - **XOR Operation:** Applies a final layer of encryption.

## Technologies
- Java JDK 11+
- MessageDigest (for SHA-256)
