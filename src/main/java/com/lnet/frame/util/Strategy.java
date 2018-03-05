package com.lnet.frame.util;

/**
 * Created by LH on 2016/12/30.
 */
public interface Strategy {

    /**
     * 根据number产生单号
     * @param number
     * @return
     */
    String getNo(String number);

}
