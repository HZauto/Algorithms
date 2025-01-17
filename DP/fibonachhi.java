import java.util.HashMap;
import java.util.Scanner;

public class fibonachhi {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        HashMap<Integer, Integer> memo = new HashMap<>();

        memo.put(0,0);
        memo.put(1,1);

        int ans1 = fibMemo(n,memo);
        System.out.println("fibMemo: " + ans1);

        int ans2 = fibTab(n);
        System.out.println("fibTab: " + ans2);

        sc.close();
    }

    private static int fibMemo(int n, HashMap<Integer, Integer> memo){
        if(!memo.containsKey(n)){
            return fibMemo(n-1, memo)+fibMemo(n-2, memo);
        }
        else{
            return memo.get(n);
        }
    }

    private static int fibTab(int n){
        int[] arr = new int[n+1];

        arr[0] = 0;
        arr[1] = 1;
        for(int i = 2; i<=n; i++){
            arr[i] = arr[i-1]+arr[i-2];
        }
        return arr[n];
    }
}
