package com.xws.xysz.util;

import org.acegisecurity.providers.encoding.ShaPasswordEncoder;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

/**
 * 密码加密类
 * <p/>
 * Created by 光 on 2015/3/23.
 */
public class PasswordEncoder {
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private static ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder();

    static{shaPasswordEncoder.setEncodeHashAsBase64(true);}

    /**
     * 加密
     *
     * @param pass 密码
     * @param salt 扰码
     * @return 加密后的密码
     */
    public static String encode(String pass, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        // PBKDF2 with SHA-1 as the hashing algorithm. Note that the NIST
        // specifically names SHA-1 as an acceptable hashing algorithm for PBKDF2
        String algorithm = "PBKDF2WithHmacSHA1";
        // SHA-1 generates 160 bit hashes, so that's what makes sense here
        int derivedKeyLength = 160;
        // Pick an iteration count that works for you. The NIST recommends at
        int iterations = 20000;
        byte[] salts = DatatypeConverter.parseBase64Binary(salt);
        KeySpec spec = new PBEKeySpec(pass.toCharArray(), salts, iterations, derivedKeyLength);

        SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);

        return DatatypeConverter.printBase64Binary(f.generateSecret(spec).getEncoded());
    }

    /**
     * 验证密码是否正确
     *
     * @param encPass 加密后密码
     * @param rawPass 输入的密码
     * @param salt    扰码
     * @return 是否正确
     */
    public static boolean isPasswordValid(String encPass, String rawPass, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Encrypt the clear-text password using the same salt that was used to
        // encrypt the original password
        byte[] encryptedAttemptedPassword = DatatypeConverter.parseBase64Binary(encode(rawPass, salt));

        // Authentication succeeds if encrypted password that the user entered
        // is equal to the stored hash
        return Arrays.equals(DatatypeConverter.parseBase64Binary(encPass), encryptedAttemptedPassword);


    }

    /**
     * 获得一个扰码
     *
     * @return 扰码
     */
    public static String getSalt()throws NoSuchAlgorithmException {
        // VERY important to use SecureRandom instead of just Random
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

        // Generate a 8 byte (64 bit) salt as recommended by RSA PKCS5
        byte[] salt = new byte[8];
        random.nextBytes(salt);

        return DatatypeConverter.printBase64Binary(salt);
    }

}
