package com.shangchain.straitchain;

import java.io.File;

/**
 * 2022/11/15
 * 星际文件存储
 * @author shangchain Junisyoan
 */
public class IpfsTest {
    public static void main(String[] args) {
        StraitChainClient client = new StraitChainClient("appId","appKey");
        String from       = "通行证地址";
        String to         = "通行证地址";
        String privateKey = "privateKey";
        String contractAddress = "contractAddress";
        File file = new File("C:\\Users\\shangchain\\Pictures\\Saved Pictures\\1.jpg");
        String s = client.ipfsUpload(from, file);
        System.out.println(s);
    }
}
