package com.qc.mall.consts;

/**
 * @author qc
 * @date 2019/9/26
 * @description
 * @project mall
 */

public interface OauthConst {

    //weibo开放平台社交登录
    interface WeiBo {

        String CLIENT_ID = "2040288732";

        String SECRET_ID = "a99ba3fcf83c28fb06001ec71a166742";
        //回调地址
        String REDIRECT_URI = "http://passport.mall.com:8005/vlogin";
    }
    //QQ开放平台社交登录
    interface QQ {



    }

    //Github开放平台社交登录
    interface Github {

        String CLIENT_ID = "5cd47cd2dea6297483bd";

        String SECRET_ID = "484ec67b3355c9efffa60c7418b58d97d803b56e";
        //回调地址
        String REDIRECT_URI = "http://passport.mall.com:8005/glogin";
    }
}
