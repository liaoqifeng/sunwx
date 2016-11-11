package com.koch.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.koch.entity.LotteryGift;
import com.koch.util.LotteryUtil;

public class LotteryTest {
    public static void main(String[] args) {
    	
    	
    	
        List<LotteryGift> gifts = new ArrayList<LotteryGift>();
        // 序号==物品Id==物品名称==概率
//      gifts.add(new LotteryGift(1, "P1", "物品1", 5, 0.1));
//      gifts.add(new LotteryGift(2, "P2", "物品2", 5, 0.2d));
//      gifts.add(new LotteryGift(3, "P3", "物品3", 5, 0.4d));
//      gifts.add(new LotteryGift(4, "P4", "物品4", 5, 0.3d));
//      gifts.add(new LotteryGift(5, "P5", "物品5", 5, 0d));
//      gifts.add(new LotteryGift(6, "P6", "物品6", 5, -0.1d));
//      gifts.add(new LotteryGift(7, "P7", "物品7", 5, 0.000001d));
        
        gifts.add(new LotteryGift(1, "P1", "物品1", 5, 0.1d));
        gifts.add(new LotteryGift(2, "P2", "物品2", 5, 0.01d));
        gifts.add(new LotteryGift(3, "P3", "物品3", 5, 0.1d));
        gifts.add(new LotteryGift(4, "P4", "物品4", 5, 0.1d));
        gifts.add(new LotteryGift(5, "P5", "物品5", 5, 0.1d));
        gifts.add(new LotteryGift(6, "P6", "物品6", 5, 0.1d));
        gifts.add(new LotteryGift(7, "P7", "物品7", 5, 0.1d));
        gifts.add(new LotteryGift(8, "P8", "物品7", 5, 0.1d));
        gifts.add(new LotteryGift(9, "P9", "物品7", 5, 0.1d));
        gifts.add(new LotteryGift(10, "P10", "物品7", 5, 0.1d));
        
        //[0.8, 0.1, 0.7, 0.1, 0.6, 0.1, 0.5, 0.1, 0.4, 0.1]

        List<Double> orignalRates = new ArrayList<Double>(gifts.size());
        for (LotteryGift gift : gifts) {
            double probability = gift.getProbability();
            if (probability < 0) {
                probability = 0;
            }
            orignalRates.add(probability);
        }

        // // test
        // for (int i = 0; i < 10000; i++) {
        // try {
        // Gift tuple = gifts.get(LotteryUtil.lottery(orignalRates));
        // System.out.println(tuple);
        // } catch (Exception e) {
        // System.out.println("lottery failed, please check it!");
        // }
        // }

        //int orignalIndex = LotteryUtil.lottery(orignalRates);
        
        //System.out.println("key="+orignalIndex );
        
        // statistics
        Map<Integer, Integer> count = new HashMap<Integer, Integer>();
        double num = 10000;
        for (int i = 0; i < num; i++) {
            int orignalIndex = LotteryUtil.lottery(orignalRates);

            Integer value = count.get(orignalIndex);
            count.put(orignalIndex, value == null ? 1 : value + 1);
        }
        
        for (Entry<Integer, Integer> entry : count.entrySet()) {
        	System.out.println("key="+entry.getKey() );
            System.out.println( " ," + gifts.get(entry.getKey()) + ", count=" + entry.getValue() + ", probability=" + entry.getValue() / num);
        }
    }

}
