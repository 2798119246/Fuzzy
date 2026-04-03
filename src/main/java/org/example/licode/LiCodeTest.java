package org.example.licode;

import java.util.*;

public class LiCodeTest {

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 1};
        System.out.println(simplifyPath("/.../a/../b/c/../d/./"));
        MinStack obj = new MinStack();
        obj.push(-3);
        obj.push(0);
        obj.push(-2);
        int param_2 = obj.getMin();
        obj.pop();
        int param_3 = obj.top();
        int param_4 = obj.getMin();
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
     * <p>
     * 「快乐数」 定义为：
     * <p>
     * 对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和。
     * 然后重复这个过程直到这个数变为 1，也可能是 无限循环 但始终变不到 1。
     * 如果这个过程 结果为 1，那么这个数就是快乐数。
     *
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

    /**
     * 存在重复元素 II ,hash表解法，一边存一边查找，如果存在满足要求的两个元素，则返回，切记这里不能用双指针，因为如果输入过长，会超时
     *
     * @param nums
     * @param k
     * @return
     */
    public static boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i]) && i - map.get(nums[i]) <= k) {
                return true;
            }
            map.put(nums[i], i);
        }
        return false;
    }

    /**
     * 存在重复元素 II ,滑动窗口
     *
     * @param nums
     * @param k
     * @return
     */
    public static boolean containsNearbyDuplicate2(int[] nums, int k) {
        // 滑动窗口,
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            // 从0开始遍历，当i> k时，需要将下标为 i-k-1 的元素移除。
            if (i > k) {
                set.remove(nums[i - k - 1]);
            }
            // 利用set元素不重复的特性，如果添加失败，说明此元素已经存在，即存在 nums[i] = nums[j]
            if (!set.add(nums[i])) {
                // 又因为在固定窗口内，所以存在 nums[i] = nums[j] 即满足要求
                return true;
            }
        }
        return false;
    }

    /**
     * 汇总区间
     * 给定一个  无重复元素 的 有序 整数数组 nums 。
     * 区间 [a,b] 是从 a 到 b（包含）的所有整数的集合。
     * 输入：nums = [0,1,2,4,5,7]
     * 输出：["0->2","4->5","7"]
     * 解释：区间范围是：
     * [0,2] --> "0->2"
     * [4,5] --> "4->5"
     * [7,7] --> "7"
     * <p>
     * 常规解法，遍历一次
     *
     * @param nums
     * @return
     */
    public List<String> summaryRanges(int[] nums) {
        List<String> result = new ArrayList<>();
        int i = 0, n = nums.length;
        while (i < n) {
            // 记录起始位置
            int low = i;
            // do- while 会先执行一次 i++;
            do {
                // 根据连续条件，开始挪动起始指针
                i++;
            } while (i < n && nums[i] == nums[i - 1] + 1);
            // 最高点必须减一，因为无论循环有没有执行，都会对i进行加一操作
            int high = i - 1;
            StringBuffer sb = new StringBuffer(String.valueOf(nums[low]));
            if (low < high) {
                sb.append("->");
                sb.append(nums[high]);
            }
            result.add(sb.toString());
        }
        return result;
    }

    /**
     * 合并区间， 记住思路，先按照左端点进行排序，然后再去一次遍历即可
     *
     * @param intervals
     * @return
     */
    public static int[][] merge(int[][] intervals) {
        if (intervals.length == 0) {
            return new int[0][2];
        }
        // 把intervals按照左端点进行排序
        Arrays.sort(intervals, new Comparator<int[]>() {
            public int compare(int[] intervals1, int[] intervals2) {
                return intervals1[0] - intervals2[0];
            }
        });
        List<int[]> merged = new ArrayList<>();
        for (int[] interval : intervals) {
            int left = interval[0], right = interval[1], n = merged.size();
            // 如果新的区间的左端点，都比以合并区间的右端点还要大，那么就说明这个合并区间已经合并完成，需要开始新的合并区间了
            if (merged.isEmpty() || merged.get(n - 1)[1] < left) {
                // 新的合并区间
                merged.add(interval);
            } else { // 可以合并
                // 更新右端点最大值，拿当前合并区间的右端点来和遍历到的区间右端点进行比较
                merged.get(n - 1)[1] = Math.max(merged.get(n - 1)[1], right);
            }
        }
        return merged.toArray(new int[merged.size()][]);
    }

    /**
     * 有效的括号  给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
     *
     * @param s
     * @return
     */
    public static boolean isValid(String s) {
        int n = s.length();
        // 长度必定为偶数
        if (n % 2 == 1) {
            return false;
        }

        // Stack<Character> stack = new Stack<>(); 这种写法其实java官方不推荐，下面这种写法才是罪推荐的栈使用方法。
        Deque<Character> stack = new LinkedList<Character>();
        Map<Character, Character> map = new HashMap<>() {
            {
                put(']', '[');
                put('}', '{');
                put(')', '(');
            }
        };
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            // 如果是右括号，则拿栈顶的元素来看能否凑成一对
            if (map.containsKey(ch)) {
                if (stack.isEmpty() || stack.peek() != map.get(ch)) {
                    return false;
                }
                stack.pop();
            } else {
                // 如果是左括号则放入栈顶等待匹配
                stack.push(ch);
            }
        }
        // 满足条件的字符串，执行完成后栈必然为空，即所有左括号都在对应位置匹配上了右括号
        return stack.isEmpty();
    }

    /**
     * 简化路径： 给你一组由 / 隔开的字符串（忽略空串和 .），请你从左到右遍历这些字符串，依次删除每个 .. 及其左侧的字符串（模拟返回上一级目录）。
     *
     * @param path
     * @return
     */
    public static String simplifyPath(String path) {
        // 这里用stack，最终组装出来的目录是反的，需要单独，处理，不能直接用String.join();所以用List
        List<String> stack = new ArrayList<>();
        for (String s : path.split("/")) {
            if (s.isEmpty() || s.equals(".")) {
                // 空字符串和“.”不处理，直接跳过
                continue;
            }
            if (!s.equals("..")) {
                // 常规目录直接入栈
                stack.add(s);
            } else if (!stack.isEmpty()) {
                // 从头到尾遍历的，遇到.. 把上一个入栈的目录跳过
                stack.remove(stack.size() - 1);
            }
        }
        return "/" + String.join("/", stack);
    }

}
