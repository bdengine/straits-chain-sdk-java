package com.shangchain.straitchain.params;

import lombok.Data;

/**
 * 2022/4/26
 * 交易参数
 *
 * @author shangchain Junisyoan
 */
@Data
public class StraitChainSendRawTxParam {
    String from;
    String to;
    String contractAddress;
    Integer tokenId;
    String privateKey;
}
