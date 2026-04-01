package org.example.licode;

import java.util.*;

public class LiCodeTest {

    public static void main(String[] args) {
        System.out.println(isHappy(19));

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

    /**
     * 罗马数字转整数, 这个主要在于要理解罗马数字的组成，罗马数字其实是各个字符所代表的数字之和，唯一要单独考虑的就是，常规情况下，罗马数字大的数字都在前，
     * 但是一些特殊情况，是小的数字在前，这种情况就不能加，而是减，
     * 譬如
     * III = 1+1+1
     * XV = 10 +5
     * IX= -1 + 10 =9
     *
     * @param s
     * @return
     */
    public static int romanToInt(String s) {
        char[] romanArr = s.toCharArray();
        int res = 0, n = romanArr.length;
        Map<Character, Integer> intMap = new HashMap<>();
        intMap.put('I', 1);
        intMap.put('V', 5);
        intMap.put('X', 10);
        intMap.put('L', 50);
        intMap.put('C', 100);
        intMap.put('D', 500);
        intMap.put('M', 1000);
        for (int i = 0; i < n; i++) {
            if (i + 1 < n && intMap.get(romanArr[i]) < intMap.get(romanArr[i + 1])) {
                res += intMap.get(romanArr[i + 1]) - intMap.get(romanArr[i]);
                i++;
            } else {
                res += intMap.get(romanArr[i]);
            }
        }
        return res;

    }

    /**
     * 最后一个单词的长度,两个解法
     * 一是直接用split api，然后看最后一个字符串的长度，split api会把最末尾的空格消除掉
     * 二是 倒序遍历，从后往前，先排除空格，然后遍历统计最后一个字符串的长度，由于charAt() api的存在，都无需把字符串转换为字符数组
     *
     * @param s
     * @return
     */
    public static int lengthOfLastWord(String s) {
//        String[] rt = s.split(" ");
//        return rt[rt.length - 1].length();

        int p = s.length() - 1;
        while (p >= 0 && s.charAt(p) == ' ') {
            p--;
        }
        int res = 0;
        while (p >= 0 && s.charAt(p) != ' ') {
            p--;
            res++;
        }
        return res;
    }

    /**
     * 最长公共前缀
     * 1. 横向扫描，直接套两个循环
     *
     * @param strs
     * @return
     */
    public static String longestCommonPrefix(String[] strs) {
        // 横向比较的解法
//        String preFix = strs[0];
//        for (int i = 1; i < strs.length; i++) {
//            int n = Math.min(strs[i].length(), preFix.length());
//            String s = "";
//            int p =0;
//            while (p < n) {
//                if (preFix.charAt(p) == (strs[i].charAt(p))) {
//                    s += preFix.charAt(p);
//                    p++;
//                } else {
//                    break;
//                }
//            }
//            preFix = s;
//            if(preFix.isEmpty())break;
//        }
//        return preFix;
//        纵向比较的写法
        if (strs == null || strs.length == 0) return "";
        // 只遍历第一个字符串的字符（纵向按位置检查）
        for (int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);
            // 遍历所有字符串，对比当前位置字符
            for (int j = 1; j < strs.length; j++) {
                // 超出长度 或 字符不相等，直接截断
                if (i == strs[j].length() || strs[j].charAt(i) != c) {
                    return strs[0].substring(0, i);
                }
            }
        }
        return strs[0];

    }

    /**
     * 找出字符串中第一个匹配项的下标 暴力解法，双指针
     *
     * @param haystack
     * @param needle
     * @return
     */
    public static int strStr(String haystack, String needle) {
        // 双指针
        int p1 = 0, n = haystack.length(), m = needle.length();
        // needle大于haystack则一定不匹配
        if (m > n) {
            return -1;
        }
        while (p1 < n) {
            if (p1 + m > n) {
                return -1;
            }
            int mid = p1, p2 = 0, count = 0;
            while (p2 < m) {
                if (haystack.charAt(mid++) != needle.charAt(p2++)) {
                    count++;
                    break;
                }
            }
            if (count == 0) {
                return p1;
            }
            p1++;
        }
        return -1;
    }

