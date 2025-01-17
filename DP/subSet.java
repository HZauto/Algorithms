import java.util.Scanner;

public class subSet {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int array[] = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = sc.nextInt();
        }

        int sum = sc.nextInt();

        if (isSubsetSum(array, n, sum))
            System.out.println("true");

        else
            System.out.println("false");

        sc.close();
    }

    private static boolean isSubsetSum(int[] array, int n, int sum) {
        boolean[][] DP = new boolean[n + 1][sum + 1];

        for (int i = 0; i <= n; i++) {
            DP[i][0] = true; // row 0 is emptyset all true
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= sum; j++) {

                if (j < array[i - 1])
                    DP[i][j] = DP[i - 1][j];

                else
                    DP[i][j] = DP[i - 1][j] || DP[i - 1][j - array[i - 1]];
            }
        }
        return DP[n][sum];
    }
}
