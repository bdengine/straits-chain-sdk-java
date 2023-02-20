package com.shangchain.straitchain;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.shangchain.straitchain.constants.StraitChainConstant;
import com.shangchain.straitchain.dto.*;
import com.shangchain.straitchain.enums.ContractTypeEnum;
import com.shangchain.straitchain.exception.StraitChainException;
import com.shangchain.straitchain.params.*;
import com.shangchain.straitchain.service.*;
import com.shangchain.straitchain.utils.StraitChainUtil;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.*;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.*;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.utils.Numeric;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 2022/4/26
 * 客户端
 *
 * @author shangchain Junisyoan
 */
@Data
public class StraitChainClient implements
        IScsChain, INftContract,
        IDepositCertificateContract, Ipfs,
        IBusiness,INft1155Contract
{
    Logger log = LoggerFactory.getLogger(this.getClass());

    private String appId;
    private String appKey;
    private String url;
    private BigInteger defaultGasLimit = new BigInteger("150000");
    private BigInteger defaultGasPrice = new BigInteger(new Double(5.63 * Math.pow(10, 11) + 7).longValue()+"");
    /**
     * 初始化的时候要设置
     */
    private int timeout = 6000;

    public StraitChainClient(String appId, String appKey, String url) {
        this.appId = appId;
        this.appKey = appKey;
        this.url = url;
    }

    public void refresh(String appId, String appKey, String url){
        this.appId = appId;
        this.appKey = appKey;
        this.url = url;
    }

    public void setTimeout(int timeout){
        this.timeout = timeout;
    }

    public StraitChainClient() {
    }

    /**
     * 由于有些项目会造成okhttp冲突，所以改为hutool请求
     * @param requestBody json串
     * @param url 请求地址
     * @return 响应
     */
    protected StraitChainResponse commonRequest(String requestBody, String url){
        log.debug("请求地址：{}，请求体：{}",url,requestBody);
        HttpRequest request = HttpUtil.createPost(url).contentType(ContentType.JSON.getValue()).timeout(timeout);
        HttpResponse response = request.body(requestBody).execute();
        String body = response.body();
        log.debug("请求结果：{}",body);
        // 状态码范围在200~299内
        if (response.isOk()){
            StraitChainResponse result = JSONObject.parseObject(body, StraitChainResponse.class);
            if (result.getError()!=null){
                throw new StraitChainException(result.getError());
            }
            return result;
        }
        throw new StraitChainException("请求失败："+ body);
    }


    protected StraitChainResponse commonFileRequest(Map<String,Object> parametersMap, String url){
        log.info("请求地址：{}，请求体：{}",url,JSONObject.toJSONString(parametersMap));
        HttpResponse response = HttpRequest.post(url).form(parametersMap).timeout(timeout).execute();
        log.info("请求结果：{}",response.body());
        // 状态码范围在200~299内
        if (response.isOk()){
            StraitChainResponse result = JSONObject.parseObject(response.body(), StraitChainResponse.class);
            if (result.getError()!=null){
                throw new StraitChainException(result.getError().getMessage(),result.getError().getCode());
            }
            return result;
        }
        throw new StraitChainException("请求失败，状态码：："+response.getStatus());
    }

    protected StraitChainResponse chainRequest(StraitChainParam straitChainParam){
        if (StrUtil.isEmpty(url)) {
            throw new IllegalArgumentException("url不能为空");
        }
        String chainUri = "/webclient/api/develop/straits/action";
        List<Object> paramList = straitChainParam.getParams();
        for (int i = 0; i < paramList.size(); i++) {
            Object obj = paramList.get(i);
            if (obj == null) {
                throw new StraitChainException(straitChainParam.getMethod()+"，第"+i+"个参数为null，参数不允许为null，为空则填写空串\"\"");
            }
        }
        return commonRequest(JSONObject.toJSONString(straitChainParam, SerializerFeature.DisableCircularReferenceDetect), url + chainUri);
    }

    @Override
    public BigInteger scsProtocolVersion() throws StraitChainException {
        StraitChainParam param = new StraitChainParam();
        param.setMethod(StraitChainConstant.SCS_PROTOCOL_VERSION);
        StraitChainResponse response = chainRequest(param);
        return StraitChainUtil.toBigInteger(response.getResult().toString());
    }

    @Override
    public BigInteger scsGasPrice() throws StraitChainException {
        StraitChainParam param = new StraitChainParam();
        param.setMethod(StraitChainConstant.SCS_GAS_PRICE);
        StraitChainResponse response = chainRequest(param);
        return StraitChainUtil.toBigInteger(response.getResult().toString());
    }

    @Override
    public BigInteger scsGetBalance(String address) throws StraitChainException {
        List<Object> list =new ArrayList<>();
        list.add(address);
        list.add(DefaultBlockParameterName.LATEST.getValue());
        StraitChainParam param = new StraitChainParam();
        param.setMethod(StraitChainConstant.SCS_GET_BALANCE);
        param.setParams(list);
        StraitChainResponse response = chainRequest(param);
        return StraitChainUtil.toBigInteger(response.getResult().toString());
    }

    @Override
    public BigInteger scsGetTransactionCount(String from) throws StraitChainException {
        return scsGetTransactionCount(from,DefaultBlockParameterName.PENDING);
    }

    @Override
    public BigInteger scsGetTransactionCount(String from, DefaultBlockParameterName blockParameterName) throws StraitChainException, NullPointerException {
        StraitChainParam param = new StraitChainParam();
        param.setMethod(StraitChainConstant.SCS_GET_TRANSACTION_COUNT);
        List<Object> list =new ArrayList<>();
        list.add(from);
        list.add(blockParameterName.getValue());
        param.setParams(list);
        StraitChainResponse response = chainRequest(param);
        return StraitChainUtil.toBigInteger(response.getResult().toString());
    }

    @Override
    public BigInteger scsGetBlockTransactionCountByHash(String blockHx) throws StraitChainException {
        StraitChainParam param = new StraitChainParam();
        param.setMethod(StraitChainConstant.SCS_GET_BLOCK_TRANSACTION_COUNT_BY_HASH);
        List<Object> list =new ArrayList<>();
        list.add(blockHx);
        param.setParams(list);
        StraitChainResponse response = chainRequest(param);
        return StraitChainUtil.toBigInteger(response.getResult().toString());
    }

    @Override
    public BigInteger scsGetBlockTransactionCountByNumber(Long blockNumber) throws StraitChainException {
        StraitChainParam param = new StraitChainParam();
        param.setMethod(StraitChainConstant.SCS_GET_BLOCK_TRANSACTION_COUNT_BY_NUMBER);
        List<Object> list =new ArrayList<>();
        list.add(blockNumber);
        param.setParams(list);
        StraitChainResponse response = chainRequest(param);
        return StraitChainUtil.toBigInteger(response.getResult().toString());
    }

    @Override
    public String scsGetCode(String address) throws StraitChainException {
        StraitChainParam param = new StraitChainParam();
        param.setMethod(StraitChainConstant.SCS_GET_CODE);
        List<Object> list =new ArrayList<>();
        list.add(address);
        list.add(DefaultBlockParameterName.LATEST.getValue());
        param.setParams(list);
        StraitChainResponse response = chainRequest(param);
        return response.getResult().toString();
    }

    @Override
    public String scsSendTransaction(ScsSendTransactionParam scsParam) throws StraitChainException {
        // nonce
        BigInteger nonce = scsGetTransactionCount(scsParam.getFrom());
        return scsSendTransaction(scsParam, nonce);
    }

    @Override
    public String scsSendTransaction(ScsSendTransactionParam scsParam,BigInteger nonce) throws StraitChainException {
        StraitChainParam param = new StraitChainParam();
        param.setMethod(StraitChainConstant.SCS_SEND_TRANSACTION);
        List<Object> list =new ArrayList<>();
        list.add(scsParam.getFrom());
        list.add(scsParam.getTo());
        list.add(scsParam.getGas());
        // gasPrice
        BigInteger integer = scsGasPrice();
        list.add("0x"+integer.toString(16));
        list.add(scsParam.getValue());
        list.add(scsParam.getContractSignData());
        list.add("0x"+nonce.toString(16));
        param.setParams(list);
        StraitChainResponse response = chainRequest(param);
        return response.getResult().toString();
    }

    @Override
    public String scsSendRawTransaction(String txValue) throws StraitChainException {
        List<Object> list =new ArrayList<>();
        list.add(txValue);
        StraitChainParam param = new StraitChainParam();
        param.setMethod(StraitChainConstant.SCS_SEND_RAW_TRANSACTION);
        param.setParams(list);
        StraitChainResponse response = chainRequest(param);
        return response.getResult().toString();
    }

    @Override
    public String scsSendRawTransaction(StraitChainSendRawTransactionParams params,String encode){
        RawTransaction rawTransaction = RawTransaction.createTransaction(
                params.getNonce(),
                params.getGasPrice(),
                params.getGasLimit(),
                params.getContractAddress(),
                encode);
        Credentials credentials = Credentials.create(params.getPrivateKey());
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String txValue = Numeric.toHexString(signedMessage);
        return scsSendRawTransaction(txValue);
    }

    @Override
    public String scsSendRawTransaction(String contractAddress, String fromAddress, String privateKey, String encode) throws StraitChainException, NullPointerException {
        BigInteger nonce = this.scsGetTransactionCount(fromAddress);
        BigInteger gasPrice = this.scsGasPrice();
        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce,
                gasPrice,
                defaultGasLimit,
                contractAddress,
                encode);
        Credentials credentials = Credentials.create(privateKey);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String txValue = Numeric.toHexString(signedMessage);
        return scsSendRawTransaction(txValue);
    }

    /**
     * 转移nft所属
     * @param params 参数
     * @return 交易哈希
     */
    @Override
    public String transferNftUser(StraitChainSendRawTxParam params){
        BigInteger nonce = scsGetTransactionCount(params.getFrom());
        return transferNftUser(params,defaultGasLimit,nonce);
    }

    /**
     * 转移nft所属
     * @param params 参数
     * @return 交易哈希
     */
    @Override
    public String transferNftUser(StraitChainSendRawTxParam params,BigInteger gasLimit){
        BigInteger nonce = scsGetTransactionCount(params.getFrom());
        return transferNftUser(params,gasLimit,nonce);
    }

    /**
     * 转移nft所属
     * @param params 参数
     * @return 交易哈希
     */
    @Override
    public String transferNftUser(StraitChainSendRawTxParam params,BigInteger gasLimit,BigInteger nonce){
        BigInteger gasPrice = scsGasPrice();
        Function function = new Function(
                StraitChainConstant.CONTRACT_TRANSFER_FROM,
                Arrays.asList(new Address(160, params.getFrom())
                        ,new Address(160, params.getTo())
                        ,new Uint256(params.getTokenId())),
                Collections.emptyList());
        String encode = FunctionEncoder.encode(function);





        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, params.getContractAddress(), encode);
        Credentials credentials = Credentials.create(params.getPrivateKey());
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String txValue = Numeric.toHexString(signedMessage);
        return scsSendRawTransaction(txValue);
    }


    @Override
    public BigInteger scsEstimateGas(ScsEstimateGasParam scsParam) throws StraitChainException {

        // nonce
        BigInteger nonce = scsGetTransactionCount(scsParam.getFrom());
        return scsEstimateGas(scsParam,nonce);
    }

    @Override
    public BigInteger scsEstimateGas(ScsEstimateGasParam scsParam,BigInteger nonce) throws StraitChainException {
        StraitChainParam param = new StraitChainParam();
        param.setMethod(StraitChainConstant.SCS_ESTIMATE_GAS);
        List<Object> list =new ArrayList<>();
        list.add(scsParam.getFrom());
        list.add(scsParam.getTo());
        list.add(scsParam.getGas());
        // gasPrice
        BigInteger integer = scsGasPrice();
        list.add("0x"+integer.toString(16));
        list.add(scsParam.getValue());
        list.add(scsParam.getContractSignData());
        list.add("0x"+nonce.toString(16));
        param.setParams(list);
        StraitChainResponse response = chainRequest(param);
        return StraitChainUtil.toBigInteger(response.getResult().toString());
    }

    @Override
    public String scsGetBlockByHashWithReturnHash(String address) throws StraitChainException {
        StraitChainResponse response = scsGetBlockByHash(address, false);
        return response.getResult().toString();
    }

    @Override
    public BlockInfoDto scsGetBlockByHashWithReturnObject(String address) throws StraitChainException {
        StraitChainResponse response = scsGetBlockByHash(address, true);
        return JSONObject.parseObject(response.getResult().toString(), BlockInfoDto.class);
    }

    private StraitChainResponse scsGetBlockByHash(String address,boolean returnObject){
        List<Object> list =new ArrayList<>();
        list.add(address);
        list.add(returnObject);
        StraitChainParam param = new StraitChainParam();
        param.setParams(list);
        param.setMethod(StraitChainConstant.SCS_GET_BLOCK_BY_HASH);
        return chainRequest(param);
    }

    @Override
    public String scsGetBlockByNumberWithReturnHash(String blockNumber) throws StraitChainException {
        StraitChainResponse response = scsGetBlockByNumber(blockNumber, false);
        return response.getResult().toString();
    }

    @Override
    public BlockInfoDto scsGetBlockByNumberWithReturnObject(String blockNumber) throws StraitChainException {
        StraitChainResponse response = scsGetBlockByNumber(blockNumber, true);
        return JSONObject.parseObject(response.getResult().toString(), BlockInfoDto.class);
    }

    private StraitChainResponse scsGetBlockByNumber(String blockNumber,boolean returnObject){
        List<Object> list =new ArrayList<>();
        list.add(blockNumber);
        list.add(returnObject);
        StraitChainParam param = new StraitChainParam();
        param.setParams(list);
        param.setMethod(StraitChainConstant.SCS_GET_BLOCK_BY_NUMBER);
        return chainRequest(param);
    }

    @Override
    public TransactionInfoDto scsGetTransactionReceipt(String txHash) throws StraitChainException {
        List<Object> list =new ArrayList<>();
        list.add(txHash);
        StraitChainParam param = new StraitChainParam();
        param.setParams(list);
        param.setMethod(StraitChainConstant.SCS_GET_TRANSACTION_RECEIPT);
        StraitChainResponse response = chainRequest(param);
        return JSONObject.parseObject(response.getResult().toString(),TransactionInfoDto.class);

    }

    @Override
    public TransactionInfoDto scsGetTransactionByHash(String txHash) throws StraitChainException {
        List<Object> list =new ArrayList<>();
        list.add(txHash);
        StraitChainParam param = new StraitChainParam();
        param.setParams(list);
        param.setMethod(StraitChainConstant.SCS_GET_TRANSACTION_BY_HASH);
        StraitChainResponse response = chainRequest(param);
        return JSONObject.parseObject(response.getResult().toString(),TransactionInfoDto.class);
    }

    @Override
    public String scsBlockNumber() throws StraitChainException {
        StraitChainParam param = new StraitChainParam();
        param.setMethod(StraitChainConstant.SCS_BLOCK_NUMBER);
        StraitChainResponse response = chainRequest(param);
        return response.getResult().toString();
    }

    @Override
    public String scsNftMint(StraitNftMintParam nftMintParam) throws StraitChainException {
        List<Object> list =new ArrayList<>();
        list.add(appId);
        list.add(nftMintParam.getNftName());
        list.add(nftMintParam.getCid());
        list.add(nftMintParam.getNftUri());
        list.add(nftMintParam.getCopyRight());
        list.add(nftMintParam.getIssuer());
        list.add(nftMintParam.getOperator());
        list.add(nftMintParam.getRemark());
        list.add(nftMintParam.getCount());
        list.add(nftMintParam.getOwner());
        list.add(nftMintParam.getContractAddress());
        list.add(nftMintParam.getCollectSn());
        list.add(nftMintParam.getServiceId());
        String md5 = StraitChainUtil.encryptDataByMd5(list, appKey);
        list.add(md5);
        StraitChainParam param = new StraitChainParam();
        param.setMethod(StraitChainConstant.SCS_NFT_MINT);
        param.setParams(list);
        StraitChainResponse response = chainRequest(param);
        return response.getResult().toString();
    }

    @Override
    public String scsDeployContract(String from,Integer count) throws StraitChainException {
        return scsDeployContract(from,count,ContractTypeEnum.DEFAULT);
    }

    @Override
    public String scsDeployContract(String from, Integer count, ContractTypeEnum contractTypeEnum) throws StraitChainException, NullPointerException {
        List<Object> list =new ArrayList<>();
        list.add(from);
        list.add(count);
        list.add(appId);
        if (contractTypeEnum!=ContractTypeEnum.DEFAULT){
            list.add(contractTypeEnum.getValue());
        }
        StraitChainParam param = new StraitChainParam();
        param.setMethod(StraitChainConstant.SCS_DEPLOY_CONTRACT);
        param.setParams(list);
        StraitChainResponse response = chainRequest(param);
        return response.getResult().toString();
    }

    @Override
    public String scsContractAddressByHash(String txHash) throws StraitChainException {
        List<Object> list =new ArrayList<>();
        list.add(txHash);
        StraitChainParam param = new StraitChainParam();
        param.setMethod(StraitChainConstant.SCS_CONTRACT_ADDRESS_BY_HASH);
        param.setParams(list);
        StraitChainResponse response = chainRequest(param);
        return response.getResult().toString();
    }

    @Override
    public String scsCall(ScsCallParam scsParam, String blockNumber) throws StraitChainException {
        BigInteger gasPrice = scsGasPrice();
        return scsCall(scsParam,blockNumber,gasPrice);
    }

    private String scsCall(String from,String contractAddress,String data){
        ScsCallParam param = new ScsCallParam();
        param.setFrom(from);
        param.setTo(contractAddress);
        param.setGas("0x34455");
        param.setValue("");
        param.setData(data);
        return scsCall(param,DefaultBlockParameterName.LATEST.getValue());
    }
    @Override
    public String scsCall(ScsCallParam scsParam, String blockNumber,BigInteger gasPrice) throws StraitChainException {
        scsParam.setGasPrice("0x"+gasPrice.toString(16));
        List<Object> list =new ArrayList<>();
        list.add(scsParam);
        list.add(blockNumber);
        StraitChainParam param = new StraitChainParam();
        param.setParams(list);
        param.setMethod(StraitChainConstant.SCS_CALL);
        StraitChainResponse response = chainRequest(param);
        return response.getResult().toString();
    }

    @Override
    public List<NftMintDto> scsGetTokenByHash(String txHash) throws StraitChainException {
        List<Object> list =new ArrayList<>();
        list.add(txHash);
        StraitChainParam param = new StraitChainParam();
        param.setMethod(StraitChainConstant.SCS_GET_TOKEN_BY_HASH);
        param.setParams(list);
        StraitChainResponse response = chainRequest(param);
        return JSONObject.parseArray(response.getResult().toString(), NftMintDto.class);
    }

    @Override
    public String scsGetEvidenceContractAddress() throws StraitChainException {
        List<Object> list =new ArrayList<>();
        StraitChainParam param = new StraitChainParam();
        param.setMethod(StraitChainConstant.SCS_GET_EVIDENCE_CONTRACT_ADDRESS);
        param.setParams(list);
        StraitChainResponse response = chainRequest(param);
        return response.getResult().toString();

    }

    @Override
    public String scsExistingEvidence(StraitChainExistingEvidenceParam existingEvidenceParam) throws StraitChainException {
        List<Object> list =new ArrayList<>();
        list.add(appId);
        list.add(existingEvidenceParam.getServiceId());
        list.add(existingEvidenceParam.getCid());
        list.add(existingEvidenceParam.getContent());
        list.add(existingEvidenceParam.getContractSignHex());
        String md5 = StraitChainUtil.encryptDataByMd5(list, appKey);
        list.add(md5);
        StraitChainParam param = new StraitChainParam();
        param.setMethod(StraitChainConstant.SCS_EXISTING_EVIDENCE);
        param.setParams(list);
        StraitChainResponse response = chainRequest(param);
        return response.getResult().toString();
    }


    @Override
    public void dcEvidence() throws StraitChainException {
        // 这里是方法签名，在com.shangchain.straitchain.StraitChainClient.dcEvidenceSignHex中已经写好
    }

    @Override
    public String dcEvidenceSignHex(StraitChainContractDcEvidenceSignHexParam param) throws StraitChainException {

        // 获取nonce
        BigInteger nonce = scsGetTransactionCount(param.getFrom());
        return dcEvidenceSignHex(param,nonce);
    }

    @Override
    public String dcEvidenceSignHex(StraitChainContractDcEvidenceSignHexParam param, BigInteger nonce) throws StraitChainException {
        // 获取合约地址
        String contractAddress = scsGetEvidenceContractAddress();

        // 获取gasPrice
        BigInteger gasPrice = scsGasPrice();

        // 方法编码
        Function function = new Function(
                StraitChainConstant.CONTRACT_EVIDENCE,
                Arrays.asList(new Utf8String(param.getCid()),new Utf8String(param.getContent())),
                Collections.emptyList());
        String encode = FunctionEncoder.encode(function);

        // 合约签名
        Credentials credentials = Credentials.create(param.getPrivateKey());
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, defaultGasLimit, contractAddress, encode);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        return Numeric.toHexString(signedMessage);
    }

    @Override
    public String ipfsUpload(String address, File file) throws StraitChainException {
        List<Object> list =new ArrayList<>();
        list.add(appId);
        list.add(address);

        String signData = StraitChainUtil.encryptDataByMd5(list, appKey);

        Map<String,Object> requestParametersMap = new HashMap<>(3);
        requestParametersMap.put("file",file);
        requestParametersMap.put("address",address);
        requestParametersMap.put("appId",appId);
        requestParametersMap.put("sign",signData);

        StraitChainResponse result;
        try {
            result = commonFileRequest(requestParametersMap, url + "/api/develop/ipfsUpload");
        } catch (Exception e) {
            String message = e.getMessage();
            if (StrUtil.contains(message, "已上传")) {
                log.warn("文件已上传："+e.getMessage());
                String[] s = message.split(" ");
                return s[1];
            }else {
                throw e;
            }
        }
        return result.getResult().toString();
    }

    @Override
    public String nftOwnerOf(String from, String contractAddress, Integer tokenId) throws StraitChainException {
        Function function = new Function(
                StraitChainConstant.CONTRACT_OWNER_OF,
                Collections.singletonList(new Uint256(tokenId)),
                Collections.emptyList());
        String encode = FunctionEncoder.encode(function);

        return scsCall(from, contractAddress,encode);

    }

    @Override
    public String nftOwnerOfExcludeZero(String from, String contractAddress, Integer tokenId) throws StraitChainException {
        String ownerOfAddress = nftOwnerOf(from, contractAddress, tokenId);
        return removeExtraZero(ownerOfAddress);
    }

    @Override
    public NftMintDto scsNftMintAlone(StraitNftMintParam nftMintParam) throws StraitChainException {
        List<Object> list =new ArrayList<>();
        list.add(appId);
        list.add(nftMintParam.getNftName());
        list.add(nftMintParam.getCid());
        list.add(nftMintParam.getNftUri());
        list.add(nftMintParam.getCopyRight());
        list.add(nftMintParam.getIssuer());
        list.add(nftMintParam.getOperator());
        list.add(nftMintParam.getRemark());
        list.add(nftMintParam.getCount());
        list.add(nftMintParam.getOwner());
        list.add(nftMintParam.getContractAddress());
        list.add(nftMintParam.getCollectSn());
        list.add(nftMintParam.getServiceId());
        String md5 = StraitChainUtil.encryptDataByMd5(list, appKey);
        list.add(md5);
        StraitChainParam param = new StraitChainParam();
        param.setMethod(StraitChainConstant.SCS_NFT_MINT_ALONE);
        param.setParams(list);
        StraitChainResponse response = chainRequest(param);
        return JSONObject.parseObject(response.getResult().toString(), NftMintDto.class);
    }

    @Override
    public String scsDigitalCollectionMint(DigitalCollectionMintParam nftMintParam){
        List<Object> list =new ArrayList<>();
        list.add(appId);
        list.add(nftMintParam.getName());
        list.add(nftMintParam.getNumber());
        list.add(nftMintParam.getNftUri());
        list.add(nftMintParam.getCopyRight());
        list.add(nftMintParam.getIssuer());
        list.add(nftMintParam.getOperator());
        list.add(nftMintParam.getRemark());
        list.add(nftMintParam.getCount());
        list.add(nftMintParam.getOwner());
        String md5 = StraitChainUtil.encryptDataByMd5(list, appKey);
        list.add(md5);
        StraitChainParam param = new StraitChainParam();
        param.setMethod(StraitChainConstant.SCS_DIGITAL_COLLECTION_MINT);
        param.setParams(list);
        StraitChainResponse response = chainRequest(param);
        return response.getResult().toString();
    }

    @Override
    public List<DigitalMintDto> scsDigitalCollectionList(String hash) {
        List<Object> list =new ArrayList<>();
        list.add(hash);
        StraitChainParam param = new StraitChainParam();
        param.setMethod(StraitChainConstant.SCS_DIGITAL_COLLECTION_LIST);
        param.setParams(list);
        StraitChainResponse response = chainRequest(param);
        return JSONObject.parseArray(response.getResult().toString(), DigitalMintDto.class);
    }

    @Override
    public String scsDigitalCollectionTransaction(DigitalCollectionTransactionParam digitalTxParam) {
        List<Object> list =new ArrayList<>();
        list.add(appId);
        list.add(digitalTxParam.getFrom());
        list.add(digitalTxParam.getTo());
        list.add(digitalTxParam.getTokenId());
        list.add(digitalTxParam.getNumber());
        String md5 = StraitChainUtil.encryptDataByMd5(list, appKey);
        list.add(md5);
        StraitChainParam param = new StraitChainParam();
        param.setMethod(StraitChainConstant.SCS_DIGITAL_COLLECTION_TRANSACTION);
        param.setParams(list);
        StraitChainResponse response = chainRequest(param);
        return response.getResult().toString();
    }

    @Override
    public String transferDrop(ScsTransferDropParam param,BigInteger nonce){
        return transferDrop(param,defaultGasLimit,nonce);
    }

    @Override
    public String transferDrop(ScsTransferDropParam param,BigInteger gasLimit,BigInteger nonce){
        // 获取gas
        BigInteger gasPrice = scsGasPrice();

        Credentials credentials = Credentials.create(param.getPrivateKey());

        if (param.getDrop() == null || param.getDrop() < 0) {
            throw new StraitChainException("转移得drop不能为空");
        }
        BigDecimal bigDecimal = BigDecimal.valueOf(param.getDrop()).multiply(BigDecimal.valueOf(Math.pow(10, 18)));
        BigInteger value = bigDecimal.toBigInteger();
        //创建交易
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, param.getToAddress(), value,"0x0");
        //签名Transaction，这里要对交易做签名
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signedMessage);
        //发送交易
        return scsSendRawTransaction(hexValue);
    }



    @Override
    public String nft4907SetUser(StraitNft4907SetUserParam params) throws StraitChainException, NullPointerException {
        Function function = new Function(
                StraitChainConstant.CONTRACT_4907_SET_USER,
                Arrays.asList(
                        // tokenId
                        new Uint256(params.getTokenId())
                        // user
                        ,new Address(160, params.getTo())
                        // expires
                        ,new Uint64(params.getExpires())
                ),
                Collections.emptyList());
        String encode = FunctionEncoder.encode(function);
        BigInteger nonce = scsGetTransactionCount(params.getFrom());
        BigInteger gasPrice = scsGasPrice();
        StraitChainSendRawTransactionParams rawParams = new StraitChainSendRawTransactionParams();
        rawParams.setNonce(nonce);
        rawParams.setGasPrice(gasPrice);
        rawParams.setGasLimit(defaultGasLimit);
        rawParams.setContractAddress(params.getContractAddress());
        rawParams.setTo(params.getTo());
        rawParams.setPrivateKey(params.getPrivateKey());

        return scsSendRawTransaction(rawParams,encode);
    }

    @Override
    public String nft4907UserOf(String from,String contractAddress, Integer tokenId) throws StraitChainException, NullPointerException {
        Function function = new Function(
                StraitChainConstant.CONTRACT_4907_USER_OF,
                Collections.singletonList(new Uint256(tokenId)),
                Collections.emptyList());
        String encode = FunctionEncoder.encode(function);

        return scsCall(from, contractAddress,encode);
    }

    @Override
    public String nft4907UserOfExcludeZero(String from, String contractAddress, Integer tokenId) throws StraitChainException, NullPointerException {
        String ownerOfAddress = nft4907UserOf(from, contractAddress, tokenId);
        return removeExtraZero(ownerOfAddress);
    }

    /**
     * 地址去0
     * version 2.2.0
     * @param address 地址，如果没有地址则返回null
     * @return 去0后的地址
     */
    private String removeExtraZero(String address){
        if (address.startsWith("0x")) {
            address = address.replace("0x", "");
        }
        if (StrUtil.isBlank(address)){
            return null;
        }

        BigInteger bigInteger = new BigInteger(address, 16);
        String hex = bigInteger.toString(16);
        StringBuilder sbr = new StringBuilder(hex);
        // 长度固定40位
        int fillZeroNumber = 40 - hex.length();
        if (fillZeroNumber>0){
            for (int i = 0; i < fillZeroNumber; i++) {
                sbr.insert(0, "0");
            }
        }
        return "0x"+ sbr;
    }

    @Override
    public Long nft4907UserExpires(String from,String contractAddress, Integer tokenId) throws StraitChainException, NullPointerException {
        Function function = new Function(
                StraitChainConstant.CONTRACT_4907_USER_EXPIRES,
                Collections.singletonList(new Uint256(tokenId)),
                Collections.emptyList());
        String encode = FunctionEncoder.encode(function);

        String result = scsCall(from, contractAddress,encode);
        result = result.replace("0x", "");
        return Long.parseLong(result, 16);
    }

    @Override
    public String nftTransferOfOwnerShip(String contractAddress,String fromAddress,String toAddress,String privateKey) throws StraitChainException, NullPointerException {
        Function function = new Function(
                StraitChainConstant.CONTRACT_TRANSFER_OF_OWNER_SHIP,
                Collections.singletonList(new Address(toAddress)),
                Collections.emptyList());
        String encode = FunctionEncoder.encode(function);
        return scsSendRawTransaction(contractAddress,fromAddress,privateKey, encode);
    }

    @Override
    public Boolean nftSetLockTimeFlag(String contractAddress,String fromAddress,String privateKey, Boolean lock) throws StraitChainException, NullPointerException {
        Function function = new Function(
                StraitChainConstant.CONTRACT_SET_NFT_LOCK_TIME_FLAG,
                Collections.singletonList(new Bool(lock)),
                Collections.emptyList());
        String encode = FunctionEncoder.encode(function);
        String result = scsSendRawTransaction(contractAddress, fromAddress, privateKey, encode);
        return Boolean.parseBoolean(result);
    }

    @Override
    public String nftGetLockTimeFlag(String contractAddress,String fromAddress,String privateKey) throws StraitChainException, NullPointerException {
        Function function = new Function(
                StraitChainConstant.CONTRACT_GET_NFT_LOCK_TIME_FLAG,
                Collections.emptyList(),
                Collections.emptyList());
        String encode = FunctionEncoder.encode(function);
        return scsCall(fromAddress, contractAddress,encode);
    }

    @Override
    public String nftSetLockCountFlag(String contractAddress,String fromAddress,String privateKey, Boolean lock) throws StraitChainException, NullPointerException {
        Function function = new Function(
                StraitChainConstant.CONTRACT_SET_NFT_LOCK_COUNT_FLAG,
                Collections.singletonList(new Bool(lock)),
                Collections.emptyList());
        String encode = FunctionEncoder.encode(function);
        return scsSendRawTransaction(contractAddress,fromAddress,privateKey, encode);
    }

    @Override
    public Boolean nftGetLockCountFlag(String contractAddress,String fromAddress,String privateKey) throws StraitChainException, NullPointerException {
        Function function = new Function(
                StraitChainConstant.CONTRACT_GET_NFT_LOCK_COUNT_FLAG,
                Collections.emptyList(),
                Collections.emptyList());
        String encode = FunctionEncoder.encode(function);
        String result = scsCall(fromAddress, contractAddress, encode);
        result = result.replace("0x", "");
        return Long.parseLong(result,16) == 1;
    }

    @Override
    public String nftSetLockTime(String contractAddress,String fromAddress,String privateKey, Long seconds) throws StraitChainException, NullPointerException {
        Function function = new Function(
                StraitChainConstant.CONTRACT_SET_NFT_LOCK_TIME,
                Collections.singletonList(new Uint256(seconds)),
                Collections.emptyList());
        String encode = FunctionEncoder.encode(function);
        return scsSendRawTransaction(contractAddress,fromAddress,privateKey, encode);
    }

    @Override
    public Long nftGetLockTime(String contractAddress,String fromAddress,String privateKey) throws StraitChainException, NullPointerException {
        Function function = new Function(
                StraitChainConstant.CONTRACT_GET_NFT_LOCK_TIME,
                Collections.emptyList(),
                Collections.emptyList());
        String encode = FunctionEncoder.encode(function);
        String result = scsCall(fromAddress,contractAddress, encode);
        result = result.replace("0x", "");
        return Long.parseLong(result, 16);
    }

    @Override
    public String nftSetLockCount(String contractAddress,String fromAddress,String privateKey, Long lockCount) throws StraitChainException, NullPointerException {
        Function function = new Function(
                StraitChainConstant.CONTRACT_SET_NFT_LOCK_COUNT,
                Collections.singletonList(new Uint256(lockCount)),
                Collections.emptyList());
        String encode = FunctionEncoder.encode(function);
        return scsSendRawTransaction(contractAddress, fromAddress, privateKey, encode);
    }

    @Override
    public Long nftGetLockCount(String contractAddress,String fromAddress,String privateKey) throws StraitChainException, NullPointerException {
        Function function = new Function(
                StraitChainConstant.CONTRACT_GET_NFT_LOCK_COUNT,
                Collections.emptyList(),
                Collections.emptyList());
        String encode = FunctionEncoder.encode(function);
        String s = scsCall(fromAddress,contractAddress, encode);
        if (StrUtil.startWith(s, "0x")){
            s = s.replace("0x", "");
        }
        return Long.parseLong(s,16);
    }

    @Override
    public String nftTokenUri(String contractAddress,String fromAddress,Long tokenId) throws StraitChainException, NullPointerException {
        Function function = new Function(
                StraitChainConstant.CONTRACT_TOKEN_URI,
                Collections.singletonList(new Uint256(tokenId)),
                Collections.emptyList());
        String encode = FunctionEncoder.encode(function);
        return scsCall(fromAddress,contractAddress, encode);
    }

    @Override
    public Long nftTotalSupply(String contractAddress,String fromAddress) throws StraitChainException, NullPointerException {
        Function function = new Function(
                StraitChainConstant.CONTRACT_TOTAL_SUPPLY,
                Collections.emptyList(),
                Collections.emptyList());
        String encode = FunctionEncoder.encode(function);
        String s = scsCall(fromAddress,contractAddress, encode);
        return Long.parseLong(s);
    }

    @Override
    public String nftGetOwnerOfContract(String contractAddress,String fromAddress) throws StraitChainException, NullPointerException {
        Function function = new Function(
                StraitChainConstant.CONTRACT_GET_OWNER_OF_CONTRACT,
                Collections.emptyList(),
                Collections.emptyList());
        String encode = FunctionEncoder.encode(function);
        String address = scsCall(fromAddress,contractAddress, encode);
        return removeExtraZero(address);
    }

    @Override
    public Nft1155MintDto scs1155NftMint(StraitNftMintParam param) throws StraitChainException, NullPointerException {
        List<Object> list =new ArrayList<>();
        list.add(appId);
        list.add(param.getNftName());
        list.add(param.getCid());
        list.add(param.getNftUri());
        list.add(param.getCopyRight());
        list.add(param.getIssuer());
        list.add(param.getOperator());
        list.add(param.getRemark());
        list.add(param.getCount());
        list.add(param.getOwner());
        list.add(param.getContractAddress());
        list.add(param.getCollectSn());
        list.add(param.getServiceId());
        String md5 = StraitChainUtil.encryptDataByMd5(list, appKey);
        list.add(md5);
        StraitChainParam request = new StraitChainParam();
        request.setMethod(StraitChainConstant.SCS_1155_NFT_MINT);
        request.setParams(list);
        StraitChainResponse response = chainRequest(request);
        return JSONObject.parseObject(response.getResult().toString(), Nft1155MintDto.class);
    }

    @Override
    public String nft1155SafeTransferFrom(Nft1155SafeTransferFromParam param){
        BigInteger nonce = scsGetTransactionCount(param.getFrom(), DefaultBlockParameterName.LATEST);
        return nft1155SafeTransferFrom(param,nonce,defaultGasPrice,defaultGasLimit);
    }

    @Override
    public String nft1155SafeTransferFrom(Nft1155SafeTransferFromParam param, BigInteger nonce,BigInteger gasPrice,BigInteger gasLimit) {

        Function function = new Function(
                StraitChainConstant.CONTRACT_1155_SAFE_TRANSFER_FROM,
                Arrays.asList(
                        new Address(160, param.getFrom())
                        ,new Address(160, param.getTo())
                        ,new Uint256(param.getTokenId())
                        ,new Uint256(param.getAmount())
                        ,new DynamicBytes(param.getData().getBytes(StandardCharsets.UTF_8))
                ),
                Collections.emptyList());
        String encode = FunctionEncoder.encode(function);
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, param.getContractAddress(), encode);
        Credentials credentials = Credentials.create(param.getPrivateKey());
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String txValue = Numeric.toHexString(signedMessage);
        return scsSendRawTransaction(txValue);
    }

    @Override
    public Long nft1155BalanceOf(Nft1155BalanceOfParam param) {
        Function function = new Function(
                StraitChainConstant.CONTRACT_1155_BALANCE_OF,
                Arrays.asList(
                    new Address(160, param.getAccount())
                    ,new Uint256(param.getTokenId())
                ),
                Collections.emptyList());
        String encode = FunctionEncoder.encode(function);
        String result = scsCall(param.getFrom(), param.getContractAddress(), encode);
        result = result.replace("0x", "");
        return Long.parseLong(result, 16);
    }

    @Override
    public List<Long> nft1155BalanceOfBatch(Nft1155BalanceOfBatchParam param) {
        List<String> accounts = param.getAccounts();
        List<Address> addresses = new ArrayList<>(accounts.size());
        for (String account : accounts) {
            Address address = new Address(160, account);
            addresses.add(address);
        }

        List<Long> tokenIds = param.getTokenIds();
        List<Uint256> tokenIdes = new ArrayList<>(tokenIds.size());
        for (Long tokenId : tokenIds) {
            Uint256 unit = new Uint256(tokenId);
            tokenIdes.add(unit);
        }

        Function function = new Function(
                StraitChainConstant.CONTRACT_1155_BALANCE_OF_BATCH,
                Arrays.asList(
                        new DynamicArray<>(Address.class, addresses)
                        ,new DynamicArray<>(Uint256.class, tokenIdes)
                ),
                // output参数
                Collections.singletonList(new TypeReference<DynamicArray<Uint256>>() {}));
        String encode = FunctionEncoder.encode(function);
        String result = scsCall(param.getFrom(), param.getContractAddress(),encode);
        List<Type> types = FunctionReturnDecoder.decode(result, function.getOutputParameters());
        // 根据function的output取
        List<Type> list = (List<Type>)types.get(0).getValue();
        List<Long> resultAmount = new ArrayList<>();
        list.forEach((x-> resultAmount.add(((BigInteger)x.getValue()).longValue())));
        return resultAmount;
    }

    @Override
    public Nft1155MintDetailDto nft1155VerificationNft(Nft1155VerificationNftParam param) {
        Function function = new Function(
                StraitChainConstant.CONTRACT_1155_VERIFICATION_NFT,
                Collections.singletonList(
                        new Uint256(param.getTokenId())
                ),
                Arrays.asList(
                        new TypeReference<Uint256>() {},
                        new TypeReference<Address>() {},
                        new TypeReference<Uint256>() {},
                        new TypeReference<DynamicArray<Utf8String>>(){}
                )
        );
        String encode = FunctionEncoder.encode(function);
        String result = scsCall(param.getFrom(), param.getContractAddress(), encode);
        List<Type> decode = DefaultFunctionReturnDecoder.decode(result, function.getOutputParameters());
        Nft1155MintDetailDto dto = new Nft1155MintDetailDto();
        dto.setTokenId(((Uint256) decode.get(0)).getValue().longValue());
        dto.setMintOwner(((Address) decode.get(1)).getValue());
        dto.setAmount(((Uint256) decode.get(2)).getValue().longValue());

        DynamicArray<Utf8String> dynamicArray = (DynamicArray<Utf8String>) decode.get(3);
        List<String> data = new ArrayList<>(dynamicArray.getValue().size());
        for (Utf8String o : dynamicArray.getValue()) {
            data.add(o.getValue());
        }
        dto.setMintData(data);
        return dto;
    }
}
