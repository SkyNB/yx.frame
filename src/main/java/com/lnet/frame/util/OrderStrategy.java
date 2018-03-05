package com.lnet.frame.util;

/**
 * Created by LH on 2016/12/30.
 */
public class OrderStrategy {
    public static final String LOGISTICS_ORDER = "logisticsOrder";//订单
    public static final String CONSIGN_ORDER = "consignOrder";//运输订单

    private Strategy strategy;

    private void confirmStrategy(OrderType orderType) {
        switch (orderType) {
            case LOGISTICS_ORDER:
                strategy = new LogisticsOrderUtil();
                break;
            case CONSIGN_ORDER:
                strategy = new ConsignOrderUtil();
                break;
            default:
                break;
        }
    }

    public String getNo(OrderType orderType, String orderNum) {
        confirmStrategy(orderType);
        return strategy.getNo(orderNum);
    }

    public enum OrderType {
        LOGISTICS_ORDER("订单"),
        CONSIGN_ORDER("运输订单");

        private String text;

        private OrderType(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
