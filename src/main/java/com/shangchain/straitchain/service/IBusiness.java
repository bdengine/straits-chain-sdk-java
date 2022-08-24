package com.shangchain.straitchain.service;

import com.shangchain.straitchain.exception.StraitChainException;
import com.shangchain.straitchain.params.ScsTransferDropParam;
import com.shangchain.straitchain.params.StraitChainSendRawTxParam;

import java.math.BigInteger;

/**
 * 2022/8/22
 * 业务接口
 * 1、转移nft
 * 2、转移drop
 *
 * @author shangchain Junisyoan
 */

public interface IBusiness {
    String transferNftUser(StraitChainSendRawTxParam params) throws StraitChainException,NullPointerException;

    /**
     * 转移nft所属
     * @param params 参数
     * @return 交易哈希
     */
    String transferNftUser(StraitChainSendRawTxParam params,BigInteger gasLimit) throws StraitChainException,NullPointerException;

    /**
     * 转移nft所属
     * @param params 参数
     * @return 交易哈希
     */
    String transferNftUser(StraitChainSendRawTxParam params,BigInteger gasLimit,BigInteger nonce) throws StraitChainException,NullPointerException;

    String transferDrop(ScsTransferDropParam param, BigInteger nonce) throws StraitChainException,NullPointerException;

    String transferDrop(ScsTransferDropParam param,BigInteger gasLimit,BigInteger nonce) throws StraitChainException,NullPointerException;

}
