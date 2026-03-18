package org.example.licode;

import java.util.*;

public class LiCodeTest {

    public static void main(String[] args) {
        int[] nums1 = {-3, -1, 0, 0, 0, 3, 3};

        int ds = removeDuplicates2(nums1);
        System.out.println(ds);
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

}
