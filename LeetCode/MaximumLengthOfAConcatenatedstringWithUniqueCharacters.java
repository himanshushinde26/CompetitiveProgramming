import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This problem can be simplified to 0-1 knapsack problem
 * Convert string to binary of length 26 and use bitwise and
 * to find if the two string does not contain any common char.
 * For example
 * "a" -> "00000000000000000000000001"
 * "b" -> "00000000000000000000000010"
 * "a" & "b" -> 0
 * */
class MaximumLengthOfAConcatenatedstringWithUniqueCharacters {
    /*
    * Contains indices of strings which has duplicate chars in it.
    * We cannot add this string in the final string, as the final string should contain all unique chars.
    * */
    int[] invalidStringAtIndex;
    HashMap<String,Integer> memo = new HashMap<>();

    public int maxLength(List<String> arr) {
        invalidStringAtIndex = new int[arr.size()];
        int[] intergerRepresentationOfstrings = getIntegerRepresentationOfEachString((ArrayList<String>) arr);
        return solve(0, intergerRepresentationOfstrings, 0, (ArrayList<String>) arr, 0);
    }

    /*
    * Create map of Array[26] for each string, it should be binary of strings who does not have repeating chars in it.
    * For example "ba" -> [00000000000000000000000011]
    * This is nothing but binary, convert this binary to base 10 Integer
    * Therefore "ba" -> "00000000000000000000000011" -> 3
    * */
    private int[] getIntegerRepresentationOfEachString(ArrayList<String> arr) {
        byte[] binaryOfEachString;
        int[] result = new int[arr.size()];
        for (int i = 0; i<arr.size(); i++) {
            binaryOfEachString = new byte[26];
            for (int j = 0; j<arr.get(i).length(); j++) {
                /*
                * If there is a duplicate char in the string
                * */
                if (binaryOfEachString[(int)arr.get(i).charAt(j) - (int)'a'] == 1) {
                    invalidStringAtIndex[i] = 1;
                } else {
                    binaryOfEachString[(int)arr.get(i).charAt(j) - (int)'a'] = 1;
                }
            }
            result[i] = Integer.parseInt(getStringForBinaryByteArray(binaryOfEachString), 2);
        }
        return result;
    }

    private String getStringForBinaryByteArray (byte[] binaryOfEachString) {
        StringBuilder result = new StringBuilder();
        for (int i = binaryOfEachString.length-1; i>=0; i--) {
            result.append(binaryOfEachString[i]);
        }
        return result.toString();
    }

    private int solve (int binaryOfConcatString, int[] intergerRepresentationOfstrings, int index, ArrayList<String> arr, int count) {
        if (index == arr.size()) {
            return count;
        }
        if (memo.containsKey(Integer.toString(binaryOfConcatString) + index)) {
            return memo.get(Integer.toString(binaryOfConcatString) + index);
        }
        int includeCount = Integer.MIN_VALUE;
        int excludeCount;
        /*
        * String in the current index should be valid, and should not contain chars which are already present in ConcatString
        * */
        if (invalidStringAtIndex[index] != 1 && ((intergerRepresentationOfstrings[index] & binaryOfConcatString) == 0)) {
            includeCount = solve((binaryOfConcatString|intergerRepresentationOfstrings[index]), intergerRepresentationOfstrings, index+1, arr, count + arr.get(index).length());
        }
        excludeCount = solve(binaryOfConcatString, intergerRepresentationOfstrings, index+1, arr, count );

        memo.put(Integer.toString(binaryOfConcatString) + index, Math.max(includeCount, excludeCount));
        return Math.max(includeCount, excludeCount) ;
    }
}