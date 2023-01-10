package com.shangchain.straitchain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 2023/1/9
 * 铸造返回值
 *
 * @author shangchain Junisyoan
 */

@Setter
@Getter
@ToString
public class Nft1155MintDto {
    /**
     * 唯一标识
     */
    Long tokenId;

    /**
     *总数
     */
    Long count;

    /**
     * 铸造返回哈希
     */
    String hash;

    /**
     * 铸造信息
     */
    List<String> mintData;
}
