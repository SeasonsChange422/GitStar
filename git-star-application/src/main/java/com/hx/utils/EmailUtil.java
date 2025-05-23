package com.hx.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dhx
 * @date 2025/1/8 18:25
 */
public class EmailUtil {
    public static Map<String, VerificationCodeData> verificationCodeMap = new HashMap<>();

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }

    public static boolean isValidVerificationCode(String email, String verificationCode) {
        if (!verificationCodeMap.containsKey(email)) return false;
        VerificationCodeData codeData = verificationCodeMap.get(email);
        if (codeData.isExpired()) {
            verificationCodeMap.remove(email); // 验证码已过期，移除
            return false;
        }
        boolean ret = codeData.getCode().equals(verificationCode);
        verificationCodeMap.remove(email);
        return ret;
    }

    public static boolean sendVerificationCode(String email) {
        String verificationCode = RandomUtil.generateCode(6);
        VerificationCodeData codeData = new VerificationCodeData(verificationCode, 300); // 设置验证码有效时间为300秒（5分钟）
        verificationCodeMap.put(email, codeData);
        try {
            Email.sendMail(email, verificationCode);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static class VerificationCodeData {
        private String code;
        private long expirationTime; // 过期时间，单位：秒

        public VerificationCodeData(String code, long expirationTime) {
            this.code = code;
            this.expirationTime = System.currentTimeMillis() + (expirationTime * 1000);
        }

        public String getCode() {
            return code;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() > expirationTime;
        }
    }
}
