package com.dnd.MusicLog.global.jwt.util;

public interface JwtProperties {

    String BEARER_TYPE = "Bearer";
    String ACCESS_TOKEN_TYPE = "access";
    String REFRESH_TOKEN_TYPE = "refresh";
    long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;            // 30분
    long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일

}
