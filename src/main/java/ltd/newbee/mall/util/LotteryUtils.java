package ltd.newbee.mall.util;


import ltd.newbee.mall.entity.lottery.football.*;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class LotteryUtils {

    public static final Map<String, Integer[]>  matrixNumber = new HashMap<>();
    public static final Map<String, Boolean> notSupportOneGame = new HashMap<>();
    static {
        matrixNumber.put("1v1", new Integer[]{1, 0, 0, 0, 0, 0, 0, 0});
        matrixNumber.put("2v1", new Integer[]{0, 1, 0, 0, 0, 0, 0, 0});
        matrixNumber.put("2v3", new Integer[]{2, 1, 0, 0, 0, 0, 0, 0});
        matrixNumber.put("3v1", new Integer[]{0, 0, 1, 0, 0, 0, 0, 0});
        matrixNumber.put("3v3", new Integer[]{0, 3, 0, 0, 0, 0, 0, 0});
        matrixNumber.put("3v4", new Integer[]{0, 3, 1, 0, 0, 0, 0, 0});
        matrixNumber.put("3v6", new Integer[]{3, 3, 0, 0, 0, 0, 0, 0});
        matrixNumber.put("3v7", new Integer[]{3, 3, 1, 0, 0, 0, 0, 0});
        matrixNumber.put("4v1", new Integer[]{0, 0, 0, 1, 0, 0, 0, 0});
        matrixNumber.put("4v4", new Integer[]{0, 0, 4, 0, 0, 0, 0, 0});
        matrixNumber.put("4v5", new Integer[]{0, 0, 4, 1, 0, 0, 0, 0});
        matrixNumber.put("4v6", new Integer[]{0, 6, 0, 0, 0, 0, 0, 0});
        matrixNumber.put("4v10", new Integer[]{4, 6, 0, 0, 0, 0, 0, 0});
        matrixNumber.put("4v11", new Integer[]{0, 6, 4, 1, 0, 0, 0, 0});
        matrixNumber.put("4v14", new Integer[]{4, 6, 4, 0, 0, 0, 0, 0});
        matrixNumber.put("4v15", new Integer[]{4, 6, 4, 1, 0, 0, 0, 0});
        matrixNumber.put("5v1", new Integer[]{0, 0, 0, 0, 1, 0, 0, 0});
        matrixNumber.put("5v5", new Integer[]{0, 0, 0, 5, 0, 0, 0, 0});
        matrixNumber.put("5v6", new Integer[]{0, 0, 0, 5, 1, 0, 0, 0});
        matrixNumber.put("5v10", new Integer[]{0, 10, 0, 0, 0, 0, 0, 0});
        matrixNumber.put("5v15", new Integer[]{5, 10, 0, 0, 0, 0, 0, 0});
        matrixNumber.put("5v16", new Integer[]{0, 0, 10, 5, 1, 0, 0, 0});
        matrixNumber.put("5v20", new Integer[]{0, 10, 10, 0, 0, 0, 0, 0});
        matrixNumber.put("5v25", new Integer[]{5, 10, 10, 0, 0, 0, 0, 0});
        matrixNumber.put("5v26", new Integer[]{0, 10, 10, 5, 1, 0, 0, 0});
        matrixNumber.put("5v30", new Integer[]{5, 10, 10, 5, 0, 0, 0, 0});
        matrixNumber.put("5v31", new Integer[]{5, 10, 10, 5, 1, 0, 0, 0});
        matrixNumber.put("6v1", new Integer[]{0, 0, 0, 0, 0, 1, 0, 0});
        matrixNumber.put("6v6", new Integer[]{0, 0, 0, 0, 6, 0, 0, 0});
        matrixNumber.put("6v7", new Integer[]{0, 0, 0, 0, 6, 1, 0, 0});
        matrixNumber.put("6v15", new Integer[]{0, 15, 0, 0, 0, 0, 0, 0});
        matrixNumber.put("6v20", new Integer[]{0, 0, 20, 0, 0, 0, 0, 0});
        matrixNumber.put("6v21", new Integer[]{6, 15, 0, 0, 0, 0, 0, 0});
        matrixNumber.put("6v22", new Integer[]{0, 0, 0, 15, 6, 1, 0, 0});
        matrixNumber.put("6v35", new Integer[]{0, 15, 20, 0, 0, 0, 0, 0});
        matrixNumber.put("6v41", new Integer[]{6, 15, 20, 0, 0, 0, 0, 0});
        matrixNumber.put("6v42", new Integer[]{0, 0, 20, 15, 6, 1, 0, 0});
        matrixNumber.put("6v50", new Integer[]{0, 15, 20, 15, 0, 0, 0, 0});
        matrixNumber.put("6v56", new Integer[]{6, 15, 20, 15, 0, 0, 0, 0});
        matrixNumber.put("6v57", new Integer[]{0, 15, 20, 15, 6, 1, 0, 0});
        matrixNumber.put("6v62", new Integer[]{6, 15, 20, 15, 6, 0, 0, 0});
        matrixNumber.put("6v63", new Integer[]{6, 15, 20, 15, 6, 1, 0, 0});
        matrixNumber.put("7v1", new Integer[]{0, 0, 0, 0, 0, 0, 1, 0});
        matrixNumber.put("7v7", new Integer[]{0, 0, 0, 0, 0, 7, 0, 0});
        matrixNumber.put("7v8", new Integer[]{0, 0, 0, 0, 0, 7, 1, 0});
        matrixNumber.put("7v21", new Integer[]{0, 0, 0, 0, 21, 0, 0, 0});
        matrixNumber.put("7v35", new Integer[]{0, 0, 0, 35, 0, 0, 0, 0});
        matrixNumber.put("7v120", new Integer[]{0, 21, 35, 35, 21, 7, 1, 0});
        matrixNumber.put("7v127", new Integer[]{7, 21, 35, 35, 21, 7, 1, 0});
        matrixNumber.put("8v1", new Integer[]{0, 0, 0, 0, 0, 0, 0, 1});
        matrixNumber.put("8v8", new Integer[]{0, 0, 0, 0, 0, 0, 8, 0});
        matrixNumber.put("8v9", new Integer[]{0, 0, 0, 0, 0, 0, 8, 1});
        matrixNumber.put("8v28", new Integer[]{0, 0, 0, 0, 0, 28, 0, 0});
        matrixNumber.put("8v56", new Integer[]{0, 0, 0, 0, 56, 0, 0, 0});
        matrixNumber.put("8v70", new Integer[]{0, 0, 0, 70, 0, 0, 0, 0});
        matrixNumber.put("8v247", new Integer[]{0, 28, 56, 70, 56, 28, 8, 1});
        matrixNumber.put("8v255", new Integer[]{8, 28, 56, 70, 56, 28, 8, 1});


        notSupportOneGame.put("2v3", true);
        notSupportOneGame.put("3v6", true);
        notSupportOneGame.put("3v7", true);
        notSupportOneGame.put("4v10", true);
        notSupportOneGame.put("4v14", true);
        notSupportOneGame.put("4v15", true);
        notSupportOneGame.put("5v15", true);
        notSupportOneGame.put("5v25", true);
        notSupportOneGame.put("5v30", true);
        notSupportOneGame.put("5v31", true);
        notSupportOneGame.put("6v21", true);
        notSupportOneGame.put("6v41", true);
        notSupportOneGame.put("6v56", true);
        notSupportOneGame.put("6v62", true);
        notSupportOneGame.put("6v63", true);
        notSupportOneGame.put("7v127", true);
        notSupportOneGame.put("8v255", true);


    }


    /**
     * Cmn eg: C(5,4)
     * @param m
     * @param n
     * @return
     */
    public static  int mathCmn(int m, int n) {
        int num = 1;
        int sum = 1;
        //(n - m)!
        for (int i =  m - n; i > 0 ; i--) {
            sum = sum * i;
        }
        //m!
        for (int cout = 0;  cout < m ;  cout++) {
            num = num * (m - cout);
        }
        // n!
        int nSum = 1;
        for (int i = 1; i <= n ; i++) {
            nSum = nSum * i;
        }
        return (num / sum)/nSum;
    }

    public static Integer[] getGames(String rule) {
       return matrixNumber.get(rule);
    }


    public  static  List<Double> getOddsByLotteryVos(List<LotteryOrder> lotteryOrders) {

        List<Double> odds = new ArrayList<>();
        lotteryOrders.forEach(lotteryOrder -> {
            switch (lotteryOrder.getType()) {
                case HALF_COURT:
                    lotteryOrder.getHalfCourtOrders().forEach(halfCourtOrder -> {
                        odds.add(halfCourtOrder.getDetailOdds());
                    });
                    break;
                case TOTAL_SCORE:
                   lotteryOrder.getTtgOrders().forEach(ttgOrder -> {
                       odds.add(ttgOrder.getGoalOdds());
                   });
                    break;
                case SCORE:
                   lotteryOrder.getCrsOrders().forEach(crsOrder -> {
                       odds.add(crsOrder.getCoreOdds());
                   });
                    break;
                case MIXED:
                    List<HalfCourtOrder> halfCourtOrdersMixed = lotteryOrder.getHalfCourtOrders();

                    List<TtgOrder> ttgOrdersMixed = lotteryOrder.getTtgOrders();

                    List<OddsOrder> oddsOrdersMixed = lotteryOrder.getOddsOrders();

                    List<CrsOrder> crsOrdersMixed = lotteryOrder.getCrsOrders();
                    if (!CollectionUtils.isEmpty(halfCourtOrdersMixed)) {
                        halfCourtOrdersMixed.forEach(halfCourtOrder -> {
                            odds.add(halfCourtOrder.getDetailOdds());
                        });
                    }
                    if (!CollectionUtils.isEmpty(ttgOrdersMixed)) {
                        lotteryOrder.getTtgOrders().forEach(ttgOrder -> {
                            odds.add(ttgOrder.getGoalOdds());
                        });
                    }
                    if (!CollectionUtils.isEmpty(oddsOrdersMixed)) {
                        oddsOrdersMixed.forEach(oddsOrder -> {
                            if (oddsOrder.getHomeDraw()) {
                                odds.add(oddsOrder.getHomeDrawOdds());
                            }
                            if (oddsOrder.getHomeWin()) {
                                odds.add(oddsOrder.getHomeWinOdds());
                            }
                            if (oddsOrder.getVisitingWin()) {
                                odds.add(oddsOrder.getVisitingWinOdds());
                            }
                        });
                    }
                    if (!CollectionUtils.isEmpty(crsOrdersMixed)) {
                        crsOrdersMixed.forEach(crsOrder -> {
                            odds.add(crsOrder.getCoreOdds());
                        });
                    }


                    break;
                case VICTORY:
                    lotteryOrder.getOddsOrders().forEach(oddsOrder -> {
                        if (oddsOrder.getHomeDraw()) {
                            odds.add(oddsOrder.getHomeDrawOdds());
                        }
                        if (oddsOrder.getHomeWin()) {
                            odds.add(oddsOrder.getHomeWinOdds());
                        }
                        if (oddsOrder.getVisitingWin()) {
                            odds.add(oddsOrder.getVisitingWinOdds());
                        }
                    });
                    break;
            }
        });
        odds.sort(Double::compareTo);
        return odds;
    }

    /*public static void main(String[] args) {
        List<Double> list = new ArrayList<Double>();
        list.add(2.6);
        list.add(3.4);
        list.add(1.2);
        list.add(5.6);
        list.add(7.0);
        Vector<Double> listVector = new Vector<>();
        list.forEach(aDouble -> {
            listVector.add(aDouble);
        });
        Vector<Vector<Double>> vectors = subsets(listVector, 5, 2);
        vectors.forEach(ele -> {
            System.out.print("[");
            ele.forEach(aDouble -> {
                System.out.print(aDouble + ",");
            });
            System.out.println("]");
        });

       int ss =  mathCmn(4, 2);
        System.out.println("===========>" + ss);
    }*/

    static public  Vector<Double> coverArrayListToVector(List<Double> list ) {
        Vector<Double> listVector = new Vector<>();
        list.forEach(aDouble -> {
            listVector.add(aDouble);
        });
        return listVector;
    }


    public  static  Vector<Vector<Double> > subsets(Vector<Double> source , int n, int m)
    {
        //n个数有0~max-1即2^n中组合，1<<n表示2^n
        int max = 1<<n;
        Vector<Vector<Double> > result = new Vector<>();
        for(int i = 0;i < max;i++)
        {
            Vector<Double> temp = new Vector<>();
            int idx = 0;
            int j = i;
            while(j > 0)
            {
                //判断最后一位是否为1，若为1则将对应数加入到当前组合中
                if((j & 1) == 1)
                {
                    temp.add(source.get(idx));
                }
                idx++;
                //判断了这一位是否为1后要右移
                j = j>>1;
            }
            //判断完了一种组合，加入到结果集中
            if (temp.size() == m) {
                result.add(temp);
            }
        }
        return result;
    }

}