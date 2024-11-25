import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.swing.*;

public class SubstitutionCipherGUI extends JFrame {
    private JTextArea inputTextArea;
    private JTextArea outputTextArea;
    private JComboBox<String> languageComboBox;
    private JTextField substitutionKeyField;
    private JButton encryptButton;
    private JButton decryptButton;

    public SubstitutionCipherGUI() {
        setTitle("Substitution Cipher");
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
        
        controlPanel.add(new JLabel("Language:"));
        languageComboBox = new JComboBox<>(new String[]{"English", "Thai"});
        controlPanel.add(languageComboBox);

        controlPanel.add(new JLabel("Substitution Key:"));
        substitutionKeyField = new JTextField();
        controlPanel.add(substitutionKeyField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        encryptButton = new JButton("Encrypt");
        decryptButton = new JButton("Decrypt");
        JButton generateKeyButton = new JButton("Generate Random Key");
        buttonPanel.add(encryptButton);
        buttonPanel.add(decryptButton);
        buttonPanel.add(generateKeyButton);

        JPanel outputPanel = new JPanel(new BorderLayout());
        outputTextArea = new JTextArea(10, 40);
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        outputTextArea.setEditable(false);
        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);
        outputPanel.add(new JLabel("Output Text:"), BorderLayout.NORTH);
        outputPanel.add(outputScrollPane, BorderLayout.CENTER);

        setupListeners(generateKeyButton);

        add(inputPanel, BorderLayout.NORTH);
        add(controlPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.WEST);
        add(outputPanel, BorderLayout.SOUTH);
    }

    private void setupListeners(JButton generateKeyButton) {
        encryptButton.addActionListener(e -> processText(true));
        decryptButton.addActionListener(e -> processText(false));
        generateKeyButton.addActionListener(e -> generateRandomKey());
    }

    private void generateRandomKey() {
        String language = (String) languageComboBox.getSelectedItem();
        String randomKey;

        if ("English".equals(language)) {
            randomKey = generateEnglishRandomKey();
        } else {
            randomKey = generateThaiRandomKey();
        }

        substitutionKeyField.setText(randomKey);
    }

    private String generateEnglishRandomKey() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return shuffleString(alphabet);
    }

    private String generateThaiRandomKey() {
        String thaiAlphabet = "กขฃคฅฆงจฉชซฌญฎฏฐฑฒณดตถทธนบปผฝพฟภมยรลวศษสหฬอฮ";
        return shuffleString(thaiAlphabet);
    }

    private String shuffleString(String input) {
        char[] characters = input.toCharArray();
        Random random = new Random();
        
        for (int i = characters.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            char temp = characters[index];
            characters[index] = characters[i];
            characters[i] = temp;
        }
        
        return new String(characters);
    }

    private void processText(boolean isEncrypt) {
        String input = inputTextArea.getText();
        String language = (String) languageComboBox.getSelectedItem();
        String substitutionKey = substitutionKeyField.getText().trim();

        if (substitutionKey.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a substitution key.");
            return;
        }

        try {
            String result = language.equals("English") 
                ? substitutionCipherEnglish(input, substitutionKey, isEncrypt)
                : substitutionCipherThai(input, substitutionKey, isEncrypt);
            
            outputTextArea.setText(result);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error processing text: " + e.getMessage());
        }
    }

    private String substitutionCipherEnglish(String text, String key, boolean isEncrypt) {
        Map<Character, Character> substitutionMap = new HashMap<>();
        Map<Character, Character> reverseMap = new HashMap<>();
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        
        if (key.length() != 26) {
            throw new IllegalArgumentException("Substitution key must be 26 characters long for English");
        }
    
        for (int i = 0; i < 26; i++) {
            substitutionMap.put(alphabet.charAt(i), key.toUpperCase().charAt(i));
            reverseMap.put(key.toUpperCase().charAt(i), alphabet.charAt(i));
        }
    
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch)) {
                char upperCh = Character.toUpperCase(ch);
                Map<Character, Character> currentMap = isEncrypt ? substitutionMap : reverseMap;
                char mappedChar = currentMap.getOrDefault(upperCh, upperCh);
                result.append(Character.isUpperCase(ch) ? mappedChar : Character.toLowerCase(mappedChar));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    private String substitutionCipherThai(String text, String key, boolean isEncrypt) {
        String thaiAlphabet = "กขฃคฅฆงจฉชซฌญฎฏฐฑฒณดตถทธนบปผฝพฟภมยรลวศษสหฬอฮ";
        
        if (key.length() != thaiAlphabet.length()) {
            throw new IllegalArgumentException("Substitution key must be " + thaiAlphabet.length() + " characters long for Thai");
        }
    
        Map<Character, Character> substitutionMap = new HashMap<>();
        Map<Character, Character> reverseMap = new HashMap<>();
        for (int i = 0; i < thaiAlphabet.length(); i++) {
            substitutionMap.put(thaiAlphabet.charAt(i), key.charAt(i));
            reverseMap.put(key.charAt(i), thaiAlphabet.charAt(i));
        }
    
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            Map<Character, Character> currentMap = isEncrypt ? substitutionMap : reverseMap;
            char mappedChar = currentMap.getOrDefault(ch, ch);
            result.append(mappedChar);
        }
    
        return result.toString();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SubstitutionCipherGUI().setVisible(true);
        });
    }
}