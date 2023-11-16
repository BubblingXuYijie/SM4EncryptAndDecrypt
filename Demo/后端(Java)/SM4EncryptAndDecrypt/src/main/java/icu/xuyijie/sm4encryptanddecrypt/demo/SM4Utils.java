package icu.xuyijie.sm4encryptanddecrypt.demo;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: 徐一杰
 * @date: 2022/10/11
 * <p>
 * ECB 加密模式
 * 不使用自定义 secretKey，一般用于后端自行加解密,如果是前端加密后端解密，则需要自定义secretKey，前后端一致才能正确解密
 * 经过ECB加密的密文为：SM4Utils.encryptData_ECB("123456");
 * 经过ECB解密的密文为：SM4Utils.decryptData_ECB("UQZqWWcVSu7MIrMzWRD/wA==");
 * 使用自定义 secretKey，传入的 secretKey 必须为16位，可包含字母、数字、标点
 * 经过ECB加密的密文为：SM4Utils.encryptData_ECB("123456");
 * 经过ECB解密的密文为：SM4Utils.decryptData_ECB("UQZqWWcVSu7MIrMzWRD/wA==");
 * <p>
 * CBC 加密模式（更加安全），需要两个密钥
 * 经过CBC加密的密文为：SM4Utils.encryptData_CBC("123456");
 * 经过CBC解密的密文为：SM4Utils.decryptData_CBC("hbMK6/IeJ3UTzaTgLb3f3A==");
 * 同样可以自定义 secretKey 和 iv，需要两个密钥前后端都一致
 * 经过CBC加密的密文为：SM4Utils.encryptData_CBC("123456","asdfghjklzxcvb!_","1234567890123456");
 * 经过CBC解密的密文为：SM4Utils.decryptData_CBC("sTyCl3G6TF311kIENzsKNg==","asdfghjklzxcvb!_","1234567890123456");
 */
public class SM4Utils {

    /**
     * 默认 SECRET_KEY
     * 当时用ECB模式的时候，和前端key一致
     * secretKey 必须为16位，可包含字母、数字、标点
     */
    private static final String SECRET_KEY = "GJwsXX_BzW=gJWJW";

    /**
     * 默认 IV
     * 当时用CBC模式的时候，SECRET_KEY和IV都需要传值，解密要和加密的SECRET_KEY和IV一致，更加安全
     * iv 必须为 16 位，可包含字母、数字、标点
     */
    private static final String IV = "ZkR_SiNoSOFT=568";

    private static final boolean HEX_STRING = false;

    public SM4Utils() {

    }

    /**
     * 不要在方法里定义正则表达式规则,应定义为常量或字段,能加快正则匹配速度
     */
    private static final Pattern P = Pattern.compile("\\s*|\t|\r|\n");

    /**
     * ECB模式加密，自定义密钥，加解密密钥需一致
     *
     * @param plainText 要加密的数据
     * @param secretKey 密钥，必须为 16 位，可包含字母、数字、标点
     * @return
     */
    public static String encryptData_ECB(String plainText, String secretKey) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_ENCRYPT;

            byte[] keyBytes;
            keyBytes = secretKey.getBytes();
            SM4 sm4 = new SM4();
            sm4.sm4_setkey_enc(ctx, keyBytes);
            byte[] encrypted = sm4.sm4_crypt_ecb(ctx, plainText.getBytes(StandardCharsets.UTF_8));
            String cipherText = Base64.encodeBase64String(encrypted);
            if (cipherText != null && cipherText.trim().length() > 0) {
                Matcher m = P.matcher(cipherText);
                cipherText = m.replaceAll("");
            }
            return cipherText;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ECB模式加密，默认密钥
     *
     * @param plainText 要加密的数据
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
                Matcher m = P.matcher(cipherText);
                cipherText = m.replaceAll("");
            }
            return cipherText;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ECB模式解密，自定义密钥，加解密密钥需一致
     *
     * @param cipherText 要解密的数据
     * @param secretKey 密钥，必须为 16 位，可包含字母、数字、标点
     * @return
     */
    public static String decryptData_ECB(String cipherText, String secretKey) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_DECRYPT;

            byte[] keyBytes;
            keyBytes = secretKey.getBytes();
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
     * ECB模式解密，默认密钥
     *
     * @param cipherText 要解密的数据
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
     * CBC模式加密，SECRET_KEY和IV都需要传值，解密要和加密的SECRET_KEY和IV一致，更加安全
     *
     * @param plainText 要加密的数据
     * @param secretKey 密钥一，必须为 16 位，可包含字母、数字、标点
     * @param iv 密钥二，必须为 16 位，可包含字母、数字、标点
     * @return
     */
    public static String encryptData_CBC(String plainText, String secretKey, String iv) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_ENCRYPT;

            byte[] keyBytes;
            byte[] ivBytes;

            keyBytes = secretKey.getBytes();
            ivBytes = iv.getBytes();

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_enc(ctx, keyBytes);
            byte[] encrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, plainText.getBytes(StandardCharsets.UTF_8));
            String cipherText = Base64.encodeBase64String(encrypted);
            if (cipherText != null && cipherText.trim().length() > 0) {
                Matcher m = P.matcher(cipherText);
                cipherText = m.replaceAll("");
            }
            return cipherText;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * CBC模式加密，SECRET_KEY和IV都需要传值，解密要和加密的SECRET_KEY和IV一致，更加安全
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
                Matcher m = P.matcher(cipherText);
                cipherText = m.replaceAll("");
            }
            return cipherText;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * CBC模式解密，SECRET_KEY和IV都需要传值，解密要和加密的SECRET_KEY和IV一致，更加安全
     *
     * @param cipherText 要解密的数据
     * @param secretKey 密钥一，必须为 16 位，可包含字母、数字、标点
     * @param iv 密钥二，必须为 16 位，可包含字母、数字、标点
     * @return
     */
    public static String decryptData_CBC(String cipherText, String secretKey, String iv) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_DECRYPT;

            byte[] keyBytes;
            byte[] ivBytes;
            if (HEX_STRING) {
                keyBytes = Util.hexStringToBytes(secretKey);
                ivBytes = Util.hexStringToBytes(iv);
            } else {
                keyBytes = secretKey.getBytes();
                ivBytes = iv.getBytes();
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

    /**
     * CBC模式解密，SECRET_KEY和IV都需要传值，解密要和加密的SECRET_KEY和IV一致，更加安全
     *
     * @param cipherText 要解密的数据
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
        System.out.println("经过ECB加密的密文为：" + SM4Utils.encryptData_ECB("123456"));
        System.out.println("经过ECB解密的密文为：" + SM4Utils.decryptData_ECB("UQZqWWcVSu7MIrMzWRD/wA=="));
        System.out.println("经过CBC加密的密文为：" + SM4Utils.encryptData_CBC("123456", "asdfghjklzxcvbnm", "1234567890123456"));
        System.out.println("经过CBC解密的密文为：" + SM4Utils.decryptData_CBC("RZUhE8Zeobfkn/sqnPXA+g==", "asdfghjklzxcvbnm", "1234567890123456"));
    }
}
