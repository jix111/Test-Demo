package utils;

import java.util.ArrayList;

public class Message {
    public static final String REGISTER = "REGISTER"; //登陆消息的类型
    public static final String LOGIN = "LOGIN"; //登陆消息的类型
    public static final String SPLIT = "&&"; //分隔符
    public static final String SUCCCESS = "SUCCCESS"; //成功
    public static final String FAILURE = "FAILURE"; //失败
    public static final String UNKNOWN = "UNKNOWN"; //未知

    // 注册验证
    public static String requestRegister(String userId, String userPassword) {
        return REGISTER + SPLIT + userId + SPLIT + userPassword;
    }

    // 登录验证
    // LOGIN&&10001&&123456
    public static String requestLogin(String userId, String userPassword) {
        return LOGIN + SPLIT + userId + SPLIT + userPassword;
    }

    // 注册响应结果
    public static String responseRegister(String result){
        return REGISTER + SPLIT + result;
    }
    // 登录响应结果
    public static String responseLogin(String result) {
        return LOGIN + SPLIT + result;
    }
}
