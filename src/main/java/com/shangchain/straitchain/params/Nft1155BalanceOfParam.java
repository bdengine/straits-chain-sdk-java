package com.shangchain.straitchain.params;

import lombok.Getter;
import lombok.Setter;

/**
 * 2023/1/9
 * 查询账户拥有NFT数量
 *
 * @author shangchain Junisyoan
 */

@Setter
@Getter
public class Nft1155BalanceOfParam {

    String from;
    /**
     * 账户地址
     */
    String account;
    /**
     * 唯一标识
     */
    Long tokenId;
    /**
     * 合约地址
     */
    String contractAddress;
}
