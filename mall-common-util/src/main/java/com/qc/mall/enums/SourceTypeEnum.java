package com.qc.mall.enums;



/**
 * @author qc
 * @date 2019/9/26
 * @description
 * @project mall
 */

public enum SourceTypeEnum {
    WEIBO(1,"微博"),
    QQ(2,"QQ"),
    Github(3,"Github");

    private Integer typeId;
    private String typeName;

    public Integer getTypeId() {
        return typeId;
    }

    SourceTypeEnum(Integer typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }


}
