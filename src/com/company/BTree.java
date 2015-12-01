package com.company;

import java.util.NoSuchElementException;
import java.util.Optional;

public class BTree<T1 extends Comparable<T1>, T2> {

   /* <T extends Comparable<T>> simply means that T has to be of type Comparable at least.
    However, Comparable is also Generic, so you have to specify the type of Comparable as well, which is with the type T.
*/

    static class Node<T1, T2> {
        T1 key;
        T2 value;
        Node<T1, T2> left, right;

        Node(T1 key, T2 value) {
            this.key = key;
            this.value = value;
        }
    }

    public Node<T1, T2> root = null;

    private int length = 1;

    private int height() {
        return length;
    }

    public boolean containsKey(T1 key) {
        Node<T1, T2> x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp == 0) {
                return true;
            }
            if (cmp < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        return false;
    }

    public Optional<T2> get(T1 key) {
        Optional<T2> optional = Optional.empty();
        Node<T1, T2> x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp == 0) {
                optional = Optional.of(x.value);   // of() Returns an Optional with the specified present non-null value.
                return optional;
            }
            if (cmp < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        optional.orElseThrow(NoSuchElementException::new);
        return optional;

    }

    public void add(T1 key, T2 value) {
        Node<T1, T2> current = root, parent = null;
        int i = 0;
        while (current != null) {
            int cmp = key.compareTo(current.key);
            if (cmp == 0) {
                current.value = value;
                return;
            } else {
                parent = current;
                if (cmp < 0) {
                    current = current.left;
                } else {
                    current = current.right;
                }
            }
            i++;
        }
        Node<T1, T2> newNode = new Node<T1, T2>(key, value);
        if (parent == null) {
            root = newNode;
        } else {
            if (key.compareTo(parent.key) < 0) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }
        }
        if (i >= length) {
            length++;
        }
    }
    public void show(Node<T1, T2> x) {
        if (x!= null) {
            System.out.print(x.key + " (");
        }
        else {
            System.out.print("null");
            return;
        }
        show(x.left);
        System.out.print(", ");
        show(x.right);
        System.out.print(")");
    }


    public void remove(T1 key) throws RemoveException {
        if (height() == 0) {
            new RemoveException("Tree is empty");
        } else {
            Node<T1, T2> elToRemove = root, parent = null;
            while (elToRemove != null) {
                int cmp = key.compareTo(elToRemove.key);
                if (cmp == 0) {
                    break;
                } else {
                    parent = elToRemove;
                    if (cmp < 0) {
                        elToRemove = elToRemove.left;
                    } else {
                        elToRemove = elToRemove.right;
                    }
                }
            }
            if (elToRemove == null) {
                return;
            }
            if (elToRemove.right == null) {
                if (parent == null) {
                    root = elToRemove.left;
                } else {
                    if (elToRemove == parent.left) {
                        parent.left = elToRemove.left;
                    } else {
                        parent.right = elToRemove.left;
                    }
                }
            } else {
                Node<T1, T2> leftMost = elToRemove.right;
                Node<T1, T2> rightChild = null;
                while (leftMost.left != null) {
                    rightChild = leftMost;
                    leftMost = leftMost.left;
                }
                if (rightChild != null) {
                    rightChild.left = leftMost.right;
                } else {
                    elToRemove.right = leftMost.right;
                }
                elToRemove.key = leftMost.key;
                elToRemove.value = leftMost.value;
            }
        }
    }
}