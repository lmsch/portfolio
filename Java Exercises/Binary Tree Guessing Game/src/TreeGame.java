/* TreeGame - a tree class that extends LinkedBinaryTree, a class from Data Stuctures and Algorithms in Java, written by Michael T. Goodrich,
Roberto Tamassia, and Michael H. Goldwasser. TreeGame has unique methods addQuestion, read, and write. Default file for saving and writing is save.txt.
 */

import examples.LinkedBinaryTree;
import examples.Position;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TreeGame extends LinkedBinaryTree<String> {
    // delimiting characters that are used to indicate whether a node is internal or external in a text file
    private final char indicateExt = 'e', indicateInt = 'i';

    // a tree game always has a question with two answers
    public TreeGame(String question, String yAnswer, String nAnswer) {
        super();
        addRoot(question);
        addLeft(root, yAnswer);
        addRight(root, nAnswer);
    }

    // adds a question to the tree to extend its intelligence
    void addQuestion(Position<String> p, String question, String answer) {
        String temp = p.getElement(); // save current node answer in temp
        set(p, question); // sets current node to user question
        addLeft(p, temp); // sets original answer to left node of question
        addRight(p, answer); // sets new answer to right node of question
    }

    // reads node data from a file
    void read(String name) throws IOException {
        Scanner fileScan = new Scanner(new File(name));
        root = preOrderRead(null, fileScan); // defines new tree by calling helper method
        fileScan.close();
    }

    // writes node data to a file
    void write(String name) throws IOException {
        FileWriter fw = new FileWriter(new File(name));
        PrintWriter pw = new PrintWriter(fw);
        preOrderWrite(root, pw); // calls helper to traverse tree
        pw.close();
    }

    private Node<String> preOrderRead(Node<String> parent, Scanner fileScan) {
        String s = fileScan.nextLine().trim(); // scans the next line of the text file
        Node<String> n;
        if (s.charAt(0) == indicateExt) { // lines are formatted as eAnswer or iQuestion, indicating internal or external
            n = new Node<>(s.substring(1), parent, null, null); // if external, returns a new node of the answer
            return n;
        } else {
            n = new Node<>(s.substring(1), parent, null, null); // if internal, creates a new node
            n.setLeft(preOrderRead(n, fileScan)); // sets its left and right to a recursive call, passing the node as a parent parameter
            n.setRight(preOrderRead(n, fileScan)); // note: each node has a pointer to its parent
            return n;
        }
    }

    private void preOrderWrite(Node<String> node, PrintWriter pw) {
        if (isExternal(node))
            pw.println(indicateExt + node.getElement()); // if the node is external, simply write it to the file with indicator 'e'
        else { // otherwise, write the node to the file with indicator 'i'
            pw.println(indicateInt + node.getElement());
            if (node.getLeft() != null)
                preOrderWrite(node.getLeft(), pw); // if node has a left child, traverse left child
            if (node.getRight() != null)
                preOrderWrite(node.getRight(), pw); // if node has a right child, traverse right child
        }
    }

    public char externalIndicator() {
        return indicateExt;
    }

    public char internalIndicator() {
        return indicateInt;
    }
}
