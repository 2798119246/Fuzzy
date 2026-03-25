package org.example.licode;

import java.util.*;

public class LiCodeTest {

    public static void main(String[] args) {
        int[] nums1 = {1, 7, 5, 2, 1, 9, 2};

        System.out.println(maxProfit(nums1));
    }

    /**
     * 暴力枚举，没什么好说的
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        int[] res = new int[2];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    res[0] = i;
                    res[1] = j;
                }
            }
        }
        return res;
    }

    /**
     * hash表的查找效率要比直接遍历要高的多，每次都去hash表查找target-x，找到即返回，效率更高.
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        return null;
    }

    /**
     * 合并两个有序数组，最简单的办法，暴力破解，直接遍历
     *
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        for (int i = m, j = 0; i < m + n; i++, j++) {
            if (nums1.length == 0) {
                return;
            }
            nums1[i] = nums2[j];
        }
        Arrays.sort(nums1);
    }

    /**
     *
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public static void merge2(int[] nums1, int m, int[] nums2, int n) {
        int p1 = 0, p2 = 0;
        int[] sorted = new int[m + n];
        int cur;
        while (p1 < m || p2 < n) {
            if (p1 == m) {
                cur = nums2[p2++];
            } else if (p2 == n) {
                cur = nums1[p1++];
            } else if (nums1[p1] < nums2[p2]) {
                cur = nums1[p1++];
            } else {
                cur = nums2[p2++];
            }
            sorted[p1 + p2 - 1] = cur;
        }
        for (int i = 0; i != m + n; ++i) {
            nums1[i] = sorted[i];
        }
    }

    /**
     * 移除重复元素，利用java的Set集合元素不重复的特性，直接跳过判断重复元素的过程，其中HashSet不会保留插入顺序，也不会自动排序，而
     * LinkedHashSet 可以保持插入顺序，TreeSet不保留插入顺序但是可以自动排序
     *
     * @param nums
     * @return
     */
    public static int removeDuplicates(int[] nums) {
        Set<Integer> set = new LinkedHashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        int p = 0;
        for (Integer value : set) {
            nums[p++] = value;
        }
        return set.size();
    }

    /**
     * 经典双指针
     *
     * @param nums
     * @return
     */
    public static int removeDuplicates2(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        int fast = 1, slow = 1;
        while (fast < n) {
            if (nums[fast] != nums[fast - 1]) {
                nums[slow] = nums[fast];
                ++slow;
            }
            ++fast;
        }
        return slow;
    }

    /**
     * 移除元素，也可以用新建数组的方式，一次遍历拿出不同元素，二次遍历把不同元素放到数组首位
     *
     * @param nums
     * @param val
     * @return
     */
    public int removeElement(int[] nums, int val) {
        int rt = 0;
        int n = nums.length;
        if (n == 0) {
            return rt;
        }
        for (int num : nums) {
            if (num != val) {
                nums[rt++] = num;
            }
        }
        return rt;
    }

    /**
     * 多数元素，找出数组元素中的众数（出现次数大于n/2），有三种常见解法：
     * 第一种就是用hash表存出现次数然后找最大的
     * 第二种是利用数学规律，当他出现次数大于 n/2时，在有序数组中，它一定是数组的第n/2个元素也就是nums[n/2]
     * 第三种就是摩尔投票，这是因为题目保证了众数出现次数大于n/2，那么相当于竞选中一个人的票数会大于其他所有参与者的总票数，
     * 所以把这些不同参选者的票数相抵消，当选者的票数一定至少剩一张。
     *
     * @param nums
     * @return
     */
    public static int majorityElement(int[] nums) {
        int rt = 0;
        int counts = 0;
        for (int num : nums) {
            if (counts == 0) {
                rt = num;
            }
            if (rt == num) {
                counts++;
            } else {
                counts--;
            }
        }
        return rt;
    }

    /***
     * 买卖股票的最佳时机,只需要记录一个历史最低价格，然后去找后续的最高价卖出即可。动态规划的思想，只记录历史持股的最低价和今天卖出的最高价，
     * 每天只判断是不是最低价和利润是不是最高。
     * 1. 记住历史最便宜的价格（最优状态）
     * 2. 每天算一下今天卖能不能赚更多（状态转移）
     * @param prices
     * @return
     */
    public static int maxProfit(int[] prices) {
        int res = 0, minPoint = Integer.MAX_VALUE;
        for (int price : prices) {
            if (price < minPoint) {
                minPoint = price;
            } else if (price - minPoint > res) {
                res = price - minPoint;
            }
        }
        return res;
    }
}
