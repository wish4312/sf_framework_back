package com.lsitc.domain;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class jasyptCodeGen {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private static void jasypt( ) {
        String url = "jdbc:log4jdbc:sqlserver://ap.kfems.net:51433;databaseName=sempio;sendStringParametersAsUnicode=false";
        String username = "fems_test";
        String password = "fems2021";
                
        System.out.println("url : ENC(" + jasyptEncoding(url) + ")");
        System.out.println("username : ENC(" + jasyptEncoding(username) + ")");
        System.out.println("password : ENC(" + jasyptEncoding(password) + ")");
        System.out.println("secretKey : ENC(" + jasyptEncoding("secretKeysecretKeysecretKeysecretKeysecretKeysecretKey") + ")");
        
    }

    private static String jasyptEncoding(String value) {

        String key = "857b2405e94125f026da8aecb65ad1616bcf336ae827b549146a83cfc17bcbeb79253b61f016fac169e5cf033d470c319afa591224c1cbb84450aa08bf7cf372";
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword(key);
        return pbeEnc.encrypt(value);
    }
    
    public static void main(String[] args) { 
    	jasypt();
    }

}
