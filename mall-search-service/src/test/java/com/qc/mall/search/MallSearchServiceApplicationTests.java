package com.qc.mall.search;


import com.alibaba.dubbo.config.annotation.Reference;
import com.qc.mall.bean.PmsSearchSkuInfo;
import com.qc.mall.bean.PmsSkuInfo;
import com.qc.mall.service.SkuService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.beanutils.BeanUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MallSearchServiceApplicationTests {

    @Autowired
    JestClient jestClient;

    @Reference
    SkuService skuService;

    @Test
    public void contextLoads() throws IllegalAccessException, IOException, InvocationTargetException {
        put();
    }

    public void put() throws IOException, InvocationTargetException, IllegalAccessException {
        System.out.println(skuService);
        // 查询mysql数据
        List<PmsSkuInfo> pmsSkuInfoList ;

        pmsSkuInfoList = skuService.getAll();

        // 转化为es的数据结构
        List<PmsSearchSkuInfo> pmsSearchSkuInfos = new ArrayList<>();

        for (PmsSkuInfo pmsSkuInfo : pmsSkuInfoList) {
            PmsSearchSkuInfo pmsSearchSkuInfo = new PmsSearchSkuInfo();
            BeanUtils.copyProperties(pmsSearchSkuInfo, pmsSkuInfo);
            pmsSearchSkuInfos.add(pmsSearchSkuInfo);
        }

        // 导入es
        for (PmsSearchSkuInfo pmsSearchSkuInfo : pmsSearchSkuInfos) {
            Index put = new Index.Builder(pmsSearchSkuInfo).index("mallpms").type("PmsSkuInfo").id(pmsSearchSkuInfo.getId()+"").build();
            jestClient.execute(put);
        }

    }

    @Test
    public void get() throws Exception{
        String sql="{\n" +
                "  \"query\": {\n" +
                "    \"bool\": {\n" +
                "     \n" +
                "      \"must\": [\n" +
                "        {\n" +
                "          \"match\": {\n" +
                "            \"skuName\": \"1\"\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "      \n" +
                "      \n" +
                "    }\n" +
                "    \n" +
                "    \n" +
                "  }\n" +
                "}";
        Search build = new Search.Builder(sql).addIndex("mallpms").addType("PmsSkuInfo").build();

        ArrayList<PmsSearchSkuInfo> pmsSearchSkuInfos = new ArrayList<>();

        SearchResult execute = jestClient.execute(build);
        List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = execute.getHits(PmsSearchSkuInfo.class);
        hits.stream().forEach(x->{
            pmsSearchSkuInfos.add(x.source);
        });
        System.out.println(pmsSearchSkuInfos.size());
    }
    @Test
    public void getByDsl() throws Exception{

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();

        TermQueryBuilder termQueryBuilder=new TermQueryBuilder("skuAttrValueList.valueId","139");

        boolQueryBuilder.filter(termQueryBuilder);

        MatchQueryBuilder matchQueryBuilder=new MatchQueryBuilder("skuName", "12321");

        boolQueryBuilder.must(matchQueryBuilder);

        searchSourceBuilder.query(boolQueryBuilder);

        searchSourceBuilder.from(0);

        searchSourceBuilder.size(20);

        searchSourceBuilder.highlighter(null);

        String dslStr = searchSourceBuilder.toString();

        System.err.println(dslStr);

        Search build = new Search.Builder(dslStr).addIndex("mallpms").addType("PmsSkuInfo").build();

        SearchResult execute = jestClient.execute(build);

        List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = execute.getHits(PmsSearchSkuInfo.class);

        ArrayList<PmsSearchSkuInfo> pmsSearchSkuInfos = new ArrayList<>();

        hits.stream().forEach(x->{
            pmsSearchSkuInfos.add(x.source);
        });

        System.out.println(pmsSearchSkuInfos);
    }
}
