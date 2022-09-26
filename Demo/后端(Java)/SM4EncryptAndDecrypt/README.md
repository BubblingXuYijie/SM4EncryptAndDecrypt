# 使用方法

> 直接调用 SM4Util 的方法

```java
/**
 * ECB 加密模式
 */
//不使用自定义 secretKey，一般用于后端自行加解密
// 如果是前端加密后端解密，则需要自定义secretKey，前后端一致才能正确解密
System.out.println("经过ECB加密的密文为：" + SM4Utils.encryptData_ECB("123456"));
System.out.println("经过ECB解密的密文为：" + SM4Utils.decryptData_ECB("UQZqWWcVSu7MIrMzWRD/wA=="));
//使用自定义 secretKey，传入的 secretKey 必须为16位，可包含字母、数字、标点
System.out.println("经过ECB加密的密文为：" + SM4Utils.encryptData_ECB("123456"));
System.out.println("经过ECB解密的密文为：" + SM4Utils.decryptData_ECB("UQZqWWcVSu7MIrMzWRD/wA=="));
```
```java
/**
 * CBC 加密模式（更加安全）
 * 需要两个密钥
 */
System.out.println("经过CBC加密的密文为：" + SM4Utils.encryptData_CBC("123456"));
System.out.println("经过CBC解密的密文为：" + SM4Utils.decryptData_CBC("hbMK6/IeJ3UTzaTgLb3f3A=="));
//同样可以自定义 secretKey 和 iv，需要两个密钥前后端都一致
System.out.println("经过CBC加密的密文为：" + SM4Utils.encryptData_CBC("123456", "asdfghjklzxcvb!_", "1234567890123456"));
System.out.println("经过CBC解密的密文为：" + SM4Utils.decryptData_CBC("sTyCl3G6TF311kIENzsKNg==", "asdfghjklzxcvb!_", "1234567890123456"));
```
