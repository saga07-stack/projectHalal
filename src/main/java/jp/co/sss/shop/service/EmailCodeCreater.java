package jp.co.sss.shop.service;

import java.util.Random;

public class EmailCodeCreater {
	
	public int RandomNumberGenerator() {
		
		Random random = new Random();
    		
	return 100000 + random.nextInt(900000); // 100000から999999までのランダムな整数を生成	
		
		
	}
	

}
