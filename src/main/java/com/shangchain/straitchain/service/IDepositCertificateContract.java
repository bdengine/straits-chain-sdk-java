package com.shangchain.straitchain.service;

import com.shangchain.straitchain.exception.StraitChainException;
import com.shangchain.straitchain.params.StraitChainContractDcEvidenceSignHexParam;

import java.math.BigInteger;

/**
 * 2022/4/26
 * 存证合约相关接口
 *
 * @author shangchain Junisyoan
 */
public interface IDepositCertificateContract {
    void dcEvidence();

    /**
     *
     * @return 存证方法签名
     * @param param
     */
    String dcEvidenceSignHex(StraitChainContractDcEvidenceSignHexParam param) throws StraitChainException;
    String dcEvidenceSignHex(StraitChainContractDcEvidenceSignHexParam param, BigInteger nonce) throws StraitChainException;
}
