package com.shangchain.straitchain;

/**
 * 2022/11/1
 *
 * @author shangchain Junisyoan
 */
public class Contract {
    public static void main(String[] args) {
        StraitChainClient client = new StraitChainClient();
        client.setAppId("Ist8KOqm");
        client.setAppKey("8d065964ab77cfa1917bdafa6c27e5dd605590ed");
//        client.setUrl("https://backend.straitchain.com/webclient");
        client.setUrl("http://192.168.80.15/strait-chain-client-test");
        String from       = "0xc4244f49522c32e6181b759f35be5efa2f19d7f9";
        String to         = "0x171Fa07F54E730364ad843153e896427110F6ea2";
        String privateKey = "09f51b8fd9e4124e1b80e4ffd475a5a542a438177fed9d4f10d626958e16b1da";
        String contractAddress = "0x2797b16bbd1d7e7d0cd3c226dd4b7a0c0c11b45e";


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
