package com.shangchain.straitchain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 2023/1/10
 * 铸造信息详情
 *
 * @author shangchain Junisyoan
 */
@Setter
@Getter
public class Nft1155MintDetailDto {
    Long tokenId;
    String mintOwner;
    Long amount;
    List<String> mintData;
}
