package com.shangchain.straitchain;

import com.shangchain.straitchain.client.SNSRegister;
import com.shangchain.straitchain.client.SNSResolver;
import com.shangchain.straitchain.utils.SNSUtil;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

public class StraitsRealmClient {
    static String web3Node_prod = "https://backend.straitchain.com/webclient/api/develop/action?chainCode=ScOcc03001";
    //参数
    static BigInteger gasLimit = BigInteger.valueOf(100000);
    static BigInteger gasPrice = BigInteger.valueOf(Double.valueOf(564e9).longValue());
    static Long chainId_prod = 20220331L;

    /**
     * 构建解析器
     * @param ownerPrivateKey
     * @param contractAddress
     * @param node
     * @return
     * @throws Exception
     */
    private static SNSResolver createResolver(String ownerPrivateKey, String contractAddress, byte[] node) throws Exception{
        Service service = new HttpService(web3Node_prod);
        Web3j web3j = Web3j.build(service);
        Credentials credentials = Credentials.create(ownerPrivateKey);
        TransactionManager transactionManager = new RawTransactionManager(web3j, credentials, chainId_prod);
        ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);
        SNSRegister register = SNSRegister.load(contractAddress, web3j, transactionManager, gasProvider);
        String address = register.resolver(node).send();
        SNSResolver resolver = SNSResolver.load(address, web3j, transactionManager, gasProvider);
        return resolver;
    }

    /**
     * 构建注册器
     * @param ownerPrivateKey
     * @param contractAddress
     * @return
     * @throws Exception
     */
    private static SNSRegister createRegister(String ownerPrivateKey,String contractAddress){
        Service service = new HttpService(web3Node_prod);
        Web3j web3j = Web3j.build(service);
        Credentials credentials = Credentials.create(ownerPrivateKey);
        TransactionManager transactionManager = new RawTransactionManager(web3j, credentials, chainId_prod);
        ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);
        SNSRegister register = SNSRegister.load(contractAddress, web3j, transactionManager, gasProvider);
        return register;
    }


    /**
     * 获取自定义属性值
     * @param rootName 根域名
     * @param oneLevelName 一级域名
     * @param ownerPrivateKey 域名属性值
     * @param contractAddress 合约地址
     * @param key 自定义属性KEY值
     * @return
     * @throws Exception
     */
    public static String getRealmAttribute(String rootName,String oneLevelName,String ownerPrivateKey,String contractAddress,String key) throws Exception{
        byte[] node = SNSUtil.hashEns(rootName + "." + oneLevelName);
        SNSResolver resolver = createResolver(ownerPrivateKey,contractAddress,node);
        String value = resolver.text(node, key).send();
        return value;
    }



    /**
     * 设置域名的自定义属性值（同步）
     * @param rootName 根域名
     * @param oneLevelName 一级域名
     * @param ownerPrivateKey 域名所有者
     * @param contractAddress 合约地址
     * @param key 属性key值
     * @param value 属性值
     * @return
     * @throws Exception
     */
    public static TransactionReceipt setRealmAttribute(String rootName,String oneLevelName,String ownerPrivateKey,String contractAddress,String key,String value)throws Exception{
        byte[] node = SNSUtil.hashEns(rootName + "." + oneLevelName);
        SNSResolver resolver = createResolver(ownerPrivateKey,contractAddress,node);
        RemoteFunctionCall<TransactionReceipt> call = resolver.setText(node, key, value);
        TransactionReceipt receipt = call.send();
        return receipt;
    }
    /**
     * 设置域名的自定义属性值（同步）
     * @param rootName 根域名
     * @param oneLevelName 一级域名
     * @param ownerPrivateKey 域名所有者
     * @param contractAddress 合约地址
     * @param key 属性key值
     * @param value 属性值
     * @return
     * @throws Exception
     */
    public static  CompletableFuture<TransactionReceipt> setRealmAttributeAsync(String rootName,String oneLevelName,String ownerPrivateKey,String contractAddress,String key,String value)throws Exception{
        byte[] node = SNSUtil.hashEns(rootName + "." + oneLevelName);
        SNSResolver resolver = createResolver(ownerPrivateKey,contractAddress,node);
        RemoteFunctionCall<TransactionReceipt> call = resolver.setText(node, key, value);
        CompletableFuture<TransactionReceipt> receipt = call.sendAsync();
        return receipt;
    }



    /**
     * 获取域名的相关解析地址
     * @param rootName 根域名
     * @param oneLevelName 一级域名
     * @param ownerPrivateKey 钱包账户私钥
     * @param contractAddress 合约地址
     * @return
     * @throws Exception
     */
    public static String getRealmResolveAddress(String rootName,String oneLevelName,String ownerPrivateKey,String contractAddress) throws Exception {
        byte[] node = SNSUtil.hashEns(rootName + "." + oneLevelName);
        SNSResolver resolver = createResolver(ownerPrivateKey,contractAddress,node);
        String addr = resolver.addr(node).send();
        //如果是0地址, 那么默认解析地址为owner
        if (SNSUtil.isZeroAddress(addr)) {
            //当解析地址为0地址，那么域名的owner未设置解析地址，此时owner为解析出的地址
            return "0x";
        }
        return addr;
    }

    /**
     * 设置域名的解析地址（同步）
     * @param rootName 根域名
     * @param oneLevelName 一级域名
     * @param ownerPrivateKey 域名所有者私钥
     * @param contractAddress 合约地址
     * @param resolveAddress 解析到某个地址
     * @return
     * @throws Exception
     */
    public static TransactionReceipt setRealmResolveAddress(String rootName,String oneLevelName,String ownerPrivateKey,String contractAddress,String resolveAddress) throws Exception {
        byte[] node = SNSUtil.hashEns(rootName + "." + oneLevelName);
        SNSResolver resolver = createResolver(ownerPrivateKey,contractAddress,node);
        RemoteFunctionCall<TransactionReceipt> call = resolver.setAddr(node, resolveAddress);
        //同步发送交易
        TransactionReceipt receipt = call.send();
        return receipt;
    }

    /**
     * 设置域名的解析地址（异步）
     * @param rootName 根域名
     * @param oneLevelName 一级域名
     * @param ownerPrivateKey 域名所有者私钥
     * @param contractAddress 合约地址
     * @param resolveAddress 解析到某个地址
     * @return
     * @throws Exception
     */
    public static CompletableFuture<TransactionReceipt> setRealmResolveAddressAsync(String rootName,String oneLevelName,String ownerPrivateKey,String contractAddress,String resolveAddress) throws Exception {
        byte[] node = SNSUtil.hashEns(rootName + "." + oneLevelName);
        SNSResolver resolver = createResolver(ownerPrivateKey,contractAddress,node);
        RemoteFunctionCall<TransactionReceipt> call = resolver.setAddr(node, resolveAddress);
        //同步发送交易
        CompletableFuture<TransactionReceipt> receipt = call.sendAsync();
        return receipt;
    }

    /**
     * 查询域名当前所属者
     * @param rootName 根域名名称
     * @param oneLevelName 一级域名名称
     * @return
     */
    public static String ownerOf(String rootName,String oneLevelName,String ownerPrivateKey,String contractAddress) throws Exception{
        byte[] node = SNSUtil.hashEns(rootName + "." + oneLevelName);
        SNSRegister register = createRegister(ownerPrivateKey,contractAddress);
        String owner = register.owner(node).send();
        return owner;
    }

    /**
     * 注册子域名（同步）
     * @param ownerPrivateKey 当前域名所属者
     * @param toUserAddress 转移到该地址下
     * @param rootName 根域名
     * @param oneLevelName 一级域名
     * @param contractAddress 合约地址
     * @throws Exception
     */
    public static TransactionReceipt registerOrTransform(String ownerPrivateKey,String toUserAddress,String rootName,String oneLevelName,String contractAddress) throws Exception{
        //初始化web3j对象
        SNSRegister register = createRegister(ownerPrivateKey,contractAddress);
        RemoteFunctionCall<TransactionReceipt> call = register.setSubnodeOwner(SNSUtil.hashEns(rootName), SNSUtil.hashEns(oneLevelName), toUserAddress);
        TransactionReceipt receipt = call.send();
        return receipt;
    }

    /**
     * 注册子域名（异步）
     * @param ownerPrivateKey 当前域名所属者
     * @param toUserAddress 转移到该地址下
     * @param rootName 根域名
     * @param oneLevelName 一级域名
     * @param contractAddress 合约地址
     * @throws Exception
     */
    public static CompletableFuture<TransactionReceipt> registerOrTransformAsync(String ownerPrivateKey,String toUserAddress,String rootName,String oneLevelName,String contractAddress) throws Exception{
        SNSRegister register = createRegister(ownerPrivateKey,contractAddress);
        RemoteFunctionCall<TransactionReceipt> call = register.setSubnodeOwner(SNSUtil.hashEns(rootName), SNSUtil.hashEns(oneLevelName), toUserAddress);
        CompletableFuture<TransactionReceipt> transactionReceiptCompletableFuture = call.sendAsync();
       /* transactionReceiptCompletableFuture.thenAccept((result)->{
            System.out.println("返回的交易结果result:"+result.isStatusOK()+"================"+result.getTransactionHash());
        });*/
        return transactionReceiptCompletableFuture;
    }

    /**
     * 子域名用户之间转移（同步）
     * @param ownerPrivateKey
     * @param toUserAddress
     * @param oneLevelName
     * @param contractAddress
     * @return
     * @throws Exception
     */
    public static TransactionReceipt transform(String ownerPrivateKey,String toUserAddress,String oneLevelName,String contractAddress) throws Exception{
        //初始化web3j对象
        SNSRegister register = createRegister(ownerPrivateKey,contractAddress);
        RemoteFunctionCall<TransactionReceipt> call = register.setOwner(SNSUtil.hashEns(oneLevelName), toUserAddress);
        TransactionReceipt receipt = call.send();
        return receipt;
    }

    /**
     * 子域名用户之间转移（异步）
     * @param ownerPrivateKey
     * @param toUserAddress
     * @param oneLevelName
     * @param contractAddress
     * @return
     * @throws Exception
     */
    public static CompletableFuture<TransactionReceipt> transformAsync(String ownerPrivateKey,String toUserAddress,String oneLevelName,String contractAddress) throws Exception{
        //初始化web3j对象
        SNSRegister register = createRegister(ownerPrivateKey,contractAddress);
        RemoteFunctionCall<TransactionReceipt> call = register.setOwner(SNSUtil.hashEns(oneLevelName), toUserAddress);
        CompletableFuture<TransactionReceipt> transactionReceiptCompletableFuture = call.sendAsync();
        return transactionReceiptCompletableFuture;
    }
}
