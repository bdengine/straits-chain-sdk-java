package com.shangchain.straitchain.service;

import com.shangchain.straitchain.exception.StraitChainException;

/**
 * 2022/4/26
 * nft合约接口
 *
 * @author shangchain Junisyoan
 */
public interface INftContract {

    /**
     * 获取nft所有者
     *
     * @param from
     * @param tokenId nftId
     * @return 拥有者地址
     */
    String nftOwnerOf(String from, String contractAddress,Integer tokenId) throws StraitChainException,NullPointerException;
    String nftOwnerOfExcludeZero(String from, String contractAddress,Integer tokenId) throws StraitChainException,NullPointerException;
}
