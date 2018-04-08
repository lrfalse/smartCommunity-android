/**
 * Copyright (c) 2009 - 2016 LANMEI, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * LANMEI, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with LANMEI.
 */
package com.ysjkj.smart.community.provider.utils;

import android.util.Base64;

import com.piaolac.core.utils.Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Description(功能描述): aes加密解密
 * @author(作者): lrfalse<wangliyou>
 * @date (开发日期): 2018/4/3 13:49
 **/
public class AESEncryptUtils {
    private static String KEY = "I8jb59qxzrW2H2BEk1lTGmHarkmv8pqE";                 //加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
    private static String IV_PARAMETER = "2018040313591229";                        //偏移量
    public static final String KEY_ALGORITHM = "AES";                            //密钥算法
    public static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";            //加解密算法/工作模式/填充方式,Java6.0支持PKCS5Padding填充方式,BouncyCastle支持PKCS7Padding填充方式
    private static final String CHSR_SET = "UTF-8";                                //编码格式
    private static AESEncryptUtils instance = null;                                //实例对象

    public static AESEncryptUtils getInstance() {
        if (instance == null)
            instance = new AESEncryptUtils();
        return instance;
    }

    /**
     * @Description(功能描述): aes加密
     * @author(作者): lrfalse<wangliyou>
     * @date (开发日期): 2018/4/3 13:52
     **/
    public static String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        byte[] raw = KEY.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
        IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

        byte[] encrypted = cipher.doFinal(data.getBytes(CHSR_SET));
        return Base64.encodeToString(encrypted, Base64.DEFAULT);
    }

    /**
     * @Description(功能描述): aes解密
     * @author(作者): lrfalse<wangliyou>
     * @date (开发日期): 2018/4/3 13:54
     **/
    public static String decrypt(String data) throws Exception {
        try {
            byte[] raw = KEY.getBytes(CHSR_SET);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = Base64.decode(data, Base64.DEFAULT);        // 先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, CHSR_SET);
            return originalString;
        } catch (Exception ex) {
            return null;
        }
    }


    public static void mains(String[] args) throws Exception {
        String cSrc = "123456";           // 需要加密的字串
        String enString = AESEncryptUtils.getInstance().encrypt(cSrc);  // 加密
        System.out.println(" String：" + cSrc);
        System.out.println(" AES：" + enString);
        System.out.println(" Base64：" + Utils.INSTANCE.md5(cSrc));
        String DeString = AESEncryptUtils.getInstance().decrypt("Rn1sg0UeCxJf9ymSOjU0tFG5CUr3sNxx4uWTGtoc0p8=");
        System.out.println("xxx 解密后的字串是：" + DeString);
    }
}
