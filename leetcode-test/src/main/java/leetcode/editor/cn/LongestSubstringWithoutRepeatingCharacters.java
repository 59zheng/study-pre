/**
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
 * <p>
 * <p>
 * <p>
 * 示例 1:
 * <p>
 * <p>
 * 输入: s = "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * <p>
 * <p>
 * 示例 2:
 * <p>
 * <p>
 * 输入: s = "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * <p>
 * <p>
 * 示例 3:
 * <p>
 * <p>
 * 输入: s = "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 * <p>
 * <p>
 * <p>
 * <p>
 * 提示：
 * <p>
 * <p>
 * 0 <= s.length <= 5 * 10⁴
 * s 由英文字母、数字、符号和空格组成
 * <p>
 * <p>
 * Related Topics 哈希表 字符串 滑动窗口 👍 9559 👎 0
 */
package leetcode.editor.cn;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LongestSubstringWithoutRepeatingCharacters {
    public static void main(String[] args) {
        Solution solution = new LongestSubstringWithoutRepeatingCharacters().new Solution();

        int abcabcbb = solution.lengthOfLongestSubstring(" ");
        System.out.println(abcabcbb);
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int lengthOfLongestSubstring(String s) {
// 循环遍历,记录出现两个相同字符串的最大区间 左边界 保证当前区间内无重复
            int max = 0;
            int lefter = -1;
            Map<Character, Integer> onlyChatIndex = new HashMap<>();
            char[] chars = s.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                Character current = chars[i];
                if (onlyChatIndex.containsKey(current)) {
                    Integer integer = onlyChatIndex.get(current);
                    lefter = Math.max(lefter, integer);
                }
                max = Math.max(max, i - lefter);
                onlyChatIndex.put(current, i);
            }
            return max;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
