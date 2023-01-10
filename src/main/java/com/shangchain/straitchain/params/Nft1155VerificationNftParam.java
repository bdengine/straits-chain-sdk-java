package com.shangchain.straitchain.params;

import lombok.Getter;
import lombok.Setter;

/**
 * 2023/1/9
 * 查询铸造信息参数
 *
 * @author shangchain Junisyoan
 */

@Setter
@Getter
public class Nft1155VerificationNftParam {
    /**
     * 藏品唯一标识
     */
    Long tokenId;
    /**
     * 合约所有者
     */
    String from;
    /**
     * 合约地址
     */
    String contractAddress;
}
