package com.lsitc.core.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

import com.lsitc.core.exception.BisiExcp;

public class CryptoUtils {

//    public static String getEncrypt(String source, String salt) {
//        return getEncrypt(source, salt.getBytes());
//    }
//     
//    public static String getEncrypt(String source, byte[] salt) {
//        
//        String result = "";
//         
//        byte[] a = source.getBytes();
//        byte[] bytes = new byte[a.length + salt.length];
//         
//        System.arraycopy(a, 0, bytes, 0, a.length);
//        System.arraycopy(salt, 0, bytes, a.length, salt.length);
//         
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-512");
//            md.update(bytes);
//            
//            byte[] byteData = md.digest();
//             
//            StringBuffer sb = new StringBuffer();
//            for (int i = 0; i < byteData.length; i++) {
//                sb.append(Integer.toString((byteData[i] & 0xFF) + 256, 16).substring(1));
//            }
//             
//            result = sb.toString();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//         
//        return result;
//    }
//     
//    public static String generateSalt() {
//        Random random = new Random();
//         
//        byte[] salt = new byte[8];
//        random.nextBytes(salt);
//         
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < salt.length; i++) {
//            // byte 값을 Hex 값으로 바꾸기.
//            sb.append(String.format("%02x",salt[i]));
//        }
//         
//        return sb.toString();
//    }
    
    public static String getSHA256(String input) {
        String toReturn = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.reset();
            digest.update(input.getBytes("utf8"));
            toReturn = String.format("%064x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            //FIXME 다국어처리
            throw new BisiExcp("암호화 오류");
        }

        return toReturn;
    }

    public static String getSHA512(String input) {
        String toReturn = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(input.getBytes("utf8"));
            toReturn = String.format("%0128x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            //FIXME 다국어처리
            throw new BisiExcp("암호화 오류");
        }

        return toReturn;
    }
}
