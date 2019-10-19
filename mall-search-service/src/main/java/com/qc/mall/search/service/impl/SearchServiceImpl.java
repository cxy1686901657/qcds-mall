package com.qc.mall.search.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.qc.mall.bean.PmsSearchSkuInfo;
import com.qc.mall.bean.PmsSkuAttrValue;
import com.qc.mall.bean.PmsSkuInfo;
import com.qc.mall.consts.ESConst;
import com.qc.mall.parm.PmsSearchParam;
import com.qc.mall.service.SearchService;
import com.qc.mall.service.SkuService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author qc
 * @date 2019/9/12
 * @description
 * @project mall
 */
@Component
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Autowired
    JestClient jestClient;

    @Override
    public List<PmsSearchSkuInfo> list(PmsSearchParam pmsSearchParam) {
        String dsl=getSearchDsl(pmsSearchParam);

        Search build = new Search.Builder(dsl).addIndex(ESConst.INDEX).addType(ESConst.TPYE).build();

        log.info("执行查询：dsl={}",build);

        List<PmsSearchSkuInfo> pmsSearchSkuInfos = new ArrayList<>();

        SearchResult execute=null;
        try {
             execute = jestClient.execute(build);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = execute.getHits(PmsSearchSkuInfo.class);

        hits.stream().forEach(x->{
            Map<String, List<String>> highlight = x.highlight;
            if(x.highlight!=null){
                x.source.setSkuName(highlight.get("skuName").get(0));
            }
            pmsSearchSkuInfos.add(x.source);
        });
        Collections.sort(pmsSearchSkuInfos);
//        log.info("paixuhou  ++{}", JSON.toJSON(pmsSearchSkuInfos));
        return pmsSearchSkuInfos;
    }

    @Override
    public void sync(PmsSkuInfo pmsSkuInfo) throws Exception{
//        // 查询mysql数据
//        List<PmsSkuInfo> pmsSkuInfoList ;
//
//        pmsSkuInfoList = skuService.getAll();

        // 转化为es的数据结构
//        List<PmsSearchSkuInfo> pmsSearchSkuInfos = new ArrayList<>();

//        for (PmsSkuInfo pmsSkuInfo : pmsSkuInfoList) {
            PmsSearchSkuInfo pmsSearchSkuInfo = new PmsSearchSkuInfo();
            BeanUtils.copyProperties(pmsSearchSkuInfo, pmsSkuInfo);
//            pmsSearchSkuInfos.add(pmsSearchSkuInfo);
//        }

        // 导入es
//        for (PmsSearchSkuInfo pmsSearchSkuInfo : pmsSearchSkuInfos) {
            Index put = new Index.Builder(pmsSearchSkuInfo).index(ESConst.INDEX).type(ESConst.TPYE).id(pmsSearchSkuInfo.getId()+"").build();
            jestClient.execute(put);
//        }
    }


    private String getSearchDsl(PmsSearchParam pmsSearchParam) {

        String[] skuAttrValueList = pmsSearchParam.getValueId();
        String keyword = pmsSearchParam.getKeyword();
        String catalog3Id = pmsSearchParam.getCatalog3Id();

        // jest的dsl工具
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // bool
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        // filter
        if(StringUtils.isNotBlank(catalog3Id)){
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("catalog3Id",catalog3Id);
            boolQueryBuilder.filter(termQueryBuilder);
        }
        if(skuAttrValueList!=null){
            for (String pmsSkuAttrValue : skuAttrValueList) {
                TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuAttrValueList.valueId",pmsSkuAttrValue);
                boolQueryBuilder.filter(termQueryBuilder);
            }
        }

        // must
        if(StringUtils.isNotBlank(keyword)){
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName",keyword);
            boolQueryBuilder.must(matchQueryBuilder);
        }

        // query
        searchSourceBuilder.query(boolQueryBuilder);

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span style='color:red;'>");
        highlightBuilder.field("skuName");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);
//        // sort
//        searchSourceBuilder.sort("id",SortOrder.DESC);
        // from
        searchSourceBuilder.from(0);
        // size
        searchSourceBuilder.size(20);

        return searchSourceBuilder.toString();

    }





}
