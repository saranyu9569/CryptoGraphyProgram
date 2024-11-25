import java.awt.*;
import javax.swing.*;

public class CipherMainApp extends JFrame {
    public CipherMainApp() {
        setTitle("Multi-Cipher Encryption Toolkit");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridLayout(4, 1, 10, 10));

        JButton caesarButton = new JButton("Caesar Cipher");
        JButton substitutionButton = new JButton("Substitution Cipher");
        JButton vigenereButton = new JButton("Vigenere Cipher");
        JButton playfairButton = new JButton("Playfair Cipher");


        caesarButton.addActionListener(e -> openCaesarCipher());
        substitutionButton.addActionListener(e -> openSubstitutionCipher());
        vigenereButton.addActionListener(e -> openVigenereCipher());
        playfairButton.addActionListener(e -> openPlayfairCipher());

        add(caesarButton);
        add(substitutionButton);
        add(vigenereButton);
        add(playfairButton);
    }

    private void openCaesarCipher() {
        CaesarCipherGUI caesarCipherGUI = new CaesarCipherGUI();
        caesarCipherGUI.setLocationRelativeTo(null); 
        caesarCipherGUI.setVisible(true);
    }
    
    private void openSubstitutionCipher() {
        SubstitutionCipherGUI substitutionCipherGUI = new SubstitutionCipherGUI();
        substitutionCipherGUI.setLocationRelativeTo(null);
        substitutionCipherGUI.setVisible(true);
    }
    
    private void openVigenereCipher() {
        VigenereCipherGUI vigenereCipherGUI = new VigenereCipherGUI();
        vigenereCipherGUI.setLocationRelativeTo(null);
        vigenereCipherGUI.setVisible(true);
    }
    
    private void openPlayfairCipher() {
        PlayfairCipherGUI playfairCipherGUI = new PlayfairCipherGUI();
        playfairCipherGUI.setLocationRelativeTo(null);
        playfairCipherGUI.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CipherMainApp cipherApp = new CipherMainApp();
            cipherApp.setLocationRelativeTo(null); 
            cipherApp.setVisible(true);
        });
    }
    
}