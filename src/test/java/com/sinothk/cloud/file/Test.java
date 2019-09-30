package com.sinothk.cloud.file;

import com.sinothk.base.utils.TokenUtil;

public class Test {

    public static void main(String[] strs) {
        System.out.println(TokenUtil.createToken(TokenUtil.EXPIRE_TIME_15D, "admin"));
    }
}
