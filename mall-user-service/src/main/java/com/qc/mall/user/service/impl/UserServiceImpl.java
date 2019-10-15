package com.qc.mall.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.qc.mall.bean.UmsMember;
import com.qc.mall.bean.UmsMemberReceiveAddress;
import com.qc.mall.consts.RedisConst;
import com.qc.mall.service.UserService;
import com.qc.mall.user.mapper.UmsMemberMapper;
import com.qc.mall.user.mapper.UmsMemberReceiveAddressMapper;
import com.qc.mall.util.RedisUtil;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author qc
 * @date 2019/8/22
 * @description
 * @project mall
 */
@Service
@Component
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UmsMemberMapper umsMemberMapper;

    @Autowired
    UmsMemberReceiveAddressMapper umsMemberReceiveAddressMapper;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public List<UmsMember> getAll() {
        return umsMemberMapper.getAllUmsMember();
    }

    @Override
    public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String usermemberId) {
        return umsMemberReceiveAddressMapper.selectByMemberId(usermemberId);
    }

    @Override
    public UmsMember login(UmsMember umsMember) {
        String key=umsMember.getUsername()+umsMember.getPassword();
        Jedis jedis = null;
        try {
            jedis=redisUtil.getJedis();
            if(jedis!=null){
                String umsMemberStr = jedis.get(RedisConst.INFO.prefix + key + RedisConst.INFO.sufix);
                if (StringUtils.isNotBlank(umsMemberStr)) {
                    // 密码正确
                    UmsMember umsMemberFromCache = JSON.parseObject(umsMemberStr, UmsMember.class);
                    log.info("从缓存获取用户信息{}",umsMemberFromCache);
                    return umsMemberFromCache;
                }
            }
            UmsMember umsMemberFromDb =loginFromDb(umsMember);
            if(umsMemberFromDb!=null){
                jedis.setex(RedisConst.INFO.prefix + key + RedisConst.INFO.sufix,60*60*24, JSON.toJSONString(umsMemberFromDb));
                log.info("从数据库获取用户信息{}",umsMemberFromDb);
            }
            return umsMemberFromDb;
        }finally {
            jedis.close();
        }
    }

    @Override
    public void addUserToken(String token, UmsMember umsMemberLogin) {
        @Cleanup Jedis jedis=redisUtil.getJedis();
        jedis.setex(RedisConst.TOKEN.prefix+umsMemberLogin.getId()+RedisConst.TOKEN.sufix, 60*60*2, token);
        log.info("向缓存中添加token{}",token);
    }

    @Override
    public void addOauthUser(UmsMember umsMember) {
        umsMemberMapper.insertSelective(umsMember);
    }

    @Override
    public UmsMember checkOauthUser(String sourceUid) {
        UmsMember umsMember=umsMemberMapper.selectByUid(sourceUid);
        return umsMember;
    }

    @Override
    public void updateUser(UmsMember umsMember) {
        umsMemberMapper.updateBySourceUid(umsMember);
    }

    @Override
    public UmsMember findUserByUid(String sourceUid) {
        return umsMemberMapper.selectByUid(sourceUid);
    }

    @Override
    public UmsMemberReceiveAddress getReceiveAddressById(String receiveAddressId) {
        UmsMemberReceiveAddress umsMemberReceiveAddress = umsMemberReceiveAddressMapper.selectByPrimaryKey(receiveAddressId);
        return umsMemberReceiveAddress;
    }

    private UmsMember loginFromDb(UmsMember umsMember) {
        List<UmsMember> umsMembers = umsMemberMapper.select(umsMember);
        if(umsMembers.size()>0){
            return umsMembers.get(0);
        }
        return null;
    }
}
