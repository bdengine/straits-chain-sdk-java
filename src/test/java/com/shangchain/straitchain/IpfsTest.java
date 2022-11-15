package com.shangchain.straitchain;

import java.io.File;

/**
 * 2022/11/15
 *
 * @author shangchain Junisyoan
 */
public class IpfsTest {
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
        File file = new File("C:\\Users\\shangchain\\Pictures\\Saved Pictures\\1.jpg");
        String s = client.ipfsUpload(from, file);
        System.out.println(s);
    }
}
