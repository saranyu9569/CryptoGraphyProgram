import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class VigenereCipherGUI extends JFrame {
    private JTextArea inputTextArea;
    private JTextArea outputTextArea;
    private JTextField keyField;
    private JButton encryptButton;
    private JButton decryptButton;
    private JPanel buttonPanel;

    public VigenereCipherGUI() {
        setTitle("Vigenere Cipher");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        addGenerateKeyButton();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputTextArea = new JTextArea(10, 40);
        inputTextArea.setLineWrap(true);
        inputTextArea.setWrapStyleWord(true);
        JScrollPane inputScrollPane = new JScrollPane(inputTextArea);
        inputPanel.add(new JLabel("Input Text:"), BorderLayout.NORTH);
        inputPanel.add(inputScrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        
        controlPanel.add(new JLabel("Vigenere Key:"));
        keyField = new JTextField();
        controlPanel.add(keyField);

        buttonPanel = new JPanel(new FlowLayout());
        encryptButton = new JButton("Encrypt");
        decryptButton = new JButton("Decrypt");
        buttonPanel.add(encryptButton);
        buttonPanel.add(decryptButton);

        JPanel outputPanel = new JPanel(new BorderLayout());
        outputTextArea = new JTextArea(10, 40);
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        outputTextArea.setEditable(false);
        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);
        outputPanel.add(new JLabel("Output Text:"), BorderLayout.NORTH);
        outputPanel.add(outputScrollPane, BorderLayout.CENTER);

        setupListeners();

        add(inputPanel, BorderLayout.NORTH);
        add(controlPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.WEST);
        add(outputPanel, BorderLayout.SOUTH);
    }

    private void setupListeners() {
        encryptButton.addActionListener(e -> processText(true));
        decryptButton.addActionListener(e -> processText(false));
    }

    private void processText(boolean isEncrypt) {
        String input = inputTextArea.getText();
        String key = keyField.getText().trim();

        if (key.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Vigenere key.");
            return;
        }

        try {
            String result = vigenereCipherEnglish(input, key, isEncrypt);
            outputTextArea.setText(result);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error processing text: " + e.getMessage());
        }
    }

    private String vigenereCipherEnglish(String text, String key, boolean isEncrypt) {
        StringBuilder result = new StringBuilder();
        key = key.toUpperCase();
        int keyLength = key.length();

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                int keyShift = key.charAt(i % keyLength) - 'A';
                
                if (!isEncrypt) keyShift = -keyShift;

                char shifted = (char) (((ch - base + keyShift + 26) % 26) + base);
                result.append(shifted);
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    private String generateRandomKey(int length) {
        StringBuilder key = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            char randomChar = (char) (random.nextInt(26) + 'A');
            key.append(randomChar);
        }

        return key.toString();
    }

    private void addGenerateKeyButton() {
        JButton generateKeyButton = new JButton("Generate Key");
        generateKeyButton.addActionListener(e -> {
            int keyLength = 10;
            
            try {
                String generatedKey = generateRandomKey(keyLength);
                keyField.setText(generatedKey);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error generating key: " + ex.getMessage());
            }
        });
        
        buttonPanel.add(generateKeyButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VigenereCipherGUI().setVisible(true);
        });
    }
}