package com.xws.xysz.util;

import java.util.Random;

/**
 * @program: yunshuidi-RandomUtils
 * @description: 获取四位随机数不重复
 * @author: rwt
 * @create: 2019-03-25 17:19
 **/

public class RandomUtils {
    // 随机生成一个没有重复数字的数
    public static String random() {

//        Random random = new Random(System.currentTimeMillis());

        int number = 0;

        boolean ok = true;

        do {

            ok = true;
            number = (int) ((Math.random() * 9 + 1) * 1000);

            int[] digits = {
                    number / 1000 % 10,
                    number / 100 % 10,
                    number / 10 % 10,
                    number % 10
            };

            for (int i = 0; i < 4 && ok; i++) {
                for (int j = i + 1; j < 4; j++) {
                    for(int x = j+1;x < 4;x++){
                        for(int y = x+1;y < 4;y++){
                            if (digits[i] == digits[j] && digits[j] == digits[x] && digits[x] == digits[y]) {
                                ok = false;
                                break;
                            }
                        }
                    }
                }
            }

        } while (!ok);

        return String.valueOf(number);
    }

    /**
     * 按照比例获取金额
     * @return
     */
    public static int moneyAmount() {
        Random r = new Random();
        int a = r.nextInt(100);//随机产生[0,100)的整数，每个数字出现的概率为1%
        int b; //需要的结果数字
        if (a < 40) { //前20个数字的区间，代表20%的几率
            b = 1000;
        } else if (a >= 40 && a < 60) {//[20,80)，60个数字的区间，代表60%的几率
            b = 2000;
        } else if (a >= 60 && a < 70) {
            b = 3000;
        } else if (a >= 70 && a < 90) {
            b = 5000;
        } else {//[80,100)，20个数字区间，代表20%的几率
            b = 10000;
        }
        return b;
    }

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            String n = random();
            System.out.println(n);
        }

    }
}
