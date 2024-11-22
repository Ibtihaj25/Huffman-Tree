import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class HuffmanGameGUI {
    private JFrame frame;
    private JPanel mainPanel;
    private JTextArea treeDisplay;
    private JTextField encodeInput, decodeInput, resultOutput;
    private HuffmanTree huffmanTree;

    public HuffmanGameGUI() {
        // Sample characters and frequencies
        char[] chars = {'a', 'b', 'c', 'd', 'e'};
        int[] freq = {5, 9, 12, 13, 16};

        // Initialize HuffmanTree
        huffmanTree = new HuffmanTree();
        huffmanTree.buildTree(chars, freq);

        // Setup the GUI
        frame = new JFrame("Huffman Encoding Challenge");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        mainPanel = new JPanel(new CardLayout());

        setupWelcomeScreen();
        setupTreeDisplayScreen();
        setupEncodingScreen();
        setupDecodingScreen();

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void setupWelcomeScreen() {
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BorderLayout());

        JLabel title = new JLabel("Huffman Encoding Challenge", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> showCard("TreeDisplay"));

        welcomePanel.add(title, BorderLayout.CENTER);
        welcomePanel.add(startButton, BorderLayout.SOUTH);

        mainPanel.add(welcomePanel, "Welcome");
    }

    private void setupTreeDisplayScreen() {
        JPanel treePanel = new JPanel();
        treePanel.setLayout(new BorderLayout());

        JLabel header = new JLabel("Huffman Tree", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 18));

        treeDisplay = new JTextArea();
        treeDisplay.setEditable(false);
        displayTree();

        JButton nextButton = new JButton("Next: Encoding");
        nextButton.addActionListener(e -> showCard("Encoding"));

        treePanel.add(header, BorderLayout.NORTH);
        treePanel.add(new JScrollPane(treeDisplay), BorderLayout.CENTER);
        treePanel.add(nextButton, BorderLayout.SOUTH);

        mainPanel.add(treePanel, "TreeDisplay");
    }

    private void setupEncodingScreen() {
        JPanel encodePanel = new JPanel();
        encodePanel.setLayout(new BorderLayout());

        JLabel header = new JLabel("Encoding Challenge", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 18));

        encodeInput = new JTextField();
        JButton encodeButton = new JButton("Encode");
        resultOutput = new JTextField();
        resultOutput.setEditable(false);

        encodeButton.addActionListener(e -> {
            String input = encodeInput.getText().trim();
            String encoded = huffmanTree.encode(input);
            resultOutput.setText(encoded);
        });

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Enter a word [a, b, c, d, e]:"));
        inputPanel.add(encodeInput);
        inputPanel.add(new JLabel("Encoded Output:"));
        inputPanel.add(resultOutput);

        JButton nextButton = new JButton("Next: Decoding");
        nextButton.addActionListener(e -> showCard("Decoding"));

        encodePanel.add(header, BorderLayout.NORTH);
        encodePanel.add(inputPanel, BorderLayout.CENTER);
        encodePanel.add(nextButton, BorderLayout.SOUTH);

        mainPanel.add(encodePanel, "Encoding");
    }

    private void setupDecodingScreen() {
        JPanel decodePanel = new JPanel();
        decodePanel.setLayout(new BorderLayout());

        JLabel header = new JLabel("Decoding Challenge", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 18));

        decodeInput = new JTextField();
        JButton decodeButton = new JButton("Decode");
        JTextField decodedOutput = new JTextField();
        decodedOutput.setEditable(false);

        decodeButton.addActionListener(e -> {
            String input = decodeInput.getText().trim();
            String decoded = huffmanTree.decode(input);
            decodedOutput.setText(decoded);
        });

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Enter a binary string:"));
        inputPanel.add(decodeInput);
        inputPanel.add(new JLabel("Decoded Message:"));
        inputPanel.add(decodedOutput);

        JButton finishButton = new JButton("Finish");
        finishButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Thanks for playing!"));

        decodePanel.add(header, BorderLayout.NORTH);
        decodePanel.add(inputPanel, BorderLayout.CENTER);
        decodePanel.add(finishButton, BorderLayout.SOUTH);

        mainPanel.add(decodePanel, "Decoding");
    }

    private void displayTree() {
        StringBuilder treeText = new StringBuilder("Character Encodings:\n");
        Map<Character, String> encodingMap = huffmanTree.getEncodingMap();
        for (Map.Entry<Character, String> entry : encodingMap.entrySet()) {
            treeText.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        treeDisplay.setText(treeText.toString());
    }

    private void showCard(String cardName) {
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        cl.show(mainPanel, cardName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HuffmanGameGUI::new);
    }
}