package com.qc.mall.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qc.mall.bean.UmsMember;
import com.qc.mall.bean.UmsMemberReceiveAddress;
import com.qc.mall.service.UserService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qc
 * @date 2019/8/22
 * @description
 * @project mall
 */
@RestController
@CrossOrigin
@Api(value = "用户接口", description = "用户接口")
public class UserController {
    @Reference
    UserService userService;

    @GetMapping("getReceiveAddressByMemberId")
    @ResponseBody
    @ApiOperation(value = "返回用户所有收获地址", notes = "返回用户所有收获地址")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "error")
    })
    @ApiImplicitParam(name = "memberId",value = "用户id")
    public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId){
        List<UmsMemberReceiveAddress> umsMemberReceiveAddresses = userService.getReceiveAddressByMemberId(memberId);
        return umsMemberReceiveAddresses;
    }


    @GetMapping("getAllUser")
    public List<UmsMember> getAllUser(){
        List<UmsMember> umsMembers = userService.getAll();
        return umsMembers;
    }
}
