package com.qc.mall.passport.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.qc.mall.bean.UmsMember;
import com.qc.mall.consts.OauthConst;
import com.qc.mall.enums.SourceTypeEnum;
import com.qc.mall.service.UserService;
import com.qc.mall.util.JwtUtil;
import com.qc.mall.util.NetworkUtil;
import com.qc.mall.utils.HttpclientUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qc
 * @date 2019/9/22
 * @description
 * @project mall
 */
@Controller
public class PassportController {

    @Reference
    UserService userService;

    @RequestMapping("glogin")
    public String glogin(String code, String ReturnUrl, HttpServletRequest request) {
        String url = "https://github.com/login/oauth/access_token";
        Map<String, String> param = new HashMap<>();
        param.put("client_id", OauthConst.Github.CLIENT_ID);
        param.put("client_secret", OauthConst.Github.SECRET_ID);
        param.put("state", "use-login");
        param.put("redirect_uri", OauthConst.Github.REDIRECT_URI);
        param.put("code", code);// 授权有效期内可以使用，没新生成一次授权码，说明用户对第三方数据进行重启授权，之前的access_token和授权码全部过期
        String access_token_json = HttpclientUtil.doPost(url, param);

        /**
         * 以 & 为分割字符分割 result
         */
        String[] githubResultList = access_token_json.split("&");
        List<String> params = new ArrayList<>();
        // paramMap 是分割后得到的参数对, 比说 access_token=ad5f4as6gfads4as98fg
        for (String paramMap : githubResultList) {
            if (!"scope".equals(paramMap.split("=")[0])) {
                // 再以 = 为分割字符分割, 并加到 params 中
                params.add(paramMap.split("=")[1]);
            }
        }
        String githubInfoResult = HttpclientUtil.doGet("https://api.github.com/user?access_token=" + params.get(0));
        Map<String, Object> user_map = JSON.parseObject(githubInfoResult, Map.class);
        // 将用户信息保存数据库，用户类型设置为微博用户
        System.out.println(user_map.get("id"));
        UmsMember umsMember = UmsMember.builder()
                .sourceType(SourceTypeEnum.Github.getTypeId())
                .accessToken(params.get(0))
                .accessCode(code)
                .sourceUid(user_map.get("id").toString())
                .city(user_map.get("location") + " ")
                .nickname((String) user_map.get("name"))
                .gender(0)
                .build();

        UmsMember umsMemberCheck = userService.checkOauthUser(umsMember.getSourceUid());
        if (umsMemberCheck == null) {
            userService.addOauthUser(umsMember);
        } else {
            userService.updateUser(umsMember);
        }
        umsMember = userService.findUserByUid(umsMember.getSourceUid());
        String token = makeToken(umsMember.getId(), umsMember.getNickname(), request);

        userService.addUserToken(token, umsMember);
        return "redirect:http://localhost:8003/search/index?token=" + token;

    }

    /**
     * @param code
     * @return
     * @author qc
     * @description
     * @date 2019/9/26
     */
    @RequestMapping("vlogin")
    public String vlogin(String code, String ReturnUrl, HttpServletRequest request) {
        String s3 = "https://api.weibo.com/oauth2/access_token?";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("client_id", OauthConst.WeiBo.CLIENT_ID);
        paramMap.put("client_secret", OauthConst.WeiBo.SECRET_ID);
        paramMap.put("grant_type", "authorization_code");
        paramMap.put("redirect_uri", OauthConst.WeiBo.REDIRECT_URI);
        paramMap.put("code", code);// 授权有效期内可以使用，没新生成一次授权码，说明用户对第三方数据进行重启授权，之前的access_token和授权码全部过期
        String access_token_json = HttpclientUtil.doPost(s3, paramMap);

        Map<String, Object> access_map = JSON.parseObject(access_token_json, Map.class);

        // access_token换取用户信息
        String uid = (String) access_map.get("uid");
        String access_token = (String) access_map.get("access_token");
        String show_user_url = "https://api.weibo.com/2/users/show.json?access_token=" + access_token + "&uid=" + uid;
        String user_json = HttpclientUtil.doGet(show_user_url);
        Map<String, Object> user_map = JSON.parseObject(user_json, Map.class);
        String g = "0";
        String gender = (String) user_map.get("gender");
        if (gender.equals("m")) {
            g = "1";
        }
        // 将用户信息保存数据库，用户类型设置为微博用户
        UmsMember umsMember = UmsMember.builder()
                .sourceType(SourceTypeEnum.WEIBO.getTypeId())
                .accessToken(access_token)
                .accessCode(code)
                .sourceUid((String) user_map.get("idstr"))
                .city((String) user_map.get("location"))
                .nickname((String) user_map.get("screen_name"))
                .gender(Integer.parseInt(g))
                .build();

        UmsMember umsMemberCheck = userService.checkOauthUser(umsMember.getSourceUid());

        if (umsMemberCheck == null) {
            userService.addOauthUser(umsMember);
        } else {
            userService.updateUser(umsMember);
        }
        umsMember = userService.findUserByUid(umsMember.getSourceUid());
        String token = makeToken(umsMember.getId(), umsMember.getNickname(), request);

        userService.addUserToken(token, umsMember);

        return "redirect:http://localhost:8003/search/index?token=" + token;
    }


    @GetMapping("verify")
    @ResponseBody
    public String verify(String token, String currentIp) {

        // 通过jwt校验token真假
        Map<String, String> map = new HashMap<>();

        Map<String, Object> decode = JwtUtil.decode(token, "mall", currentIp);
        if (decode != null) {
            map.put("status", "success");
            map.put("memberId", (String) decode.get("memberId"));
            map.put("nickname", (String) decode.get("nickname"));
        } else {
            map.put("status", "fail");
        }

        return JSON.toJSONString(map);
    }


    @RequestMapping("login")
    @ResponseBody
    public String login(UmsMember umsMember, HttpServletRequest request) {

        String token = "";
        // 调用用户服务验证用户名和密码
        UmsMember umsMemberLogin = userService.login(umsMember);

        if (umsMemberLogin != null) {
            // 登录成功

            // 按照设计的算法对参数进行加密后，生成token
            token = makeToken(umsMemberLogin.getId(), umsMemberLogin.getNickname(), request);

            // 将token存入redis一份
            userService.addUserToken(token, umsMemberLogin);

        } else {
            // 登录失败
            token = "fail";
        }
        return token;
    }

    @GetMapping("index")
    public String index(String ReturnUrl, ModelMap map) {
        if (StringUtils.isNotBlank(ReturnUrl)) {
            map.put("ReturnUrl", ReturnUrl);
        }
        return "index";
    }

    private String makeToken(String id, String nickName, HttpServletRequest request) {
        // 用jwt制作token
        String memberId = id;
        String nickname = nickName;
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("memberId", memberId);
        userMap.put("nickname", nickname);

//        String ip = request.getHeader("x-forwarded-for");// 通过nginx转发的客户端ip
//        if(StringUtils.isBlank(ip)){
//            ip = request.getRemoteAddr();// 从request中获取ip
//            if(StringUtils.isBlank(ip)){
//                ip = "0:0:0:0:0:0:0:1";
//            }
//        }

        // 按照设计的算法对参数进行加密后，生成token
//        return JwtUtil.encode("mall", userMap, NetworkUtil.getIpAddress(request));
        return JwtUtil.encode("mall", userMap, "127.0.0.1");
    }
}
