package com.dxy.letterboxbackstage.common.constant;

public class JwtConstant {

    /**
     * token
     */
    public static final int JWT_ERRCODE_NULL = 4000; //Token不存在
    public static final int JWT_ERRCODE_EXPIRE = 4001; //Token过期
    public static final int JWT_ERRCODE_FAIL = 4002; //验证不通过

    /**
     * JWT
     */
    public static final String JWT_SECERT = "private key"; //密匙
    public static final long JWT_TTL = 60*60*1000; //token有效时间
}
