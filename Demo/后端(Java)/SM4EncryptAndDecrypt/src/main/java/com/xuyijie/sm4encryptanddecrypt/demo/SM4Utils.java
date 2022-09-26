package com.xuyijie.sm4encryptanddecrypt.demo;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: 徐一杰
 * @date: 2021/12/24
 * @Description: 国密SM4对称加密算法，原作者为中科软wzk，但是版本太旧，本人改进了引入的依赖。此方法需要配合 SM4_Context,SM4,Utils 共同使用
 * <p>
 * sm4加密解密
 * CBC、ECB模式
 * 原作者：中科院
 * 修改：徐一杰
 * 2021/12/24
 * <p>
 * 运    行   方   法
 * <p>
 * 1、CBC模式(此模式更安全)
 * 前端：有对应js文件
 * var sm4=new SM4Util();
 * sm4.encryptData_CBC('');
 * <p>
 * 后端：
 * SM4Utils.decryptData_CBC("");
 * <p>
 * 2、ECB模式
 * 前端：有对应js文件
 * var sm4=new SM4Util();
 * sm4.encryptData_ECB('');
 * <p>
 * 后端：
 * SM4Utils.decryptData_ECB("");
 */

@SuppressWarnings("restriction")
public class SM4Utils {

    /**
     * 当时用ECB模式的时候，和前端key一致
     */
    private static final String SECRET_KEY = "GJwsXX_BzW=gJWJW";

    /**
     * 当时用CBC模式的时候，和前端iv一致
     */
    private static final String IV = "ZkR_SiNoSOFT=568";

    private static final boolean HEX_STRING = false;

    public SM4Utils() {

    }

    /**
     * ECB模式加密
     *
     * @param plainText
     * @return
     */
    public static String encryptData_ECB(String plainText) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_ENCRYPT;

            byte[] keyBytes;
            keyBytes = SECRET_KEY.getBytes();
            SM4 sm4 = new SM4();
            sm4.sm4_setkey_enc(ctx, keyBytes);
            byte[] encrypted = sm4.sm4_crypt_ecb(ctx, plainText.getBytes(StandardCharsets.UTF_8));
            String cipherText = Base64.encodeBase64String(encrypted);
            if (cipherText != null && cipherText.trim().length() > 0) {
                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                Matcher m = p.matcher(cipherText);
                cipherText = m.replaceAll("");
            }
            return cipherText;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ECB模式解密
     *
     * @param cipherText
     * @return
     */
    public static String decryptData_ECB(String cipherText) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_DECRYPT;

            byte[] keyBytes;
            keyBytes = SECRET_KEY.getBytes();
            SM4 sm4 = new SM4();
            sm4.sm4_setkey_dec(ctx, keyBytes);
            byte[] decrypted = sm4.sm4_crypt_ecb(ctx, Base64.decodeBase64(cipherText));
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * CBC模式加密
     *
     * @param plainText
     * @return
     */
    public static String encryptData_CBC(String plainText) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_ENCRYPT;

            byte[] keyBytes;
            byte[] ivBytes;

            keyBytes = SECRET_KEY.getBytes();
            ivBytes = IV.getBytes();

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_enc(ctx, keyBytes);
            byte[] encrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, plainText.getBytes(StandardCharsets.UTF_8));
            String cipherText = Base64.encodeBase64String(encrypted);
            if (cipherText != null && cipherText.trim().length() > 0) {
                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                Matcher m = p.matcher(cipherText);
                cipherText = m.replaceAll("");
            }
            return cipherText;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * CBC模式解密
     *
     * @param cipherText
     * @return
     */
    public static String decryptData_CBC(String cipherText) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_DECRYPT;

            byte[] keyBytes;
            byte[] ivBytes;
            if (HEX_STRING) {
                keyBytes = Util.hexStringToBytes(SECRET_KEY);
                ivBytes = Util.hexStringToBytes(IV);
            } else {
                keyBytes = SECRET_KEY.getBytes();
                ivBytes = IV.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_dec(ctx, keyBytes);
            byte[] decrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, Base64.decodeBase64(cipherText));
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println("经过加密的密文为：" + SM4Utils.encryptData_CBC("123456"));
        System.out.println("经过解密的密文为：" + SM4Utils.decryptData_CBC("hbMK6/IeJ3UTzaTgLb3f3A=="));
    }
}
