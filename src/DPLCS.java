/**
 * DP —— 最长公共子串
 */
public class DPLCS {

    /*
     * 1143. 最长公共子序列 —— LCS问题
     * 经典动态规划
     * dp[i][j] =
     * {
     *     dp[i - 1][j - 1] + 1;  （当text1.charAt(i) == text2.charAt(j)时）
     *     Math.max(dp[i - 1][j], dp[i][j - 1]);  （当text1.charAt(i) != text2.charAt(j)时）
     * }
     */
    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        int[][] dp = new int[m + 1][n + 1];
        // 如果 dp[i][j] 从 dp[i-1][j-1], dp[i-1][j], dp[i][j-1] 推导
        char[] cs1 = text1.toCharArray();
        char[] cs2 = text2.toCharArray();
        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (cs1[i] == cs2[j]) dp[i][j] = dp[i - 1][j - 1] + 1;
                else dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        return dp[m][n];
    }

    // 1035. 不相交的线
    // 动态规划，与 1143. 最长公共子序列 雷同 —— LCS问题
    public int maxUncrossedLines(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            int num1 = nums1[i - 1];
            for (int j = 1; j <= n; j++) {
                int num2 = nums2[j - 1];
                if (num1 == num2) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    // dp[i - 1][j]，不用模式串
                    // dp[i][j - 1]，不用当前串
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }
}
