package com.qc.mall.search.util;

import com.qc.mall.parm.PmsSearchParam;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

/**
 * @author qc
 * @date 2019/9/16
 * @description
 * @project mall
 */

public class ParamUtil {
    private ParamUtil() {
    }

    public static String getUrlParam(@NonNull PmsSearchParam pmsSearchParam, String... deleteId) {
        String keyword = pmsSearchParam.getKeyword();
        String catalog3Id = pmsSearchParam.getCatalog3Id();
        String[] skuAttrValueList = pmsSearchParam.getValueId();
        String urlParam = "";

        if (StringUtils.isNotBlank(keyword)) {
            urlParam = urlParam + "&keyword=" + keyword;
        }

        if (StringUtils.isNotBlank(catalog3Id)) {
            urlParam = urlParam + "&catalog3Id=" + catalog3Id;
        }


        if (skuAttrValueList != null) {
            if (deleteId.length==0) {
                for (String pmsSkuAttrValue : skuAttrValueList) {
                    urlParam = urlParam + "&valueId=" + pmsSkuAttrValue;
                }
            } else {
                for (String pmsSkuAttrValue : skuAttrValueList) {
                    if (!deleteId[0].equals(pmsSkuAttrValue)) {
                        urlParam = urlParam + "&valueId=" + pmsSkuAttrValue;
                    }
                }
            }
        }
        if (urlParam.startsWith("&")) {
            urlParam = urlParam.substring(1, urlParam.length());
        }
        return urlParam;
    }
}
