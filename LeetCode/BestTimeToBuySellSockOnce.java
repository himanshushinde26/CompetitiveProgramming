/**
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
 * */
class BestTimeToBuySellSockOnce {
    public int maxProfit(int[] prices) {
        int maxDiff = 0;
        int minElementSoFar = Integer.MAX_VALUE;
        for (int i = 0; i<prices.length; i++) {
            minElementSoFar = Math.min(minElementSoFar, prices[i]);
            maxDiff = Math.max(maxDiff, prices[i]-minElementSoFar);
        }
        return maxDiff;
    }
}