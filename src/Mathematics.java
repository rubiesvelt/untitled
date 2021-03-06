import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mathematics {

    /*
     * 829. 连续整数求和
     * 给定一个正整数 n，试求有多少组连续正整数满足所有数字之和为 n
     * e.g.
     * 101
     * ＝ 101
     * ＝ 50 ＋ 51
     * ＝ 24 ＋ 25 ＋ 26 ＋ 27 = 24 * 4 + 6 ＝ a * n + n * (n - 1) / 2
     */
    public int consecutiveNumbersSum(int n) {
        // 1 个数时，必然有一个数可构成N
        // 2 个数若要构成N，第2个数与第1个数差为1，N减掉这个1能整除2则能由商与商+1构成N
        // 3 个数若要构成N，第2个数与第1个数差为1，第3个数与第1个数的差为2，N减掉1再减掉2能整除3则能由商、商+1与商+2构成N
        // 依次内推，当商即第1个数小于等于0时结束
        int res = 0;
        for (int i = 1; n > 0; n -= i++) {  // 判断当有i个连续数字时，是否满足条件；
            if (n % i == 0) {
                res++;
            }
        }
        return res;
    }

    /*
     * 剑指 Offer 10- I. 斐波那契数列
     * 写一个函数，输入 n ，求斐波那契（Fibonacci）数列的第 n 项（即 F(N)）
     * 斐波那契数列的定义如下
     * F(0) = 0,   F(1) = 1
     * F(N) = F(N - 1) + F(N - 2), 其中 N > 1.
     */
    public int fib(int n) {
        int n0 = 0;
        int n1 = 1;
        int mod = 1_000_000_007;
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        while (n-- > 1) {
            int n2 = (n0 + n1) % mod;
            n0 = n1;
            n1 = n2;
        }
        return n1;
    }

    /**
     * shopee 笔试一题
     * <br/>差分数组
     * <p/>
     * <br/>数组array由0，1组成，求数组中最长的 "连续子序列"，使子序列中0和1的个数相等，返回其长度
     * <br/>e.g.
     * <br/>array = [0,1,0]
     * <br/>-> 2
     * <p/>
     *
     * @param array 数组
     * @return 最大
     */
    public int findMax(int[] array) {
        int n = array.length;
        int[] diff = new int[n + 1];  // diff records how much 1 more than 0
        if (n == 0) {
            return 0;
        }
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 0);
        int t;
        int max = 0;
        for (int i = 0; i < n; i++) {
            if (array[i] == 1) {
                t = diff[i] + 1;
                diff[i + 1] = t;
            } else {
                t = diff[i] - 1;
                diff[i + 1] = t;
            }

            if (map.containsKey(t)) {
                int t1 = map.get(t);
                max = Math.max(max, i + 1 - t1);
            } else {
                map.put(t, i + 1);
            }
        }
        return max;
    }


    /*
     * 1943. 描述绘画结果
     *
     * segments[i] = [starti, endi, colori]表示starti和endi之间的颜色为colori，多个颜色重合时，将颜色加起来
     * 求最终绘画结果
     * e.g.
     * segments = [[1,4,5],[4,7,7],[1,7,9]]
     * -> [[1,4,14],[4,7,16]]
     * segments = [[1,7,9],[6,8,15],[8,10,7]]
     * -> [[1,6,9],[6,7,24],[7,8,15],[8,10,7]]
     *
     * 扫描线(差分)
     */
    public List<List<Long>> splitPainting(int[][] segments) {
        boolean[] startPoints = new boolean[100006];
        long[] acc = new long[100006];  // 扫描线
        for (int[] seg : segments) {
            acc[seg[0]] += seg[2];
            acc[seg[1]] -= seg[2];
            startPoints[seg[0]] = true;
        }
        List<List<Long>> ans = new ArrayList<>();
        long sum = 0;
        int pre = 0;
        for (int i = 0; i < acc.length; i++) {
            if (acc[i] == 0 && !startPoints[i]) continue;
            if (sum == 0) {
                sum = acc[i];
                pre = i;
                continue;
            }
            List<Long> l = new ArrayList<>();
            l.add((long) pre);
            l.add((long) i);
            l.add(sum);
            ans.add(l);
            pre = i;
            sum += acc[i];
        }
        return ans;
    }

    /*
     * 995. K 连续位的最小翻转次数
     * 在仅包含 0 和 1 的数组 A 中，每一次将 K 位连续子数组翻转 0变1 1变0
     * 求最少操作几次能使数组所有都是 1，如果不能，返回 -1
     *
     * 差分数组
     */
    public int minKBitFlips(int[] A, int K) {
        int n = A.length;
        int[] diff = new int[n + 1];  // 差分数组
        int ans = 0;
        int revCnt = 0;
        for (int i = 0; i < n; i++) {
            revCnt += diff[i];  // 当前项目被翻转了revCnt次
            if ((A[i] + revCnt) % 2 == 0) {  // 照现在这样，A[i]将会是0
                if (i + K > n) {
                    return -1;
                }
                ans++;
                revCnt++;  // 这俩配合，相当于将当前 至 后K个元素反转
                diff[i + K]++;  // 此处++和--效果一样
            }
        }
        return ans;
    }

    // 1922. 统计好数字的数目
    // 快速幂
    // 取模应该写在外面，并使用同一mod，否则类型转换会出现意想不到的错误
    long mod = (long) (1e9 + 7);

    public int countGoodNumbers1(long n) {
        // 直接用快速幂
        return (int) (Utils.quickPow(5, (n + 1) / 2, 1000000007) * Utils.quickPow(4, n / 2, 1000000007) % mod);
    }

    public int countGoodNumbers(long n) {
        if (n == 1) return 5;
        long a = n / 2;
        long b = n % 2;
        // 求 20 的 a 次方 与 1e9 求余的结果
        // (a * b) % q = (a % q * b % q) % q
        long mod = (long) (1e9 + 7);
        long ans = 1;
        long base = 20;
        while (a > 0) {
            // 多余的那个
            long t = a & 1;
            if (t == 1) ans = ans * base % mod;
            // other *= Math.pow(base, t) % mod;
            // 正式的
            a /= 2;
            base = base * base % mod;
        }
        // int t = (int) (Math.pow(5, b) * (other % mod) * (base % mod) % mod);
        return (int) (ans * Math.pow(5, b) % mod);
    }

    /*
     * 810. 黑板异或游戏
     * 博弈论
     * 经典场景：n = 101，两个人依次操作，每个人可以选 1-9 中一个数从n中减去，减到0者胜；先手必胜
     */
    public boolean xorGame(int[] nums) {
        int sum = 0;
        for (int i : nums) sum ^= i;
        /*
         * 如果数组异或和是0，则 先手必胜
         * 如果数组异或和不为0，但序列长度为偶数，那么最终会出现 后手必败
         */
        return sum == 0 || nums.length % 2 == 0;
    }

    /*
     * 137. 只出现一次的数字 II
     * 给你一个整数数组 nums ，除某个元素仅出现 一次 外，其余每个元素都恰出现 三次 。请你找出并返回那个只出现了一次的元素
     * 位运算
     * 有限状态自动机
     */
    public int singleNumber(int[] nums) {
        int ones = 0, twos = 0;
        for (int num : nums) {
            /*
             * 每一位
             * 第一次出现 1 —— two = 0, one = 1
             * 第二次出现 1 —— two = 1, one = 0
             * 第三次出现 1 —— two = 0, one = 0
             * 出现 0 原地不变
             */
            ones = ones ^ num & ~twos;
            twos = twos ^ num & ~ones;
        }
        return ones;
    }

    // 633. 平方数之和
    // 平方开方
    public boolean judgeSquareSum(int c) {
        int i = 0;
        while (i * i <= c / 2) {
            if (isSqrt(c - i * i)) {
                return true;
            }
            i++;
        }
        return false;
    }

    public static boolean isSqrt(double n) {
        double d = Math.sqrt(n);
        return d % 1 == 0;
    }
}
