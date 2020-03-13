/**
 * https://leetcode.com/problems/regular-expression-matching/
 * Backtracking and memoization
 * Time complexity?
 * Work(evaluate(String s, String p, int startS, int startP)) for each combination of startS and startP is done once.
 * Therefore, time complexity is O(NM)
 * Where N is length of String and M is length of pattern.
 * Space complexity is also O(NM)
 * */

class RegularExpressionMatching {
    int mem[][];
    public boolean isMatch(String s, String p) {
        mem = new int[s.length()+1][p.length()];
        return evaluate(s,p,0,0);
    }

    private boolean evaluate (String s, String p, int startS, int startP) {
        if (startP == p.length() && startS == s.length()) {
            return true;
        }
        if (startP == p.length()) {
            return false;
        }
        if (mem[startS][startP] != 0) {
            return mem[startS][startP]==1? true:false;
        }
        int sizePattern = p.length();
        int sizeString = s.length();
        char pTemp;
        int i = startP;
        if (p.charAt(i) == '*') {
            //If char at 0 of pattern is *, that * is unnecessary
            if (i==0) {
                if (evaluate(s,p,startS, startP+1)) {
                    mem[startS][startP] = 1;
                    return true;
                }
                mem[startS][startP] = -1;
                return false;
            }
            int j = startS;
            //If previous to * is ., that means any char or no char
            if (p.charAt(i-1) == '.') {
                /*
                 * If current * is the last char of pattern that means,
                 * any string can match the remaining pattern that is .*
                 */
                if (startP+1 == sizePattern) {
                    mem[startS][startP] = 1;
                    return true;
                }
                while (j<sizeString) {
                    if (evaluate(s,p,j,i+1)) {
                        mem[startS][startP] = 1;
                        return true;
                    }
                    j++;
                }
                /*
                * This is for the case where we have .*.* at the end of pattern
                * Eg:
                * String : "a"
                * Pattern : "a.*.*"
                *
                * */
                if (j == sizeString) {
                    if (evaluate(s,p,j,i+1)) {
                        mem[startS][startP] = 1;
                        return true;
                    }
                }
            } else {
                pTemp = p.charAt(i-1);
                while (j<sizeString) {
                    if (s.charAt(j)== pTemp) {
                        if (evaluate(s,p,j+1,i+1)) {
                            mem[startS][startP] = 1;
                            return true;
                        }
                        j++;
                    } else {
                        break;
                    }
                }
                // Case of no char match for _*
                if (evaluate(s,p,startS, startP+1)) {
                    mem[startS][startP] = 1;
                    return true;
                }
            }
        } else if (startP+1 < sizePattern && (p.charAt(startP+1) == '*')) { // When next char is *, skip current char of pattern
            if (evaluate(s,p,startS, startP+1)) {
                mem[startS][startP] = 1;
                return true;
            }
        } else if (startS<s.length() && p.charAt(i) == '.') {
            if (evaluate(s,p,startS+1, startP+1)) {
                mem[startS][startP] = 1;
                return true;
            }
        } else if (startS<s.length() && s.charAt(startS) == p.charAt(startP)){
            if(evaluate(s,p,startS+1, startP+1)) {
                mem[startS][startP] = 1;
                return true;
            }
        }
        mem[startS][startP] = -1;
        return false;
    }
}