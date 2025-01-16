import java.util.*;

public class rodCut {
    private static int maxProfit(int[] prices, int n) {
        int dp [] = new int[n+1];
        for(int i =1; i<=n; i++){
            for(int j=1; j<=i; j++){
                dp[i] = Math.max(dp[i],prices[j-1]+dp[i-j]);
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] prices = new int[n];

        for (int i = 0; i < n; i++) {
            prices[i] = sc.nextInt();
        }
        int ans = maxProfit(prices, n);
        System.out.println(ans);
    }
}
