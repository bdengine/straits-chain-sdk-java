package com.shangchain.straitchain.service;

import com.shangchain.straitchain.dto.*;
import com.shangchain.straitchain.enums.ContractTypeEnum;
import com.shangchain.straitchain.exception.StraitChainException;
import com.shangchain.straitchain.params.*;
import org.web3j.protocol.core.DefaultBlockParameterName;

import java.math.BigInteger;
import java.util.List;

/**
 * 2022/4/26
 * 链接口
 *
 * @author shangchain Junisyoan
 */
public interface IScsChain {
    /**
     * 链版本号
     * @return 链版本号
     */
    BigInteger scsProtocolVersion() throws StraitChainException,NullPointerException;

    /**
     * 系统建议费用
     * @return 系统建议费用
     */
    BigInteger scsGasPrice() throws StraitChainException,NullPointerException;

    /**
     * 账户余额，需要除以 18个0 Math.pow(10,18)
     * @param address 通行证地址
     * @return 账户余额
     */
    BigInteger scsGetBalance(String address) throws StraitChainException,NullPointerException;

    /**
     * 获取nonce
     * @param from 通行证地址
     * @return 获取nonce
     */
    BigInteger scsGetTransactionCount(String from) throws StraitChainException,NullPointerException;

    /**
     * v 1.2.6
     * 获取nonce
     * @param from 通行证地址
     * @param blockParameterName 块参数，已成功或者交易池
     * @return 获取nonce
     * @throws StraitChainException 海峡链异常
     * @throws NullPointerException 结果为空
     */
    BigInteger scsGetTransactionCount(String from,DefaultBlockParameterName blockParameterName) throws StraitChainException,NullPointerException;

    /**
     * 块内的交易数量
     * @param blockHx 块哈希
     * @return 块内的交易数量
     */
    BigInteger scsGetBlockTransactionCountByHash(String blockHx) throws StraitChainException,NullPointerException;

    /**
     * 块内的交易数量
     * @param blockNumber 块号
     * @return 块内的交易数量
     */
    BigInteger scsGetBlockTransactionCountByNumber(Long blockNumber) throws StraitChainException,NullPointerException;

    /**
     * 该地址的代码
     * @param address 地址
     * @return 该地址的代码
     */
    String scsGetCode(String address) throws StraitChainException,NullPointerException;

    /**
     * 发送交易
     * @param scsParam 参数
     * @return 交易哈希
     */
    String scsSendTransaction(ScsSendTransactionParam scsParam) throws StraitChainException,NullPointerException;
    String scsSendTransaction(ScsSendTransactionParam scsParam,BigInteger nonce) throws StraitChainException,NullPointerException;

    /**
     * 发送交易
     * @param txValue 哈希
     */
    String scsSendRawTransaction(String txValue) throws StraitChainException,NullPointerException;
    String scsSendRawTransaction(StraitChainSendRawTransactionParams params,String encode) throws StraitChainException,NullPointerException;
    String scsSendRawTransaction(String contractAddress,String fromAddress,String privateKey,String encode) throws StraitChainException,NullPointerException;

    /**
     * 估算消耗的gas费用
     * @param scsParam 参数
     * @return 估算消耗的gas费用
     */
    BigInteger scsEstimateGas(ScsEstimateGasParam scsParam) throws StraitChainException,NullPointerException;
    BigInteger scsEstimateGas(ScsEstimateGasParam scsParam,BigInteger nonce) throws StraitChainException,NullPointerException;

    /**
     * 根据块哈希返回块的交易哈希
     * @param address 块哈希
     * @return 交易哈希
     */
    String scsGetBlockByHashWithReturnHash(String address) throws StraitChainException,NullPointerException;

    /**
     * 根据块哈希返回块对象信息
     * @param address 块哈希
     * @return 块信息
     */
    BlockInfoDto scsGetBlockByHashWithReturnObject(String address) throws StraitChainException,NullPointerException;

    /**
     * 根据块号返回块的交易哈希
     * @param blockNumber 块号
     * @return 交易哈希
     */
    String scsGetBlockByNumberWithReturnHash(String blockNumber) throws StraitChainException,NullPointerException;
    /**
     * 根据块号返回块对象信息
     * @param blockNumber 块号
     * @return 块信息
     */
    BlockInfoDto scsGetBlockByNumberWithReturnObject(String blockNumber) throws StraitChainException,NullPointerException;

    /**
     * 获取交易详情
     * @param txHash 交易hash
     * @return 交易信息
     */
    TransactionInfoDto scsGetTransactionReceipt(String txHash) throws StraitChainException,NullPointerException;

