package com.xuyijie.sm4encryptanddecrypt.demo;

/**
 * @Author: 徐一杰
 * @date: 2021/12/24
 * @Description: 国密SM4对称加密算法，原作者为中科软wzk，但是版本太旧，本人改进了引入的依赖。此方法需要配合 SM4,SM4Utils,Utils 共同使用
 */
public class SM4_Context {
    public int mode;

    public int[] sk;

    public boolean isPadding;

    public SM4_Context() {
        this.mode = 1;
        this.isPadding = true;
        this.sk = new int[32];
    }
}
