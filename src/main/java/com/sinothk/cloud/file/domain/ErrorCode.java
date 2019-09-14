package com.sinothk.cloud.file.domain;

import java.util.HashMap;

public class ErrorCode {
    // TOKEN
    public static final int TOKEN_NOT_EMPTY = 1001;
    public static final int TOKEN_ERROR = 1002;
    // 数据

    private static HashMap<Integer, String> keyValue = null;

    public static String getValue(int code) {

        if (keyValue == null) {
            keyValue = new HashMap<>();
            keyValue.put(TOKEN_NOT_EMPTY, "token为空");
            keyValue.put(TOKEN_ERROR, "token失效，请尝试重新登录");
        }
        return keyValue.get(code);
    }
}
