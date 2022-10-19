package com.shangchain.straitchain.service;

import com.shangchain.straitchain.exception.StraitChainException;
import com.shangchain.straitchain.params.StraitNft4907SetUserParam;

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

    /**
     * 4907合约，nft所属这增加 租赁信息
     * version 2.2.0
     *
     * @param param 参数
     * @return 交易哈希
     */
    String nft4907SetUser(StraitNft4907SetUserParam param) throws StraitChainException,NullPointerException;

    /**
     * 根据tokenId查询租赁对象
     * @param from 用户地址，填合约拥有者地址，参数没用，但不能不填
     * @param contractAddress 合约地址
     * @param tokenId nft编号
     * @return 对象地址
     */
    String nft4907UserOf(String from,String contractAddress,Integer tokenId) throws StraitChainException,NullPointerException;
    String nft4907UserOfExcludeZero(String from,String contractAddress,Integer tokenId) throws StraitChainException,NullPointerException;

    /**
     * 查询租赁时间
     * @param from 用户地址，填合约拥有者地址，参数没用，但不能不填
     * @param contractAddress 合约地址
     * @param tokenId nft编号
     * @return 租赁时间
     */
    Long nft4907UserExpires(String from,String contractAddress,Integer tokenId) throws StraitChainException,NullPointerException;
}
