class Node {
    char data;
    int frequency;
    Node left, right;

    Node(char data, int frequency) {
        this.data = data;
        this.frequency = frequency;
        this.left = this.right = null;
    }
}