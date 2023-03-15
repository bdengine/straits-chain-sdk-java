package com.shangchain.straitchain;

import com.shangchain.straitchain.dto.TransactionInfoDto;
import com.shangchain.straitchain.params.ScsTransferDropParam;

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
        StraitChainClient client = new StraitChainClient("appId","appKey");
        String from       = "通行证地址";
        String to         = "通行证地址";
        String privateKey = "privateKey";

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