    /**
     * 找出字符串中第一个匹配项的下标 ，KMP算法，构建next数组
     *
     * @return
     */
    public static int strStr2(String haystack, String needle) {
        int n = haystack.length(), m = needle.length();
        if (m == 0) {
            return 0;
        }
        int[] pi = new int[m];
        for (int i = 1, j = 0; i < m; i++) {
            while (j > 0 && needle.charAt(i) != needle.charAt(j)) {
                j = pi[j - 1];
            }
            if (needle.charAt(i) == needle.charAt(j)) {
                j++;
            }
            pi[i] = j;
        }
        for (int i = 0, j = 0; i < n; i++) {
            while (j > 0 && haystack.charAt(i) != needle.charAt(j)) {
                j = pi[j - 1];
            }
            if (haystack.charAt(i) == needle.charAt(j)) {
                j++;
            }
            if (j == m) {
                return i - m + 1;
            }
        }
        return -1;
    }

    /**
     * 判断是否是回文串，双指针，一个从头一个从尾，
     *
     * @param s
     * @return
     */
    public static boolean isPalindrome(String s) {
        if (s.isEmpty()) {
            return true;
        }
        int n = s.length();
        s = s.toLowerCase();
        int left = 0, right = n - 1;
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                ++left;
            }
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                --right;
            }
            if (left < right) {
                if (s.charAt(left) != s.charAt(right)) {
                    return false;
                }
                left++;
                right--;
            }
        }
        return true;
    }

    /**
     * 判断s是否是t的子序列， 比如ace是axcde的子序列，但是ace不是casde的子序列。
     * 双指针，同时后移。
     *
     * @param s
     * @param t
     * @return
     */
    public static boolean isSubsequence(String s, String t) {
        int p1 = 0, p2 = 0, count = 0;
        while (p1 < s.length()) {
            while (p2 < t.length()) {
                if (s.charAt(p1) == t.charAt(p2)) {
                    count++;
                    p2++;
                    break;
                }
                p2++;
            }
            p1++;
        }
        return count == s.length();
    }

    /**
     * 判断s是否是t的子序列， 比如ace是axcde的子序列，但是ace不是casde的子序列。
     * 动态规划，其实相当于构建一个字典树，记录 字符串t上每个字符在当前横坐标下的位置，
     * 然后根据这个字典树去查找，
     *
     * @param s
     * @param t
     * @return
     */
    public static boolean isSubsequence2(String s, String t) {
        int n = s.length(), m = t.length();
        int[][] dp = new int[m + 1][26];
        //初始化边界条件，dp[i][j] = m表示t中不存在字符j
        for (int i = 0; i < 26; i++) {
            dp[m][i] = -1;
        }
        //从后往前递推初始化dp数组
        for (int i = m - 1; i >= 0; i--) {
            for (int j = 0; j < 26; j++) {
                if (t.charAt(i) == 'a' + j) {
                    dp[i][j] = i;
                } else {
                    // 不存在则
                    dp[i][j] = dp[i + 1][j];
                }
            }
        }
        int index = 0;
        for (int i = 0; i < n; i++) {
            //t中没有s[i] 返回false
            if (dp[index][s.charAt(i) - 'a'] == -1) {
                return false;
            }
            //否则直接跳到t中s[i]第一次出现的位置之后一位
            index = dp[index][s.charAt(i) - 'a'] + 1;
        }
        return true;
    }


    /**
     * 给你两个字符串：ransomNote 和 magazine ，判断 ransomNote 能不能由 magazine 里面的字符构成。
     * <p>
     * 如果可以，返回 true ；否则返回 false 。
     * <p>
     * 直接数组存一下字符出现次数然后查询即可
     *
     * @param ransomNote
     * @param magazine
     * @return
     */
    public static boolean canConstruct(String ransomNote, String magazine) {
        int[] dic = new int[26];
        int p = 0, q = 0;
        while (p < magazine.length()) {
            dic[magazine.charAt(p++) - 'a']++;
        }
        while (q < ransomNote.length()) {
            if (--dic[ransomNote.charAt(q++) - 'a'] < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 这种写法不推荐！！！！！！！！！！！！！！！
     * 给你两个字符串：ransomNote 和 magazine ，判断 ransomNote 能不能由 magazine 里面的字符构成。
     * <p>
     * 如果可以，返回 true ；否则返回 false 。
     * <p>
     * magazine 中的每个字符只能在 ransomNote 中使用一次。
     * 别用hashMap，纯纯陷阱，直接用数组存一下字符出现的次数再遍历就行了
     *
     * @param ransomNote
     * @param magazine
     * @return
     */
    public static boolean canConstruct2(String ransomNote, String magazine) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < magazine.length(); i++) {
            if (map.containsKey(magazine.charAt(i))) {
                map.put(magazine.charAt(i), map.get(magazine.charAt(i)) + 1);
            } else {
                map.put(magazine.charAt(i), 1);
            }
        }
        for (int j = 0; j < ransomNote.length(); j++) {
            if (map.get(ransomNote.charAt(j)) == null || map.get(ransomNote.charAt(j)) < 1) {
                return false;
            }
            map.put(ransomNote.charAt(j), map.get(ransomNote.charAt(j)) - 1);
        }
        return true;
    }


    /**
     * 给定两个字符串 s 和 t ，判断它们是否是同构的。 使用哈希表映射来实现
     *
     * @param s
     * @param t
     * @return
     */
    public static boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        Map<Character, Character> s2tMap = new HashMap<>();
        Map<Character, Character> t2sMap = new HashMap<>();
        int n = s.length();
        for (int i = 0; i < n; i++) {
            char a = s.charAt(i), b = t.charAt(i);
            if (s2tMap.containsKey(a) && s2tMap.get(a) != b || t2sMap.containsKey(b) && t2sMap.get(b) != a) {
                return false;
            }
            s2tMap.put(a, b);
            t2sMap.put(b, a);
        }
        return true;
    }

    /**
     * 给定一种规律 pattern 和一个字符串 s ，判断 s 是否遵循相同的规律。 使用哈希表映射来实现
     *
     * @param pattern
     * @param s
     * @return
     */
    public static boolean wordPattern(String pattern, String s) {
        String[] strArr = s.split(" ");
        char[] chars = pattern.toCharArray();

        if (strArr.length != chars.length) {
            return false;
        }
        Map<String, Character> str2charMap = new HashMap<>();
        Map<Character, String> char2strMap = new HashMap<>();
        for (int i = 0; i < strArr.length; i++) {
            char ch = chars[i];
            String str = strArr[i];
            if (str2charMap.containsKey(str) && str2charMap.get(str) != ch
                    || char2strMap.containsKey(ch) && !char2strMap.get(ch).equals(str)) {
                return false;
            }
            str2charMap.put(str, ch);
            char2strMap.put(ch, str);
        }
        return true;
    }

    /**
     * 有效的字母异位词 , 官方题解居然有直接排序的做法，，，这里选择用hash表，而是不是数组模拟的hash表。因为用数组的
     * 解法在面对unicode字符时会有问题，所以用hashMap就算是通解。但是比较·慢。 这里主要学会了 getOrDefault()这个方法
     *
     * @param s
     * @param t
     * @return
     */
    public static boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        Map<Character, Integer> map = new HashMap<>();
        int p = 0, q = 0;
        while (p < s.length()) {
            char ch = s.charAt(p);
            map.put(ch, map.getOrDefault(ch, 0) + 1);
            p++;
        }
        while (q < t.length()) {
            char ch = t.charAt(q);
            map.put(ch, map.getOrDefault(ch, 0) - 1);
            if (map.get(ch) < 0) {
                return false;
            }
            q++;
        }
        return true;
    }


    /**
     * 编写一个算法来判断一个数 n 是不是快乐数。
     *
     * 「快乐数」 定义为：
     *
     * 对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和。
     * 然后重复这个过程直到这个数变为 1，也可能是 无限循环 但始终变不到 1。
     * 如果这个过程 结果为 1，那么这个数就是快乐数。
     * @param n
     * @return
     */
    public static boolean isHappy(int n) {
        Set<Integer> set = new HashSet<>();
        while (n != 1 && !set.contains(n)) {
            set.add(n);
            n = getVal(n);
        }
        return n == 1;

    }

    private static int getVal(int n) {
        int sum = 0;
        while (n > 0) {
            int d = n % 10;
            n = n / 10;
            sum += d * d;
        }
        return sum;
    }


}
