package com.qc.mall;

import lombok.Getter;

/**
 * @author qc
 * @date 2019/10/17
 * @description
 * @project qcds-mall
 */
@Getter
public enum ResultStatus {
    SUCCESS(1,"success"),FAIL(2,"fail"),FORBIDDEN(3,"forbidden");
    private Integer code;
    private String content;

    ResultStatus(Integer code, String content) {
        this.code = code;
        this.content = content;
    }
}
