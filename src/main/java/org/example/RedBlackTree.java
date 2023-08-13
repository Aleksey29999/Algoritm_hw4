package org.example;

import java.util.ArrayList;
import java.util.List;

public class RedBlackTree<V extends Comparable<V>> {
    private Node root;

    private class Node {
        private Color color;
        private V value;
        private Node leftChild;
        private Node rightChild;

        @Override
        public String toString() {
            return "Node{" +
                    "color=" + color +
                    ", value=" + value +
                    ", leftChild=" + leftChild +
                    ", rightChild=" + rightChild +
                    '}';
        }
    }

    private enum Color {
        RED, BLACK
    }

    public boolean contains(V value) {
        Node node = root;
        while (node != null) {
            if (node.value.equals(value)) {
                return true;
            }
            if (node.value.compareTo(value) > 0) {
                node = node.leftChild;
            } else {
                node = node.rightChild;
            }
        }
        return false;
    }

    public boolean containsRec(V value) {
        if (root == null) {
            return false;
        }
        return containsRec(root, value);
    }

    private boolean containsRec(Node node, V value) {
        if (node.value.equals(value)) {
            return true;
        } else {
            if (node.value.compareTo(value) > 0) {
                return containsRec(node.leftChild, value);
            } else {
                return containsRec(node.rightChild, value);
            }
        }
    }

    public boolean add(V value) {

        if (root == null) {
            root = new Node();
            root.color = Color.BLACK;
            root.value = value;
//            boolean result = addNode(root, value);
        }
        return addNode(root, value);

    }

    private boolean addNode(Node node, V value) {
        if (node.value.compareTo(value) == 0)
            return false;
        if (node.value.compareTo(value) > 0) {
            if (node.leftChild != null) {
                boolean result = addNode(node.leftChild, value);
                node.leftChild = rebalance(node.leftChild);
                return result;
            } else {
                node.leftChild = new Node();
                node.leftChild.color = Color.RED;
                node.leftChild.value = value;
                return true;
            }
        } else {
            if (node.rightChild != null) {
                boolean result = addNode(node.rightChild, value);
                node.rightChild = rebalance(node.rightChild);
                return result;
            } else {
                node.rightChild = new Node();
                node.rightChild.color = Color.RED;
                node.rightChild.value = value;
                return true;
            }
        }
    }


    private void colorSwap(Node node) {
        node.rightChild.color = Color.BLACK;
        node.leftChild.color = Color.BLACK;
        node.color = Color.RED;
    }

    private Node leftSwap(Node node) {
        Node leftChild = node.leftChild;
        Node BetwinChild = leftChild.rightChild;
        leftChild.rightChild = node;
        node.leftChild = BetwinChild;
        leftChild.color = node.color;
        node.color = Color.RED;
        return leftChild;
    }

    private Node rightSwap(Node node) {
        Node rightChild = node.rightChild;
        Node BetwinChild = rightChild.leftChild;
        rightChild.leftChild = node;
        node.rightChild = BetwinChild;
        rightChild.color = node.color;
        node.color = Color.RED;
        return rightChild;
    }

    private Node rebalance(Node node) {
        Node result = node;
        boolean needRebalance;
        do {
            needRebalance = false;
            if (result.rightChild != null && result.rightChild.color == Color.RED && (
                    result.leftChild == null || result.leftChild.color == Color.BLACK)) {
                needRebalance = true;
                result = rightSwap(result);
            }
            if (result.leftChild != null && result.leftChild.color == Color.RED &&
                    result.leftChild.leftChild != null && result.leftChild.leftChild.color == Color.RED) {
                needRebalance = true;
                result = leftSwap(result);
            }
            if (result.leftChild != null && result.leftChild.color == Color.RED &&
                    result.rightChild != null && result.rightChild.color == Color.RED) {
                needRebalance = true;
                colorSwap(result);
            }
        }
        while (needRebalance);
        return result;
    }

    public void inorder() {
        inorder(root);
    }

    private void inorder(Node node) {
        if (node != null) {
            inorder(node.leftChild);
            System.out.print(node.value + " " + node.color + " ; ");
            inorder(node.rightChild);
        }
    }

    public static void main(String[] args) {
        RedBlackTree node = new RedBlackTree();

        node.add(10);
        node.inorder();
        System.out.println("\n");
        node.add(20);
        node.inorder();
        System.out.println("\n");
        node.add(30);
        node.inorder();
        System.out.println("\n");
        node.add(23);
        node.inorder();
        System.out.println("\n");
        node.add(43);
        node.inorder();
        System.out.println("\n");
        node.add(4);
        node.inorder();
        System.out.println("\n");
        node.add(44);
        node.inorder();
        System.out.println("\n");
        node.add(43);
        node.inorder();


    }
}
