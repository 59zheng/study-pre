package leetcode.editor.cn;//给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target 的那 两个 整数，并返回它们的数组下标。
//
// 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。 
//
// 你可以按任意顺序返回答案。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [2,7,11,15], target = 9
//输出：[0,1]
//解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
// 
//
// 示例 2： 
//
// 
//输入：nums = [3,2,4], target = 6
//输出：[1,2]
// 
//
// 示例 3： 
//
// 
//输入：nums = [3,3], target = 6
//输出：[0,1]
// 
//
// 
//
// 提示： 
//
// 
// 2 <= nums.length <= 10⁴ 
// -10⁹ <= nums[i] <= 10⁹ 
// -10⁹ <= target <= 10⁹ 
// 只会存在一个有效答案 
// 
//
// 
//
// 进阶：你可以想出一个时间复杂度小于 O(n²) 的算法吗？ 
//
// Related Topics 数组 哈希表 👍 17566 👎 0


import java.util.HashMap;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    public int[] twoSum(int[] nums, int target) {
        // 简单写法 双重 for 循环 时间复杂度 o n2  最差双重遍历才能找到
        for (int i = 0; i < nums.length; i++) {
            for (int i1 = nums.length - 1; i1 >= 0; i1--) {
                if (nums[i] + nums[i1] == target && i != i1) {

//                    return new int[]{i, i1};
                }
            }
        }
        // 利用 hash 表去除,找是否有匹配的元素  时间复杂度 0 1
        HashMap<Integer, Integer> hashTable = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (hashTable.containsKey(target - nums[i])) {
                return new int[]{ hashTable.get(target - nums[i]),i};
            }
            hashTable.put(nums[i], i);
        }


        return new int[0];
    }
}
//leetcode submit region end(Prohibit modification and deletion)
