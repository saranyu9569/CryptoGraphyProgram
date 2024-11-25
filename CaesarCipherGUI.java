import java.awt.*;
import javax.swing.*;

public class CaesarCipherGUI extends JFrame {
    private JTextArea inputTextArea;
    private JTextArea outputTextArea;
    private JComboBox<String> languageComboBox;
    private JTextField shiftField;
    private JButton encryptButton;
    private JButton decryptButton;

    public CaesarCipherGUI() {
        setTitle("Caesar Cipher");
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

        controlPanel.add(new JLabel("Shift Value:"));
        shiftField = new JTextField("3");
        controlPanel.add(shiftField);

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
        String input = inputTextArea.getText();
        String language = (String) languageComboBox.getSelectedItem();
        
        try {
            int shift = Integer.parseInt(shiftField.getText());
            if (!isEncrypt) shift = -shift;

            String result = language.equals("English") 
                ? caesarCipherEnglish(input, shift) 
                : caesarCipherThai(input, shift);
            
            outputTextArea.setText(result);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid shift value. Please enter a number.");
        }
    }

    private String caesarCipherEnglish(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                result.append((char) (((ch - base + shift + 26) % 26) + base));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    private String caesarCipherThai(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (ch >= 0x0E01 && ch <= 0x0E5B) { // Thai Unicode range
                result.append((char) (((ch - 0x0E01 + shift + 58) % 58) + 0x0E01));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }
}