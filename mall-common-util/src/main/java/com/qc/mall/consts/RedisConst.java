package com.qc.mall.consts;

/**
 * @author qc
 * @date 2019/8/29
 * @description
 * @project mall
 */
public interface RedisConst {

    interface INFO {
        String prefix = "sku:";
        String sufix = ":info";
    }

    interface TOKEN {
        String prefix = "user:";
        String sufix = ":token";
    }

    interface SKU_LOCK {
        String prefix = "sku:";
        String sufix = ":lock";
    }

    interface TRADECODE{
        String prefix = "user:";
        String sufix = ":tradeCode";
    }


    String prefix = "sku:";
    String sufix = ":info";
}
