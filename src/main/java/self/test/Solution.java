package self.test;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public int longestSubstring(String s, int k) {
        return search(s, 0, s.length(), k);
    }
    private int search(String s, int start, int end, int k) {
        if (end - start < k) {
            return 0;
        } else {
            int[] counts = new int[26];
            for (int i = start; i < end; i++) {
                counts[s.charAt(i) - 'a']++;
            }
            boolean needBreak = false;
            char breakPoint = '\0';
            for (int i = 0; i < 26; i++) {
                if (counts[i] != 0 && counts[i] < k) {
                    needBreak = true;
                    breakPoint = (char)(i + 'a');
                    break;
                }
            }
            int ans = end - start;
            if (needBreak) {
                int breakIndex = s.indexOf(breakPoint, start);
                ans = 0;
                while (breakIndex != -1) {
                    ans = Math.max(ans, search(s, start, breakIndex, k));
                    start = breakIndex + 1;
                    breakIndex = s.indexOf(breakPoint, start);
                }
                ans = Math.max(ans, search(s, start, end, k));
            }
            return ans;
        }

    }
}