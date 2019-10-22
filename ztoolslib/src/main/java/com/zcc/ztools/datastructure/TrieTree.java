package com.zcc.ztools.datastructure;

import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by cc on 2019-10-21.
 */
public class TrieTree {

    private Node mRoot = new Node('\n');

    public void insert(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        Node cur = mRoot;
        for (char c : str.toCharArray()) {
            if (cur.nexts.containsKey(c)) {
                cur.count++;
                cur = cur.nexts.get(c);
            } else {
                Node next = new Node(c);
                cur.nexts.put(c, next);
                cur = next;
            }
        }
    }

    public boolean contains(String str) {
        if (TextUtils.isEmpty(str)) {
            return true;
        }
        Node cur = mRoot;
        for (char c : str.toCharArray()) {
            if (cur.nexts.containsKey(c)) {
                cur = cur.nexts.get(c);
            } else {
                return false;
            }
        }
        return true;
    }


    public static class Node {
        private char val;
        private int count = 1;
        private HashMap<Character, Node> nexts = new HashMap<>();


        public Node(char val) {
            this.val = val;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public char getVal() {
            return val;
        }

        public void setVal(char val) {
            this.val = val;
        }

        public HashMap<Character, Node> getNexts() {
            return nexts;
        }

        public void setNexts(HashMap<Character, Node> nexts) {
            this.nexts = nexts;
        }
    }

}
