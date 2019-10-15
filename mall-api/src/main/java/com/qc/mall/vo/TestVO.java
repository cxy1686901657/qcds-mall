package com.qc.mall.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author qc
 * @date 2019/9/22
 * @description
 * @project mall
 */
@Data
@Builder
public class TestVO implements Serializable {
    private String name;
    private String id;
    private String password;
}
