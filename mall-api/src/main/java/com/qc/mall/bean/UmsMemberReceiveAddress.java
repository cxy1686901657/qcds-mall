package com.qc.mall.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qc
 * @date 2019/8/22
 * @description
 * @project mall
 */
@Data
public class UmsMemberReceiveAddress implements Serializable {
    private String id;

    private String memberId;

    private String name;

    private String phoneNumber;

    private Integer defaultStatus;

    private String postCode;

    private String province;

    private String city;

    private String region;

    private String detailAddress;

}
