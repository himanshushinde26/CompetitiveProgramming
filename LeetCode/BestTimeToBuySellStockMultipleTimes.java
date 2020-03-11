/**
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/
 *
 * Idea is keep the stock till the stock price of the current day is greater than stock price of the previous day.
 * That is, stock price in increasing every day.
 * Sell on the day where the next day stock price drops.
 * 1 3 5 3 5 1
 * Buy on 1 and keep 1 3 5 and sell on 5, profit = 4
 * again buy on 3 and keep 3 5 and sell on 5, profit = 2
 * Total profit = 6.
 *
 * */
class BestTimeToBuySellStockMultipleTimes {
    public int maxProfit(int[] prices) {
        int maxSum = 0;
        int sumCurrentWindow = 0;
        for (int i = 0; i<prices.length; i++) {
            if (i+1<prices.length && prices[i+1]-prices[i]>0) {
                sumCurrentWindow += prices[i+1]-prices[i];
            } else {
                maxSum += sumCurrentWindow;
                sumCurrentWindow = 0;
            }
        }
        return maxSum;
    }
}