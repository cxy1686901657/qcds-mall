package com.qc.mall;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qc
 * @date 2019/10/17
 * @description
 * @project qcds-mall
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result<T>  {
    private Integer code;
    private String msg;
    private T data;

}
