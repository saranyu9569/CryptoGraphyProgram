# Cryptographer

"Cryptographer" is a Java-based project designed to learn and implement various encryption and decryption techniques, including Caesar Cipher, Playfair Cipher, Substitution Cipher, and Vigenère Cipher. The project provides a graphical user interface (GUI) to make encryption and decryption easier for both Thai and English languages. It also includes key generation functionality to simplify the process of encryption and decryption. This project is part of a Cybersecurity course.

## Features

- **Caesar Cipher**: Encrypts and decrypts messages by shifting the alphabet.
- **Playfair Cipher**: A more complex cipher that works with digraphs (pairs of letters).
- **Substitution Cipher**: Each letter in the plaintext is replaced with a letter from a shuffled alphabet.
- **Vigenère Cipher**: A polyalphabetic cipher that uses a keyword to encrypt or decrypt a message.
- **Key Generation**: Automatically generates keys for each cipher, simplifying the encryption/decryption process.
- **Language Support**: Supports both Thai and English text for encryption and decryption.
- **GUI Interface**: Easy-to-use interface for users to input text, choose ciphers, and view the results.

## Installation

To run this project, ensure you have Java 8 or higher installed. You can download and install Java from [here](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).

Clone the repository to your local machine:
git clone https://github.com/yourusername/cryptographer.git

Navigate to the project directory:
cd cryptographer

Compile and run the project:
javac Main.java java Main

## How to Use

1. **Choose Cipher**: Select the encryption/decryption cipher you wish to use (Caesar Cipher, Playfair, Substitution, or Vigenère).
2. **Enter Text**: Input the text you want to encrypt or decrypt. Both Thai and English languages are supported.
3. **Generate Key**: You can generate a random key for each cipher to use in the process.
4. **Encrypt/Decrypt**: Click the appropriate button to encrypt or decrypt the text using the selected cipher and key.
5. **View Results**: The encrypted or decrypted text will be displayed in the result field.

## Technologies Used

- **Java**: The main programming language for the project.
- **Java GUI (Swing)**: Used for building the graphical user interface.
- **Cipher Algorithms**: Caesar Cipher, Playfair Cipher, Substitution Cipher, Vigenère Cipher.
- **Encryption/Decryption**: Implements standard encryption and decryption logic for each cipher.

## Contributing

Feel free to fork this project and submit your improvements, bug fixes, or new features. You can contribute by:

- Reporting issues or bugs
- Adding new ciphers or optimizations
- Improving the user interface

Please create a pull request for any contributions.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.