package com.qc.mall.service;

import com.qc.mall.bean.UmsMember;
import com.qc.mall.bean.UmsMemberReceiveAddress;

import java.util.List;

/**
 * @author qc
 * @date 2019/8/22
 * @description
 * @project mall
 */
public interface UserService {
    List<UmsMember> getAll();

    List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String usermemberId);

    UmsMember login(UmsMember umsMember);

    void addUserToken(String token, UmsMember umsMemberLogin);

    void addOauthUser(UmsMember umsMember);

    UmsMember checkOauthUser(String sourceUid);

    void updateUser(UmsMember umsMember);

    UmsMember findUserByUid(String sourceUid);

    UmsMemberReceiveAddress getReceiveAddressById(String receiveAddressId);
}
