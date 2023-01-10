package com.shangchain.straitchain.params;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 2023/1/9
 *
 * @author shangchain Junisyoan
 */
@Setter
@Getter
public class Nft1155BalanceOfBatchParam {
    /**
     * 合约所有者
     */
    String from;
    /**
     * 账户地址
     */
    List<String> accounts;
    /**
     * nft唯一标识
     */
    List<Long> tokenIds;

    /**
     * 合约地址
     */
    String contractAddress;
}
