package com.shangchain.straitchain;

import com.shangchain.straitchain.dto.TransactionInfoDto;
import com.shangchain.straitchain.params.ScsTransferDropParam;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 2022/4/27
 * 开发用户向普通用户转账drop
 *
 * @author shangchain Junisyoan
 */
public class DevUserTransferUserDrop {
    // 替换信息可直接运行
    public static void main(String[] args) {
        StraitChainClient client = new StraitChainClient();
//        client.setAppId("appId");
//        client.setAppKey("appKey");
//        client.setUrl("https://backend.straitchain.com");
//        String from       = "通行证地址";
//        String to         = "通行证地址";
//        String privateKey = "通行证私钥";
        client.setAppId("Ist8KOqm");
        client.setAppKey("8d065964ab77cfa1917bdafa6c27e5dd605590ed");
        client.setUrl("http://192.168.80.15/strait-chain-client-test");
        String from       = "0xc4244f49522c32e6181b759f35be5efa2f19d7f9";
        String to         = "0x171Fa07F54E730364ad843153e896427110F6ea2";
        String privateKey = "09f51b8fd9e4124e1b80e4ffd475a5a542a438177fed9d4f10d626958e16b1da";

        //获取nonce
        BigInteger nonce = client.scsGetTransactionCount(from);
        // 转移
        ScsTransferDropParam param = new ScsTransferDropParam();
        param.setFromAddress(from);
        param.setToAddress(to);
        param.setPrivateKey(privateKey);
        param.setDrop(10120.4667);
        String txHash = client.transferDrop(param, nonce);
        System.out.println(txHash);

        // 这里和上面一步要分开做
        TransactionInfoDto receipt = client.scsGetTransactionReceipt(txHash);
        if (receipt.isStatusOK()) {
            System.out.println("成功");
        }else if (receipt.getStatus().equals("0x0")){
            System.out.println("失败");
        }else {
            System.out.println("失败或者上链中");
        }

    }

}
