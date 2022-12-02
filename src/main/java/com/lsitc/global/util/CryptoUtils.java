package com.lsitc.global.util;

import com.lsitc.global.error.exception.BisiExcp;
import java.math.BigInteger;
import java.security.MessageDigest;

public class CryptoUtils {

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