    /**
     * 获取交易详情
     * @param txHash 交易hash
     * @return 交易信息
     */
    TransactionInfoDto scsGetTransactionByHash(String txHash) throws StraitChainException,NullPointerException;

    /**
     * 最新的块编号
     * @return 最新的块编号
     */
    String scsBlockNumber() throws StraitChainException,NullPointerException;

    /**
     * 铸造nft
     * @param nftMintParam 参数
     * @return 交易哈希
     */
    String scsNftMint(StraitNftMintParam nftMintParam) throws StraitChainException,NullPointerException;

    /**
     * version 2.2.0
     * 部署默认合约
     * @param count 准备铸造的nft个数
     * @param from 通行证地址
     * @return 交易哈希
     */
    String scsDeployContract(String from,Integer count) throws StraitChainException,NullPointerException;

    /**
     * version 2.2.0
     * 部署合约
     * @param count 准备铸造的nft个数
     * @param from 通行证地址
     * @param contractTypeEnum 合约类型
     * @return 交易哈希
     */
    String scsDeployContract(String from, Integer count, ContractTypeEnum contractTypeEnum) throws StraitChainException,NullPointerException;

    /**
     * 根据scsDeployContract返回的交易哈希，获取合约地址
     * @param txHash 合约部署
     * @return 合约地址
     */
    String scsContractAddressByHash(String txHash) throws StraitChainException,NullPointerException;

    /**
     * 调用链上交易
     * @param scsParam 参数
     * @param blockNumber 块号
     * @return 交易哈希
     * @see DefaultBlockParameterName
     */
    String scsCall(ScsCallParam scsParam,String blockNumber) throws StraitChainException,NullPointerException;
    String scsCall(ScsCallParam scsParam,String blockNumber,BigInteger nonce) throws StraitChainException,NullPointerException;

    /**
     * 根据scsNftMint返回的交易哈希，获取nft数据
     * @param txHash 交易hash
     * @return NftMintDto
     */
    List<NftMintDto> scsGetTokenByHash(String txHash) throws StraitChainException,NullPointerException;

    /**
     * 获取系统通用存证合约地址
     * @return 哈希地址
     */
    String scsGetEvidenceContractAddress() throws StraitChainException,NullPointerException;

    /**
     * 通用存证功能
     * @param existingEvidenceParam 参数
     * @return 交易哈希
     */
    String scsExistingEvidence(StraitChainExistingEvidenceParam existingEvidenceParam) throws StraitChainException,NullPointerException;

    /**
     * version 2.1.0
     * 为单次铸造，每次可铸造1个，合约地址为相同地址。无需多次部署。
     * 铸造nft，该参数顺序要严格按照参数表中的顺序填写。
     * nftURI： 为json文件或txt文件的在线地址（推荐json,铸造后不可变）
     * 内容为：(仅支持https请求的图片地址)
     * {"attributes":[],"image":"https://xxxx.com/xxx.png","name":"xx"}
     * @param param 参数
     * @return 单个nft铸造结果
     * @ throws StraitChainException,NullPointerException 异常
     */
    NftMintDto scsNftMintAlone(StraitNftMintParam param) throws StraitChainException,NullPointerException;

    /**
     * version 2.1.0
     * 数字藏品铸造接口，和nftmint类似，产品不同
     * @param param 参数
     * @return 一串哈希值，传给scs_digital_collection_list做参数查询
     * @ throws StraitChainException,NullPointerException 请求的异常
     */
    String scsDigitalCollectionMint(DigitalCollectionMintParam param) throws StraitChainException,NullPointerException;

    /**
     * version 2.1.0
     * scsDigitalCollectionMint 接口的返回值
     * @param param scsDigitalCollectionMint接口的返回值
     * @return 藏品信息
     */
    List<DigitalMintDto> scsDigitalCollectionList(String param) throws StraitChainException,NullPointerException;

    /**
     * version 2.1.0
     * 转移书子藏品
     * @param param 参数
     * @return 交易哈希
     */
    String scsDigitalCollectionTransaction(DigitalCollectionTransactionParam param) throws StraitChainException,NullPointerException;


    /**
     * 1155合约铸造
     * @param param 参数
     * @return
     * @throws StraitChainException 海峡链异常
     * @throws NullPointerException 结果null
     */
    Nft1155MintDto scs1155NftMint(StraitNftMintParam param) throws StraitChainException,NullPointerException;
}
