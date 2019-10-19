package com.qc.mall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.qc.mall.bean.PmsSkuAttrValue;
import com.qc.mall.bean.PmsSkuImage;
import com.qc.mall.bean.PmsSkuInfo;
import com.qc.mall.bean.PmsSkuSaleAttrValue;
import com.qc.mall.consts.MqQueueConst;
import com.qc.mall.consts.RedisConst;
import com.qc.mall.manage.mapper.PmsSkuAttrValueMapper;
import com.qc.mall.manage.mapper.PmsSkuImageMapper;
import com.qc.mall.manage.mapper.PmsSkuInfoMapper;
import com.qc.mall.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.qc.mall.service.SearchService;
import com.qc.mall.service.SkuService;
import com.qc.mall.util.ActiveMQUtil;
import com.qc.mall.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author qc
 * @date 2019/8/26
 * @description
 * @project mall
 */
@Service
@Component
@Slf4j
@Transactional
public class SkuServiceImpl implements SkuService {
    @Autowired
    PmsSkuInfoMapper pmsSkuInfoMapper;
    @Autowired
    PmsSkuImageMapper pmsSkuImageMapper;
    @Autowired
    PmsSkuAttrValueMapper pmsSkuAttrValueMapper;
    @Autowired
    PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;
    @Autowired
    RedisUtil redisUtil;
    @Reference
    SearchService searchService;
    @Autowired
    ActiveMQUtil activeMQUtil;

    @Override
    public void saveSkuInfo(PmsSkuInfo pmsSkuInfo) {
        pmsSkuInfoMapper.insertSelective(pmsSkuInfo);
        PmsSkuInfo pmsSkuInfo1 = pmsSkuInfoMapper.selectBySkuName(pmsSkuInfo.getSkuName());
        pmsSkuInfo.setId(pmsSkuInfo1.getId());
        List<PmsSkuImage> skuImageList = pmsSkuInfo.getSkuImageList();
        //images
        skuImageList.stream().forEach(x -> {
            x.setSkuId(pmsSkuInfo1.getId());
            pmsSkuImageMapper.insertSelective(x);
        });

        //skuattrvaluelist
        List<PmsSkuAttrValue> skuAttrValueList = pmsSkuInfo.getSkuAttrValueList();
        skuAttrValueList.stream().forEach(x -> {
            x.setSkuId(pmsSkuInfo1.getId());
            pmsSkuAttrValueMapper.insertSelective(x);
        });
        //skusaleattrvaluelist
        List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();
        skuSaleAttrValueList.stream().forEach(x -> {
            x.setSkuId(pmsSkuInfo1.getId());
            pmsSkuSaleAttrValueMapper.insertSelective(x);
        });
        //
        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("pmsSkuInfo", JSON.toJSONString(pmsSkuInfo));
        activeMQUtil.sendTransactedMapMessage(MqQueueConst.SKU_ADDTO_ES, objectObjectHashMap);
        log.info("SKU_ADDTO_ES消息发送成功+参数{}", pmsSkuInfo);
    }

    @Override
    public PmsSkuInfo getSkuById(String skuId) {
        PmsSkuInfo pmsSkuInfo;
        Jedis jedis = redisUtil.getJedis();
        String skuKey = RedisConst.prefix + skuId + RedisConst.sufix;
        String skuJson = jedis.get(skuKey);
        if (StringUtils.isNotBlank(skuJson)) {
            pmsSkuInfo = JSON.parseObject(skuJson, PmsSkuInfo.class);
        } else {
            String token = UUID.randomUUID().toString();
            String OK = jedis.set("sku:" + skuId + ":lock", token, "nx", "px", 10 * 1000);
            if (StringUtils.isNotBlank(OK) && OK.equals("OK")) {
                pmsSkuInfo = getSkuByIdFromDB(skuId);
                if (pmsSkuInfo == null) {
                    jedis.setex(skuKey, 60 * 2, "");
                } else {
                    jedis.setex(skuKey, 3600, JSON.toJSONString(pmsSkuInfo));
                }
                //lua脚本
                String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                jedis.eval(script, Collections.singletonList(RedisConst.SKU_LOCK.prefix + skuId + RedisConst.SKU_LOCK.sufix),
                        Collections.singletonList(token));

                //会存在某一瞬间 刚好判断完 锁过期 删除别人的锁。
//                String lockToken = jedis.get("sku:" + skuId + ":lock");
//                if(StringUtils.isNotBlank(lockToken)&&lockToken.equals(token)){
//                    jedis.del("sku:" + skuId + ":lock");// 用token确认删除的是自己的sku的锁
//                }
            } else {
                // 设置失败，自旋（该线程在睡眠几秒后，重新尝试访问本方法）
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return getSkuById(skuId);
            }
        }
        jedis.close();
        return pmsSkuInfo;

    }

    public PmsSkuInfo getSkuByIdFromDB(String skuId) {
        PmsSkuInfo pmsSkuInfo = pmsSkuInfoMapper.selectByPrimaryKey(skuId);
        pmsSkuInfo.setSkuImageList(pmsSkuImageMapper.selectBySkuId(skuId));
        return pmsSkuInfo;
    }

    @Override
    public String getSkuSaleAttrValueListBySpu(String spuId) {
        String skuSaleAttrHashJsonStr = null;
        Jedis jedis = null;
        try {
            jedis = redisUtil.getJedis();
            String s = jedis.get(RedisConst.SPU_SKU_HASH.prefix + spuId);
            if (StringUtils.isNotBlank(s)) {
                log.info("缓存命中");
                return s;
            }
            List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.selectSkuSaleAttrValueListBySpu(spuId);
            Map<String, String> skuSaleAttrHash = new HashMap<>();
            pmsSkuInfos.stream().forEach(x -> {
                String k = "";
                String v = x.getId();

                List<PmsSkuSaleAttrValue> skuSaleAttrValueList = x.getSkuSaleAttrValueList();
                for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
                    k += pmsSkuSaleAttrValue.getSaleAttrValueId() + "|";// "239|245"
                }
                skuSaleAttrHash.put(k, v);
            });
            skuSaleAttrHashJsonStr = JSON.toJSONString(skuSaleAttrHash);
            jedis.setex(RedisConst.SPU_SKU_HASH.prefix+spuId, 36000, skuSaleAttrHashJsonStr);
            log.info("缓存插入成功{}",skuSaleAttrHash);
        } catch (Exception e) {
            log.error("缓存更新异常{}", e);
        } finally {
            jedis.close();
        }
        return skuSaleAttrHashJsonStr;
    }

    @Override
    public List<PmsSkuInfo> getAll() {
        List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.getAll();

        pmsSkuInfos.stream().forEach(x -> {
            x.setSkuAttrValueList(pmsSkuAttrValueMapper.selectBySkuId(x.getId()));
        });
        return pmsSkuInfos;
    }

    @Override
    public boolean checkPrice(String productSkuId, BigDecimal price) {
        boolean b = false;
        PmsSkuInfo pmsSkuInfo = pmsSkuInfoMapper.selectByPrimaryKey(productSkuId);

        BigDecimal productPrice = pmsSkuInfo.getPrice();

        if (price.compareTo(productPrice) == 0) {
            b = true;
        }
        return b;
    }

}
