package com.qc.mall.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qc.mall.annotations.LoginRequired;
import com.qc.mall.bean.PmsBaseAttrInfo;
import com.qc.mall.bean.PmsBaseAttrValue;
import com.qc.mall.bean.PmsSearchSkuInfo;
import com.qc.mall.bean.PmsSkuAttrValue;
import com.qc.mall.parm.PmsSearchCrumb;
import com.qc.mall.parm.PmsSearchParam;
import com.qc.mall.search.util.ParamUtil;
import com.qc.mall.service.AttrService;
import com.qc.mall.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * @author qc
 * @date 2019/9/12
 * @description
 * @project mall
 */
@Controller
@RequestMapping("/search")
public class SearchController {

    @Reference
    SearchService searchService;

    @Reference
    AttrService attrService;

    @RequestMapping("list.html")
    public String list(PmsSearchParam pmsSearchParam, ModelMap modelMap){// 三级分类id、关键字、

        // 调用搜索服务，返回搜索结果
        List<PmsSearchSkuInfo> pmsSearchSkuInfos =  searchService.list(pmsSearchParam);
        modelMap.put("skuLsInfoList",pmsSearchSkuInfos);
        // 抽取检索结果锁包含的平台属性集合
        HashSet<String> valueIds = new HashSet<>();
        pmsSearchSkuInfos.stream().forEach(x->{
            x.getSkuAttrValueList().stream().forEach(y->{
                valueIds.add(y.getValueId());
            });
        });
        // 根据valueId将属性列表查询出来
        if(valueIds.size()>0){
            List<PmsBaseAttrInfo> pmsBaseAttrInfos = attrService.getAttrValueListByValueId(valueIds);
            modelMap.put("attrList", pmsBaseAttrInfos);
            // 对平台属性集合进一步处理，去掉当前条件中valueId所在的属性组
            String[] delValueIds = pmsSearchParam.getValueId();
            if (delValueIds != null) {
                Iterator<PmsBaseAttrInfo> iterator = pmsBaseAttrInfos.iterator();
                List<PmsSearchCrumb> pmsSearchCrumbs = new ArrayList<>();

                while (iterator.hasNext()){
                    PmsBaseAttrInfo pmsBaseAttrInfo = iterator.next();
                    List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
                    attrValueList.stream().forEach(x->{
                        for(String del:delValueIds){
                            if(del.equals(x.getId())){
                                PmsSearchCrumb pmsSearchCrumb = new PmsSearchCrumb();
                                pmsSearchCrumb.setValueId(del);
                                pmsSearchCrumb.setUrlParam(ParamUtil.getUrlParam(pmsSearchParam, del));
                                pmsSearchCrumb.setValueName(x.getValueName());
                                pmsSearchCrumbs.add(pmsSearchCrumb);
                                iterator.remove();
                            }
                        }
                    });

                }
                modelMap.put("attrValueSelectedList", pmsSearchCrumbs);
            }

//            // 面包屑
//            if (delValueIds != null) {
//                List<PmsSearchCrumb> pmsSearchCrumbs = new ArrayList<>();
//                for (String delValueId : delValueIds) {
//                    PmsSearchCrumb pmsSearchCrumb = new PmsSearchCrumb();
//                    // 生成面包屑的参数
//                    pmsSearchCrumb.setValueId(delValueId);
//                    pmsSearchCrumb.setValueName("");
//                    pmsSearchCrumb.setUrlParam("222");
//                    pmsSearchCrumbs.add(pmsSearchCrumb);
//                }
//                modelMap.put("attrValueSelectedList", pmsSearchCrumbs);
//            }


        }

        String urlParam = ParamUtil.getUrlParam(pmsSearchParam);
        modelMap.put("urlParam", urlParam);
        String keyword = pmsSearchParam.getKeyword();
        if (StringUtils.isNotBlank(keyword)) {
            modelMap.put("keyword", keyword);
        }

        return "list";
    }


    @RequestMapping("index")
    @LoginRequired(loginSuccess = false)
    public String index(){
        return "index";
    }
}
