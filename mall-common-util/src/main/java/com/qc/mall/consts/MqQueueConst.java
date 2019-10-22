package com.qc.mall.consts;

/**
 * @author qc
 * @date 2019/10/9
 * @description mq的队列
 * @project mall
 */
public interface MqQueueConst {
    //购物车更新添加的更新缓存
    String UPDATE_CARTCACHE="UPDATE_CARTCACHE";

    //提交订单的延迟检查(支付服务)->(延迟队列)
    String PAYMENT_CHECK_QUEUE="PAYMENT_CHECK_QUEUE";

    //支付完成(支付服务)->订单已支付(订单服务)
    String PAYHMENT_SUCCESS_QUEUE="PAYHMENT_SUCCESS_QUEUE";

    //添加sku到搜索队列
    String SKU_ADDTO_ES="SKU_ADDTO_ES";

    //订单已支付(订单服务)->库存锁定(库存系统)
    String ORDER_PAY_QUEUE="ORDER_PAY_QUEUE";

    //库存锁定(库存系统)->订单已出库(订单服务)
    String SKU_DEDUCT_QUEUE="SKU_DEDUCT_QUEUE";

    //订单已出库(订单服务)->(订单服务)
    String ORDER_SUCCESS_QUEUE="ORDER_SUCCESS_QUEUE";


    String INCR_HOTSCORE="INCR_HOTSCORE";

    //添加sku到缓存队列
    String SKU_ADDTO_CACHE="SKU_ADDTO_CACHE";

    //更新购物车，（登录）
    String UPDATE_CART="UPDATE_CART";
}
