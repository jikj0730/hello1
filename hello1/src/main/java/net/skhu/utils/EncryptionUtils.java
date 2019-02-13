package net.skhu.utils;


import java.security.MessageDigest;

/**
 * @author Promise
 * 제목 : EncryptionUtils
 * 설명 : 비밀번호 암호화 알고리즘을 위한 클래스이다
 * 설계자 : 전경준
 */
public class EncryptionUtils {

	/*
	 * SHA-256 알고리즘을 적용한다
	 */
    public static String encryptSHA256(String s) {
        return encrypt(s, "SHA-256");
    }

    public static String encryptMD5(String s) {
        return encrypt(s, "MD5");
    }

    public static String encrypt(String s, String messageDigest) {
        try {
            MessageDigest md = MessageDigest.getInstance(messageDigest);
            byte[] passBytes = s.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<digested.length;i++)
                sb.append(Integer.toHexString(0xff & digested[i]));
            return sb.toString();
        }
        catch (Exception e) {
            return s;
        }
    }
}

