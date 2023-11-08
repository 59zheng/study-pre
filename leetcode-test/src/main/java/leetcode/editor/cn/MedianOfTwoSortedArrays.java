/**
 * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
 * <p>
 * 算法的时间复杂度应该为 O(log (m+n)) 。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * 输入：nums1 = [1,3], nums2 = [2]
 * 输出：2.00000
 * 解释：合并数组 = [1,2,3] ，中位数 2
 * <p>
 * <p>
 * 示例 2：
 * <p>
 * <p>
 * 输入：nums1 = [1,2], nums2 = [3,4]
 * 输出：2.50000
 * 解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * 提示：
 * <p>
 * <p>
 * nums1.length == m
 * nums2.length == n
 * 0 <= m <= 1000
 * 0 <= n <= 1000
 * 1 <= m + n <= 2000
 * -10⁶ <= nums1[i], nums2[i] <= 10⁶
 * <p>
 * <p>
 * Related Topics 数组 二分查找 分治 👍 6815 👎 0
 */
package leetcode.editor.cn;

import sun.security.pkcs10.PKCS10;

public class MedianOfTwoSortedArrays {
    public static void main(String[] args) {

        Solution solution = new MedianOfTwoSortedArrays().new Solution();
        double medianSortedArrays = solution.findMedianSortedArrays4(new int[]{2}, new int[]{});
        System.out.println(medianSortedArrays);
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        /**
         * 合并数组 全部遍历,找中序  时间复杂度  (m+n) 空间复杂度 开辟了一个数组，保存合并后的两个数组 O(m+n)
         */
        public double findMedianSortedArrays1(int[] nums1, int[] nums2) {

            int m = nums1.length;
            int n = nums2.length;
            if (m == 0 && n == 0) return 0.0;
            int count = 0;
            int[] countNums = new int[m + n];
            int nums1Index = 0;
            int nums2Index = 0;
            // 前置检查一下 单个为空的情况 就不进行 循环了
            if (m == 0) {
                if (n % 2 == 0) {
                    return (nums2[n / 2 - 1] + nums2[n / 2]) / 2.0;
                } else {

                    return nums2[n / 2];
                }
            }
            if (n == 0) {
                if (m % 2 == 0) {
                    return (nums1[m / 2 - 1] + nums1[m / 2]) / 2.0;
                } else {
                    return nums1[m / 2];
                }
            }

            while (count != (m + n)) {
                // 两个数组单个为空的情况
                if (nums1Index == m) {
                    while (nums2Index != n) {
                        countNums[count++] = nums2[nums2Index++];
                    }
                    break;
                }
                if (nums2Index == n) {
                    while (nums1Index != m) {
                        countNums[count++] = nums1[nums1Index++];
                    }
                    break;
                }
                // 都不为空
                if (nums1[nums1Index] < nums2[nums2Index]) {
                    countNums[count++] = nums1[nums1Index++];
                } else {
                    countNums[count++] = nums2[nums2Index++];
                }

            }
            if (count % 2 == 0) {
                return (countNums[count / 2 - 1] + countNums[count / 2]) / 2.0;
            } else {
                return countNums[count / 2];
            }

        }

        /**
         * 不合并数组   直接找中序的几个数字.
         * <p>
         * 时间复杂度：遍历 len/2+1 次，len=m+n，所以时间复杂度依旧是 O(m+n)
         * 空间复杂度：都是定义的常量 计算的 为 0(1)
         */
        public double findMedianSortedArrays2(int[] nums1, int[] nums2) {
            int m = nums1.length;
            int n = nums2.length;
            int len = m + n;
            // 最终遍历的结果
            int left = -1;
            int right = -1;
            // 两个数组 当前遍历到的 index
            int aCurrentIndex = 0;
            int bCurrentIndex = 0;
            // 结束遍历的结果
            for (int i = 0; i <= len / 2; i++) {
                // 当前结果给上一次结果
                left = right;
                if (aCurrentIndex < m && (bCurrentIndex >= n || nums1[aCurrentIndex] < nums2[bCurrentIndex])) {
                    right = nums1[aCurrentIndex++];
                } else {
                    right = nums2[bCurrentIndex++];
                }
            }
            if (len % 2 == 0) {
                return (left + right) / 2.0;
            } else {
                return right;
            }

        }

        /**
         * 方法 3
         * 符合 时间复杂度 O(log (m+n))
         * 总数/2  总数/2+1
         */
        public double findMedianSortedArrays3(int[] nums1, int[] nums2) {
            int m = nums1.length;
            int n = nums2.length;
            // 这里有 +1 是为了保证 奇数 +1 和+2 /2 出来的值相同  即吧 奇数 偶数的情况合并处理
            int left = (n + m + 1) / 2;
            int right = (n + m + 2) / 2;
            return (getKth(nums1, 0, m - 1, nums2, 0, n - 1, left) + getKth(nums1, 0, m - 1, nums2, 0, n - 1, right)) * 0.5;
        }

        /**
         * 出的条件 一方数组为空了, 或者 偏移量 ==1 了
         */
        public int getKth(int[] num1, int start1, int end1, int[] num2, int start2, int end2, int skew) {
            // 数据的长度
            int len1 = end1 - start1 + 1;
            int len2 = end2 - start2 + 1;
            // 颠倒一下,使 len1 是最短的数组的长度
            if (len1 > len2) return getKth(num2, start2, end2, num1, start1, end1, skew);
            // 短的数组为 空了 直接出长度数组的偏移量
            if (len1 == 0) return num2[start2 + skew - 1];
            // 如果 skew 数量剩余1 了 出 当前两个数组的最小值
            if (skew == 1) return Math.min(num1[start1], num2[start2]);
            //排除的总偏移量/2 各个数组对应的中位偏移 index
            int num1Centre = start1 + Math.min(len1, skew / 2) - 1;
            int num2Centre = start2 + Math.min(len1, skew / 2) - 1;
            // 如果一遍小
            if (num1[num1Centre] > num2[num2Centre]) {
                // num2 num2Centre 之前的都去掉 总偏移量 - (num2Centre+1)
                return getKth(num1, start1, end1, num2, num2Centre + 1, end2, skew - (num2Centre - start2 + 1));
            } else {
                // num1 去掉 ==的情况下优先去 num1 的数字
                return getKth(num1, num1Centre + 1, end1, num2, start2, end2, skew - (num1Centre - start1 + 1));
            }

        }

        /**
         * 方法 4   这个牛逼
         * 理解  中切,通过比较切出来 两个不同大小数组的左半部分的 最大值 比较,移动 切点 i/j 的位置,并且保证 左半部分>=右半部分
         */
        public double findMedianSortedArrays4(int[] nums1, int[] nums2) {
            int m = nums1.length;
            int n = nums2.length;
            // 保证 m <= n
            if (m > n) {
                return findMedianSortedArrays4(nums2, nums1);
            }
            int iMin = 0;
            int iMax = m;
            while (iMin <= iMax) {
                // 平衡切点的条件  i==0  或者  A [ i - 1 ] > B [ j ] 在保证 i+j=m+n/2 的情况下
                int i = (iMin + iMax) / 2;
                int j = (m + n + 1) / 2 - i;
                // i 的极值判定,并且 大的数组元素 -1 比当前的 小的数组的最大值大 i++
                if (j != 0 && i != m && nums2[j - 1] > nums1[i]) {
                    iMin = i + 1;
                } else if (i != 0 && j != n && nums1[i - 1] > nums2[j]) {
                    iMax = i - 1;
                }
                // 满足平衡的条件了
                else {
                    int maxLeft = 0;
                    //  即小的数组为空了
                    if (i == 0) {
                        maxLeft = nums2[j - 1];
                        //  即小的数组走完了
                    } else if (j == 0) {
                        maxLeft = nums1[i - 1];
                    } else {
                        maxLeft = Math.max(nums1[i - 1], nums2[j - 1]);
                    }
                    // 奇数的话不需要考虑右半部分
                    if ((m + n) % 2 == 1) {
                        return maxLeft;
                    }
                    int minRight = 0;
                    // 左边走满了
                    if (i == m) {
                        minRight = nums2[j];
                    } else if (j == n) {
                        minRight = nums1[i];
                    } else {
                        minRight = Math.min(nums2[j], nums1[i]);
                    }
                    //如果是偶数的话返回结果
                    return (maxLeft + minRight) / 2.0;


                }
            }
            return 0.0;
        }

    }
//leetcode submit region end(Prohibit modification and deletion)

}
