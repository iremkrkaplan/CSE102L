import java.util.*;

public class Ex11_20220808056 {
    public static int numOfTriplets(int[] arr, int sum) {
    int count = 0;
    int n = arr.length;

    Arrays.sort(arr);

    for (int i = 0; i < n - 2; i++) {
        int left = i + 1;
        int right = n - 1;

        while (left < right) {
            int tripletSum = arr[i] + arr[left] + arr[right];
            if (tripletSum < sum) {
                count += right - left;
                left++;
            } else {
                right--;
            }
        }
    }

    return count;
}

    public static int kthSmallest(int[] arr, int k) {
        Arrays.sort(arr);
        return arr[k - 1];
    }
    public static String subSequence(String str) {
        int n = str.length();
        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (str.charAt(i) > str.charAt(j) && dp[i] < dp[j] + 1) {
                    dp[i] = dp[j] + 1;
                }
            }
        }

        int maxLength = 0;
        int endIndex = 0;

        for (int i = 0; i < n; i++) {
            if (dp[i] > maxLength) {
                maxLength = dp[i];
                endIndex = i;
            }
        }

        StringBuilder subseq = new StringBuilder();
        char prevChar = str.charAt(endIndex);

        subseq.append(prevChar);
        maxLength--;

        for (int i = endIndex - 1; i >= 0; i--) {
            if (dp[i] == maxLength && str.charAt(i) < prevChar) {
                subseq.append(str.charAt(i));
                prevChar = str.charAt(i);
                maxLength--;
            }
        }

        return subseq.reverse().toString();
    }
    public static int isSubString(String str1, String str2) {
        int n = str1.length();
        int m = str2.length();

        if (m > n) {
            return -1;
        }

        for (int i = 0; i <= n - m; i++) {
            int j;
            for (j = 0; j < m; j++) {
                if (str1.charAt(i + j) != str2.charAt(j)) {
                    break;
                }
            }
            if (j == m) {
                return i;
            }
        }
        return -1;
    }
    public static void findRepeats(int[] arr, int n) {
        if (arr == null || arr.length == 0) {
            return;
        }

        Arrays.sort(arr);

        int count = 1;
        int currentNum = arr[0];

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == currentNum) {
                count++;
            } else {
                if (count > n) {
                    System.out.println(currentNum);
                }
                count = 1;
                currentNum = arr[i];
            }
        }

        if (count > n) {
            System.out.println(currentNum);
        }
    }

}
