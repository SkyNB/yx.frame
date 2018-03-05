package com.lnet.frame.util;

import java.util.Random;

/**
 * Created by LH on 2016/12/30.
 */
public class LogisticsOrderUtil implements Strategy {
    private static final String heard = "LNET00";

    @Override
    public String getNo(String number) {
        Random random = new Random();
        return String.format("%s%s%s%s", heard, random.nextInt(1000), System.currentTimeMillis(), number);
    }
}
