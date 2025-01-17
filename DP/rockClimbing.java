import java.util.Scanner;
import java.lang.Math;

public class rockClimbing {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int wall[][] = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                wall[i][j] = sc.nextInt();
            }
        }
        climb(wall, n, m);
    }

    public static void climb(int wall[][], int n, int m) {
        int DP[][] = new int[n + 1][m + 2];

        for (int j = 1; j <= m; j++) {
            DP[0][j] = 0;
        }
        for (int i = 0; i <= n; i++) {
            DP[i][0] = Integer.MIN_VALUE;
            DP[i][m + 1] = Integer.MIN_VALUE;
        }

        for (int j = 1; j <= m; j++) {
            DP[1][j] = wall[1][j];
        }

        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int energyAbove = DP[i - 1][j];
                DP[i][j] = Math.max(energyAbove, (Math.max(DP[i - 1][j - 1], DP[i - 1][j + 1]))) + wall[i][j];
            }
        }

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m + 1; j++) {
                System.out.print(DP[i][j] + " ");
            }
            System.out.println();
        }

        int maxEnergy = Integer.MIN_VALUE;
        for (int j = 1; j <= m; j++) {
            if (DP[n][j] > maxEnergy) {
                maxEnergy = DP[n][j];
            }
        }

        System.out.println(maxEnergy);

    }

}
