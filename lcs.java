import java.util.*;

public class lcs {
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);

        String t1 = sc.nextLine();
        int start = 0, end = 0;
        for(int i=0; i<t1.length(); i++){
            if(t1.charAt(i) == '"'){
                start = i+1;
                break;
            }
        }
        for(int i = start; i<t1.length(); i++){
            if(t1.charAt(i) == '"'){
                end = i;
                break;
            }
        }
        String s1 = t1.substring(start,end);

        String t2 = sc.nextLine();
        int start1 = 0, end1 = 0;
        for(int i=0; i<t2.length(); i++){
            if(t2.charAt(i) == '"'){
                start1 = i+1;
                break;
            }
        }
        for(int i = start1; i<t2.length(); i++){
            if(t2.charAt(i) == '"'){
                end1 = i;
                break;
            }
        }
        String s2 = t2.substring(start1,end1);

        int[][] memo = new int[s1.length()+1][s2.length()+1];

        for (int i = 0; i < s1.length()+1; i++) {
            for (int j = 0; j < s2.length()+1; j++) {
                memo[i][j] = -1;
            }
        }

        int lcsLength = LCS(s1,s2,0,0,memo);
        System.out.println(lcsLength);

        sc.close();
    }

    public static int LCS(String s1, String s2, int i, int j, int[][] memo){
        if(i>=s1.length() || j>=s2.length()){ 
            return memo[i][j] = 0;
        }

        if(memo[i][j] != -1){ 
            return memo[i][j];
        }
        
        if(s1.charAt(i) == s2.charAt(j)){
            return memo[i][j] = LCS(s1,s2,i+1,j+1,memo)+1;
        }
        else{
            return memo[i][j] = Math.max(LCS(s1, s2, i+1, j, memo),LCS(s1, s2, i, j+1, memo));
        }
        
    }
    
}
