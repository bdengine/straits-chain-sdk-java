package com.shangchain.straitchain.params;

import lombok.Getter;
import lombok.Setter;

/**
 * 2023/1/9
 * 转移参数
 *
 * @author shangchain Junisyoan
 */

@Getter
@Setter
public class Nft1155SafeTransferFromParam {
    /**
     * nft所属者
     */
    String from;
    /**
     * 转移对象地址
     */
    String to;
    /**
     * 唯一标识
     */
    Long tokenId;
    /**
     * 转移个数
     */
    Long amount;
    /**
     * 附加信息
     */
    String data;
    /**
     * 合约地址
     */
    String contractAddress;

    /**
     * 私钥
     */
    String privateKey;
}
