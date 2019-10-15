package com.qc.mall.bean;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author qc
 * @date 2019/9/17
 * @description
 * @project mall
 */
@Data
public class OmsCartItem implements Serializable {

    private String id;

    private String productId;

    private String productSkuId;

    private String memberId;

    private int quantity;

    private BigDecimal price;

    private String sp1;

    private String sp2;

    private String sp3;

    private String productPic;

    private String productName;

    private String productSubTitle;

    private String productSkuCode;

    private String memberNickname;

    private Date createDate;

    private Date modifyDate;

    private Integer deleteStatus;

    private String productCategoryId;

    private String productBrand;

    private String productSn;

    private String productAttr;

    private String isChecked;

    private BigDecimal totalPrice;
}
