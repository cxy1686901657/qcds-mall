package com.qc.mall.service;

import com.qc.mall.bean.OmsCartItem;

import java.util.List;

/**
 * @author qc
 * @date 2019/9/17
 * @description
 * @project mall
 */
public interface CartService {
    OmsCartItem ifCartExistByUser(String memberId, String skuId);

    void addCart(OmsCartItem omsCartItem);

    void updateCart(OmsCartItem omsCartItemFromDb);

    void flushCartCache(String memberId);

    List<OmsCartItem> cartList(String memberId);

    void checkCart(OmsCartItem omsCartItem);
}
