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

    /**
     * version 1.0.0
     * @param toAddress 新用户地址
     * @return String
     */
    String nftTransferOfOwnerShip(String contractAddress,String fromAddress,String toAddress,String privateKey) throws StraitChainException,NullPointerException;

    /**
     * 是否开启合约锁定转移时间限制
     * @param contractAddress 合约地址
     * @param lock 是否打开
     * @return 交易哈希
     */
    Boolean nftSetLockTimeFlag(String contractAddress,String fromAddress,String privateKey,Boolean lock) throws StraitChainException,NullPointerException;

    /**
     * 获取合约开启锁定转移时间
     * @param contractAddress 合约地址
     * @return 交易哈希
     */
    String nftGetLockTimeFlag(String contractAddress,String fromAddress,String privateKey) throws StraitChainException,NullPointerException;

    /**
     * 是否开启合约锁定转移次数
     * @param contractAddress 合约地址
     * @param lock 是否锁定
     * @return 交易哈希
     */
    String nftSetLockCountFlag(String contractAddress,String fromAddress,String privateKey,Boolean lock) throws StraitChainException,NullPointerException;

    /**
     * 获取合约锁定转移次数
     * @param contractAddress 合约地址
     * @return 交易哈希
     */
    String nftGetLockCountFlag(String contractAddress,String fromAddress,String privateKey) throws StraitChainException,NullPointerException;

    /**
     * 设置合约锁定转移时间
     * @param contractAddress 合约地址
     * @param seconds 秒
     * @return 交易哈希
     */
    String nftSetLockTime(String contractAddress,String fromAddress,String privateKey,Long seconds) throws StraitChainException,NullPointerException;

    /**
     * 获取合约锁定转移时间
     * @param contractAddress 合约地址
     * @return 时间，秒
     */
    Long nftGetLockTime(String contractAddress,String fromAddress,String privateKey) throws StraitChainException,NullPointerException;

    /**
     * 设置合约限制转移次数
     * @param contractAddress 合约地址
     * @param lockCount 转移次数
     * @return 交易哈希
     */
    String nftSetLockCount(String contractAddress,String fromAddress,String privateKey,Long lockCount) throws StraitChainException,NullPointerException;

    /**
     * 获取合约限制转移次数
     * @param contractAddress 合约地址
     * @return 转移次数
     */
    Long nftGetLockCount(String contractAddress,String fromAddress,String privateKey) throws StraitChainException,NullPointerException;

    /**
     * 获取nft的tokenUri地址
     * @param contractAddress 合约地址
     * @return url
     */
    String nftTokenUri(String contractAddress,String fromAddress,Long tokenId) throws StraitChainException,NullPointerException;

    /**
     * 获取nft总数
     * @param contractAddress 合约地址
     * @return 总数
     */
    Long nftTotalSupply(String contractAddress,String fromAddress) throws StraitChainException,NullPointerException;

    /**
     * 获取合约所有者地址
     * @param contractAddress 合约地址
     * @return 地址
     */
    String nftGetOwnerOfContract(String contractAddress,String fromAddress) throws StraitChainException,NullPointerException;
}
