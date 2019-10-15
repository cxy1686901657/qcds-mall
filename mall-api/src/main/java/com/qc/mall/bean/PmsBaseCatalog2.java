package com.qc.mall.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author qc
 * @date 2019/8/22
 * @description
 * @project mall
 */
@Data
public class PmsBaseCatalog2 implements Serializable {
    private String id;

    private String name;

    private Integer catalog1Id;

    private List<PmsBaseCatalog3> catalog3List;
}