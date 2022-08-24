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
import com.shangchain.straitchain.exception.StraitChainException;
import com.shangchain.straitchain.params.*;
import com.shangchain.straitchain.service.*;
import com.shangchain.straitchain.utils.StraitChainUtil;
import lombok.Data;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.utils.Numeric;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
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
        IBusiness
{
    private String appId;
    private String appKey;
    private String url;
    private BigInteger defaultGasLimit = new BigInteger("150000");

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

    public StraitChainClient() {
    }

    /**
     * 由于有些项目会造成okhttp冲突，所以改为hutool请求
     * @param requestBody json串
     * @param url 请求地址
     * @return 响应
     */
    protected StraitChainResponse commonRequest(String requestBody, String url){
        HttpRequest request = HttpUtil.createPost(url).contentType(ContentType.JSON.getValue()).timeout(6000);
        HttpResponse response = request.body(requestBody).execute();
        // 状态码范围在200~299内
        if (response.isOk()){
            StraitChainResponse result = JSONObject.parseObject(response.body(), StraitChainResponse.class);
            if (result.getError()!=null){
                throw new StraitChainException(result.getError().getMessage(),result.getError().getCode());
            }
            return result;
        }
        throw new StraitChainException("请求失败："+response.body());
    }


    protected StraitChainResponse commonFileRequest(Map<String,Object> parametersMap, String url){
        HttpResponse response = HttpRequest.post(url).form(parametersMap).timeout(6000).execute();
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
        String chainUri = "/api/develop/straits/action";
        List<Object> paramList = straitChainParam.getParams();
        for (int i = 0; i < paramList.size(); i++) {
            Object obj = paramList.get(i);
            if (obj == null) {
                throw new StraitChainException(straitChainParam.getMethod()+"，第"+i+"个参数为null，参数不允许为null！");
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
        StraitChainParam param = new StraitChainParam();
        param.setMethod(StraitChainConstant.SCS_GET_TRANSACTION_COUNT);
        List<Object> list =new ArrayList<>();
        list.add(from);
        list.add(DefaultBlockParameterName.PENDING.getValue());
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
        List<Object> list =new ArrayList<>();
        list.add(from);
        list.add(count);
        list.add(appId);
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
        BigInteger gasLimit = new BigInteger("110000");
        Credentials credentials = Credentials.create(param.getPrivateKey());
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, contractAddress, encode);
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

        StraitChainResponse result = commonFileRequest(requestParametersMap, url + "/api/develop/ipfsUpload");
        return result.getResult().toString();
    }

    @Override
    public String nftOwnerOf(String from, String contractAddress, Integer tokenId) throws StraitChainException {
        Function function = new Function(
                StraitChainConstant.CONTRACT_OWNER_OF,
                Collections.singletonList(new Uint256(tokenId)),
                Collections.emptyList());
        String encode = FunctionEncoder.encode(function);

        ScsCallParam param = new ScsCallParam();
        param.setFrom(from);
        param.setTo(contractAddress);
        param.setGas("0x34455");
        param.setValue("");
        param.setData(encode);
        return scsCall(param, DefaultBlockParameterName.LATEST.getValue());

    }

    @Override
    public String nftOwnerOfExcludeZero(String from, String contractAddress, Integer tokenId) throws StraitChainException {
        String ownerOfAddress = nftOwnerOf(from, contractAddress, tokenId);
        if (ownerOfAddress.startsWith("0x")) {
            ownerOfAddress = ownerOfAddress.replace("0x", "");
        }
        BigInteger bigInteger = new BigInteger(ownerOfAddress, 16);
        return "0x"+bigInteger.toString(16);
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
        String hexValue = Numeric.toHexString(signedMessage);;
        //发送交易
        return scsSendRawTransaction(hexValue);
    }
}