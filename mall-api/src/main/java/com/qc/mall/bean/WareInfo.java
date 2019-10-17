package com.qc.mall.bean;

import lombok.Data;

import java.io.Serializable;
@Data
public class WareInfo implements Serializable {
    private String id;

    private String name;

    private String address;

    private String areacode;

}