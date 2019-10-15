package com.qc.mall.util;

import com.qc.mall.bean.OmsCartItem;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author qc
 * @date 2019/9/28
 * @description 金额计算
 * @project mall
 */

public class AmountUtil {
    private AmountUtil(){ }
    public static BigDecimal getTotalAmount(List<OmsCartItem> omsCartItems) {
        BigDecimal totalAmount = new BigDecimal("0");

        for (OmsCartItem omsCartItem : omsCartItems) {
            BigDecimal totalPrice = omsCartItem.getTotalPrice();

            if(omsCartItem.getIsChecked().equals("1")){
                totalAmount = totalAmount.add(totalPrice);
            }
        }

        return totalAmount;
    }
}
