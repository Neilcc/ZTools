package com.zcc.ztools;

import com.zcc.ztools.datastructure.TrieTree;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by cc on 2019-10-22.
 */
@RunWith(JUnit4.class)
public class UnitTest {

    @Test
    public void testTrie() {
        TrieTree trieTree = new TrieTree();
        trieTree.insert("asdavasdgasdt");
        trieTree.insert("sadddsaa");
        trieTree.insert("sadd");
        trieTree.insert("aaassd");
        trieTree.insert("asdavas");
        Assert.assertTrue(trieTree.contains("aaass"));
    }


}
