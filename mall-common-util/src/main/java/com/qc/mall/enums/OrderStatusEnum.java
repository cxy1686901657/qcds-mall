package com.qc.mall.enums;

/**
 * @author qc
 * @date 2019/10/9
 * @description
 * @project mall
 */
public enum OrderStatusEnum {
    WEIZHIFU(0,"未支付"),
    YIZHIFU(1,"已支付");

    private Integer typeId;
    private String typeName;

    public Integer getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    OrderStatusEnum(Integer typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }
}
