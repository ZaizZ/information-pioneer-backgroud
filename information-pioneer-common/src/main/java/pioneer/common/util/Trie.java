package pioneer.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
        root.var = ' ';
    }

    /**
     * 插入trie树
     *
     * @param word
     */
    public void insert(String word) {
        TrieNode ws = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (!ws.children.keySet().contains(c)) {
                ws.children.put(c, new TrieNode(c));
            }
            ws = ws.children.get(c);
        }
        ws.isWord = true;
    }

    /**
     * 查询以关键字开头的数据
     * @param prefix
     * @return
     */
    public List<String> startWith(String prefix) {
        List<String> match = new ArrayList<>();
        TrieNode ws = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (!ws.children.keySet().contains(c)) {
                return match;
            }
            ws = ws.children.get(c);
        }
        List<String> data = getData("", ws);
        for (String s : data) {
            match.add(prefix + s);
        }
        return match;
    }

    public List<String> getData(String prefix, TrieNode trieNode) {
        List<String> list = new ArrayList<>();
        if (trieNode.children == null || trieNode.children.size() == 0) {
            // list.add(prefix + trieNode.var);
            list.add(prefix);
            return list;
        }
        if (trieNode.isWord) {
            list.add(prefix);
            // list.add(prefix + trieNode.var);
        }
        for (Map.Entry<Character, TrieNode> nodeEntry : trieNode.children.entrySet()) {
            TrieNode node = nodeEntry.getValue();
            List<String> data = getData(nodeEntry.getKey()+"", node);
            for (String s : data) {
                list.add(prefix + s);
            }
        }
        return list;
    }

}
