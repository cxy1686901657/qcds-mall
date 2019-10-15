import com.alibaba.fastjson.JSON;
import com.qc.mall.util.JwtUtil;
import io.jsonwebtoken.impl.Base64UrlCodec;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qc
 * @date 2019/9/22
 * @description
 * @project mall
 */

public class TestJwt {
    public static void main(String[] args) {
//        Map<String,Object> map = new HashMap<>();
//        map.put("memberId","1");
//        map.put("nickname","zhangsan");
//        String ip = "127.0.0.1";
//        String time = new SimpleDateFormat("yyyyMMdd HHmm").format(new Date());
//        String encode = JwtUtil.encode("2019gmall0105", map, ip + time);
//        System.err.println(encode);
//
//
//
//        // String tokenUserInfo = StringUtils.substringBetween(encode, ".");
//        Base64UrlCodec base64UrlCodec = new Base64UrlCodec();
//        byte[] tokenBytes = base64UrlCodec.decode("eyJuaWNrbmFtZSI6IndpbmRpciIsIm1lbWJlcklkIjoiMSJ9");
//        String tokenJson = null;
//        try {
//            tokenJson = new String(tokenBytes, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        Map map1 = JSON.parseObject(tokenJson, Map.class);
//        System.out.println("64="+map1);
        Base64UrlCodec base64UrlCodec = new Base64UrlCodec();
        byte[] tokenBytes = base64UrlCodec.decode("eyJuaWNrbmFtZSI6IueUqOaItzYwMjEzMDMyMDMiLCJtZW1iZXJJZCI6IjI5In0");
        String tokenJson = null;
        try {
            tokenJson = new String(tokenBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map map1 = JSON.parseObject(tokenJson, Map.class);
        System.out.println("64="+map1);
        Map<String, Object> decode = JwtUtil.decode("eyJhbGciOiJIUzI1NiJ9.eyJuaWNrbmFtZSI6IueUqOaItzYwMjEzMDMyMDMiLCJtZW1iZXJJZCI6IjI5In0.9lHlwwJL-4Z9b3LOgpianYSl9VUJM2_R_D3__FUPQDk", "mall", "0:0:0:0:0:0:0:1");
        System.out.println(decode);
    }
}
