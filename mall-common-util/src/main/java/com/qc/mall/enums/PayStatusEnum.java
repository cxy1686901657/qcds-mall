package com.qc.mall.enums;

/**
 * @author qc
 * @date 2019/10/8
 * @description
 * @project mall
 */

public enum PayStatusEnum {
    WEIZHIFU(1,"未支付"),
    YIZHIFU(2,"已支付");

    private Integer typeId;
    private String typeName;

    public Integer getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    PayStatusEnum(Integer typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }
}
