package com.shangchain.straitchain.params;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

/**
 * 2022/10/19
 * raw交易参数
 *
 * @author shangchain Junisyoan
 */

@Setter
@Getter
public class StraitChainSendRawTransactionParams {

    BigInteger nonce;

    BigInteger gasPrice;

    BigInteger gasLimit;
    /**
     * 合约地址
     */
    String contractAddress;
    /**
     * 发送给谁
     * 地址类型
     */
    String to;
    /**
     * 私钥
     */
    String privateKey;
}
