package com.shangchain.straitchain;

/**
 * 2022/11/1
 *
 * @author shangchain Junisyoan
 */
public class Contract {
    public static void main(String[] args) {
        StraitChainClient client = new StraitChainClient("appId","appKey");
        String from       = "通行证地址";
        String to         = "通行证地址";
        String privateKey = "privateKey";
        String contractAddress = "contractAddress";


        // 限制转移次数
//        String s = client.nftSetLockCount(contractAddress, from, privateKey, 10L);
//        // 0xdbde4974b5bd30e685a3a1c2f7fc8508b156db101ee7d8002e762b1d6a66822e
//        System.out.println(s);
//        Long aLong = client.nftGetLockCount(contractAddress, from, privateKey);
//        System.out.println(aLong);

        // 是否限制转移
//        String s = client.nftSetLockCountFlag(contractAddress, from, privateKey, true);
//        // 0xfe3624cfc3f012c71acd2b979a3f23a222e2360ae220c102f993ef6cf1335beb
//        System.out.println(s);
//        Boolean s = client.nftGetLockCountFlag(contractAddress, from, privateKey);
//        System.out.println(s);

        // 限制转移时间
//        String s = client.nftSetLockTime(contractAddress, from, privateKey, 60L);
//        // 0x1f2e5b4ed619b7ab8569068b17e2486a94700754e3d317c9d3f2c08652e35fd0
//        System.out.println(s);
//        Long aLong = client.nftGetLockTime(contractAddress, from, privateKey);
//        System.out.println(aLong);

//        String s = client.nftGetOwnerOfContract(contractAddress, from);
//        System.out.println(s);
    }
}
