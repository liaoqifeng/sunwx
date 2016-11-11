package com.koch.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.wltea.expression.ExpressionEvaluator;
import org.wltea.expression.datameta.Variable;

public class ExpressionTest {
	public static void main(String[] args) {
		//String expression = "count>3?3:2";
		/*String expression = "count > 3 ? 1 : 2";  
		   //给表达式中的变量 "用户名" 付上下文的值  
		    List<Variable> variables = new ArrayList<Variable>();  
		   variables.add(Variable.createVariable("count", 1));  
		    //执行表达式  
		   Object result = ExpressionEvaluator.evaluate(expression, variables);  

		System.out.println(result.toString());*/
		
		int score = new Random().nextInt(40 - 10 + 1) + 10;
		
		System.out.println(score+"");
		
		System.out.println("554e3057a91d425bad00a98e66dfe259".length());
		
		for(int i=0;i<10000;i++){
			BigDecimal a = getRandomBonus(1.0D, 2.0D);
			System.out.println(a);
			
		}
	}
	
	private static BigDecimal getRandomBonus(double iStart,double iEnd){ 
		iStart = iStart * 100;
		iEnd = iEnd * 100;
        int start = 0;
        int end = 0;
        if(iStart > iEnd){
            start = (int)Math.floor(iEnd);
            end = (int)Math.floor(iStart);
        } else {
            start = (int)Math.floor(iStart);
            end = (int)Math.floor(iEnd);
        }
        int s = new Random().nextInt(end - start + 1) + start;
        BigDecimal b = new BigDecimal(s).divide(new BigDecimal(100)).setScale(1, BigDecimal.ROUND_HALF_UP);
        if(b.compareTo(new BigDecimal(iStart/100)) < 0) b = new BigDecimal(iStart/100);
        if(b.compareTo(new BigDecimal(iEnd/100)) > 0) b = new BigDecimal(iEnd/100);
        return b;
     }
}
