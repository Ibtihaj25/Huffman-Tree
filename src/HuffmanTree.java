import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class HuffmanTree {
    private Node root;
    private Map<Character, String> encodingMap;

    // Build the Huffman Tree
    public Node buildTree(char[] chars, int[] freq) {
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.frequency));

        // Create a leaf node for each character
        for (int i = 0; i < chars.length; i++) {
            queue.add(new Node(chars[i], freq[i]));
        }

        // Build the tree
        while (queue.size() > 1) {
            Node left = queue.poll();
            Node right = queue.poll();

            Node parent = new Node('\0', left.frequency + right.frequency);
            parent.left = left;
            parent.right = right;

            queue.add(parent);
        }

        root = queue.poll(); // Root of the tree
        encodingMap = new HashMap<>();
        generateEncodingMap(root, "");
        return root;
    }

    // Generate the encoding map
    private void generateEncodingMap(Node node, String code) {
        if (node == null) return;
        if (node.data != '\0') encodingMap.put(node.data, code);

        generateEncodingMap(node.left, code + "0");
        generateEncodingMap(node.right, code + "1");
    }

    // Get the encoding map
    public Map<Character, String> getEncodingMap() {
        return encodingMap;
    }

    // Encode a message
    public String encode(String message) {
        StringBuilder encoded = new StringBuilder();
        for (char c : message.toCharArray()) {
            encoded.append(encodingMap.get(c));
        }
        return encoded.toString();
    }

    // Decode a message
    public String decode(String encodedMessage) {
        StringBuilder decoded = new StringBuilder();
        Node current = root;
        for (char bit : encodedMessage.toCharArray()) {
            current = (bit == '0') ? current.left : current.right;

            if (current.left == null && current.right == null) {
                decoded.append(current.data);
                current = root;
            }
        }
        return decoded.toString();
    }
}