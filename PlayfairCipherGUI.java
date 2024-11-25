import java.awt.*;
import java.util.*;
import javax.swing.*;

public class PlayfairCipherGUI extends JFrame {
    private JTextArea inputTextArea;
    private JTextArea outputTextArea;
    private JTextField keyField;
    private JButton encryptButton;
    private JButton decryptButton;

    public PlayfairCipherGUI() {
        setTitle("Playfair Cipher");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
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

        JPanel controlPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        
        controlPanel.add(new JLabel("Playfair Key:"));
        keyField = new JTextField();
        controlPanel.add(keyField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
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
        String input = inputTextArea.getText().toUpperCase().replaceAll("[^A-Z]", "");
        String key = keyField.getText().toUpperCase().replaceAll("[^A-Z]", "");

        if (key.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Playfair key.");
            return;
        }

        try {
            char[][] keyMatrix = generateKeyMatrix(key);
            String result = playfairCipher(input, keyMatrix, isEncrypt);
            outputTextArea.setText(result);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error processing text: " + e.getMessage());
        }
    }

    private char[][] generateKeyMatrix(String key) {
        key = key.replace("J", "I");
        Set<Character> uniqueChars = new LinkedHashSet<>();
        for (char c : key.toCharArray()) {
            uniqueChars.add(c);
        }

        for (char c = 'A'; c <= 'Z'; c++) {
            if (c != 'J') uniqueChars.add(c);
        }

        char[][] matrix = new char[5][5];
        Iterator<Character> iter = uniqueChars.iterator();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = iter.next();
            }
        }
        return matrix;
    }

    private String playfairCipher(String text, char[][] keyMatrix, boolean isEncrypt) {
        text = prepareText(text);
        
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i += 2) {
            char a = text.charAt(i);
            char b = text.charAt(i + 1);
            
            int[] posA = findPosition(a, keyMatrix);
            int[] posB = findPosition(b, keyMatrix);
            
            if (posA[0] == posB[0]) { 
                int shift = isEncrypt ? 1 : -1;
                result.append(keyMatrix[posA[0]][(posA[1] + shift + 5) % 5]);
                result.append(keyMatrix[posB[0]][(posB[1] + shift + 5) % 5]);
            } else if (posA[1] == posB[1]) {  
                int shift = isEncrypt ? 1 : -1;
                result.append(keyMatrix[(posA[0] + shift + 5) % 5][posA[1]]);
                result.append(keyMatrix[(posB[0] + shift + 5) % 5][posB[1]]);
            } else { 
                result.append(keyMatrix[posA[0]][posB[1]]);
                result.append(keyMatrix[posB[0]][posA[1]]);
            }
        }
        return result.toString();
    }

    private String prepareText(String text) {
        text = text.replace("J", "I");
        StringBuilder prepared = new StringBuilder();
        
        for (int i = 0; i < text.length(); i += 2) {
            char first = text.charAt(i);
            
            if (i + 1 >= text.length()) {
                prepared.append(first).append('X');
            } else {
                char second = text.charAt(i + 1);
                
                if (first == second) {
                    prepared.append(first).append('X');
                    i--;
                } else {
                    prepared.append(first).append(second);
                }
            }
        }
        
        if (prepared.length() % 2 != 0) {
            prepared.append('X');
        }
        
        return prepared.toString();
    }

    private int[] findPosition(char c, char[][] matrix) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == c) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalArgumentException("Character not found in matrix");
    }
}