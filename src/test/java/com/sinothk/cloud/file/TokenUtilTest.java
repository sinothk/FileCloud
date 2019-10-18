package com.sinothk.cloud.file;

import com.sinothk.base.utils.TokenUtil;

public class TokenUtilTest {

    public static void main(String[] args) {

        String token = TokenUtil.createToken(TokenUtil.EXPIRE_TIME_15D, "admin");

//        HashMap<String, Object> map = new HashMap<>();
//        map.put("userAccount", 10000);
//        map.put("userName", "liangyt");
//        map.put("userRoles", new String[]{"Admin", "MANAGER"});
//
//        String token = TokenUtil.createToken(TokenUtil.EXPIRE_TIME_15D, map);

        System.out.println("token = " + token);

//        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnY2xhc3MiLCJ1c2VyTmFtZSI6ImxpYW5neXQiLCJleHAiOjE1Njg0Nzg2MTl9.Lpw7SoGUo7xZ0GoHfxubyvyReUdvk8osqDszF5mzB7Q";
//        Claims claims = TokenUtil.checkToken(token);
//
//        if (claims == null) {
//            // 错误token，超时都返回null
//            System.out.println("验证失败");
//        } else {
//            System.out.println("验证通过");
//            System.out.println("userName = " + TokenUtil.getUserName(token));
//        }
    }
}
