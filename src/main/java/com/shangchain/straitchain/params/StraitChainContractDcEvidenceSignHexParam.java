package com.shangchain.straitchain.params;

import lombok.Data;

/**
 * 2022/4/26
 * 存证合约签名参数
 *
 * @author shangchain Junisyoan
 */
@Data
public class StraitChainContractDcEvidenceSignHexParam {
    String from;
    String privateKey;
    String cid;
    String content;
}
