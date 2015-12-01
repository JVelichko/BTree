package com.company;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        BTree<Integer, Integer> tree = new BTree<>();
        tree.add(5,1);
        tree.add(7,2);
        tree.add(6,3);
        tree.add(10,4);
        Optional<Integer> value = tree.get(6);
        System.out.println(value);
        System.out.println(tree.root.key);
        tree.show(tree.root);
    }
}