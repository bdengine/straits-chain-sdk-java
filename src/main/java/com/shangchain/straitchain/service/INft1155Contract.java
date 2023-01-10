package com.shangchain.straitchain.service;

import com.shangchain.straitchain.dto.Nft1155MintDetailDto;
import com.shangchain.straitchain.exception.StraitChainException;
import com.shangchain.straitchain.params.Nft1155BalanceOfBatchParam;
import com.shangchain.straitchain.params.Nft1155BalanceOfParam;
import com.shangchain.straitchain.params.Nft1155SafeTransferFromParam;
import com.shangchain.straitchain.params.Nft1155VerificationNftParam;

import java.math.BigInteger;
import java.util.List;

/**
 * 2023/1/9
 * 1155合约
 *
 * @author shangchain Junisyoan
 */
public interface INft1155Contract {

    /**
     * 转移合约
     * @param param 转移参数
     * @return 交易哈希
     */
    String nft1155SafeTransferFrom(Nft1155SafeTransferFromParam param) throws StraitChainException,NullPointerException;
    /**
     * 转移合约
     * @param param 转移参数
     * @param nonce 当前from对应的交易次数，null自动获取
     * @param gasPrice 预估交易费用，null自动获取
     * @param gasLimit 交易费用上限，达到交易上限就停止执行，返回失败，null默认
     * @return 交易哈希
     */
    String nft1155SafeTransferFrom(Nft1155SafeTransferFromParam param, BigInteger nonce,BigInteger gasPrice,BigInteger gasLimit) throws StraitChainException,NullPointerException;

    /**
     * 查询账户拥有的nft数量
     * @param param
     * @return
     */
    Long nft1155BalanceOf(Nft1155BalanceOfParam param) throws StraitChainException,NullPointerException;

    /**
     * 批量查询账户拥有nft数量
     * @param param
     * @return
     */
    List<Long> nft1155BalanceOfBatch(Nft1155BalanceOfBatchParam param) throws StraitChainException,NullPointerException;

    /**
     * 查询藏品的铸造信息
     * @return
     * @param param
     */
    Nft1155MintDetailDto nft1155VerificationNft(Nft1155VerificationNftParam param) throws StraitChainException,NullPointerException;
}
