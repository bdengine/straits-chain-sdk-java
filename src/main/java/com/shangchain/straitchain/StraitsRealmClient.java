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

    //    static Long chainId_prod = 80001L;
    public StraitsRealmClient() {

    }
    public StraitsRealmClient(String url, Long chainProd) {
        web3Node_prod = url;
        chainId_prod = chainProd;
    }
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
    private static SNSRegister createRegister(String ownerPrivateKey,String contractAddress,String newUrl){
        Service service = new HttpService(newUrl==null?web3Node_prod:newUrl);
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
    public String getRealmAttribute(String rootName,String oneLevelName,String ownerPrivateKey,String contractAddress,String key) throws Exception{
        byte[] node = SNSUtil.hashEns(oneLevelName + "." + rootName);
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
    public TransactionReceipt setRealmAttribute(String rootName,String oneLevelName,String ownerPrivateKey,String contractAddress,String key,String value)throws Exception{
        byte[] node = SNSUtil.hashEns(oneLevelName + "." + rootName);
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
    public  CompletableFuture<TransactionReceipt> setRealmAttributeAsync(String rootName,String oneLevelName,String ownerPrivateKey,String contractAddress,String key,String value)throws Exception{
        byte[] node = SNSUtil.hashEns( oneLevelName + "." + rootName);
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
    public String getRealmResolveAddress(String rootName,String oneLevelName,String ownerPrivateKey,String contractAddress) throws Exception {
        byte[] node = SNSUtil.hashEns(oneLevelName+ "." + rootName);
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
    public TransactionReceipt setRealmResolveAddress(String rootName,String oneLevelName,String ownerPrivateKey,String contractAddress,String resolveAddress) throws Exception {
        byte[] node = SNSUtil.hashEns(oneLevelName + "." + rootName);
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
    public CompletableFuture<TransactionReceipt> setRealmResolveAddressAsync(String rootName,String oneLevelName,String ownerPrivateKey,String contractAddress,String resolveAddress) throws Exception {
        byte[] node = SNSUtil.hashEns( oneLevelName + "." + rootName);
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
    public String ownerOf(String rootName,String oneLevelName,String ownerPrivateKey,String contractAddress) throws Exception{
        byte[] node = SNSUtil.hashEns(oneLevelName + "." + rootName);
        SNSRegister register = createRegister(ownerPrivateKey,contractAddress,null);
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
    public TransactionReceipt registerOrTransform(String ownerPrivateKey,String toUserAddress,String rootName,String oneLevelName,String contractAddress) throws Exception{
        //初始化web3j对象
        SNSRegister register = createRegister(ownerPrivateKey,contractAddress,web3Node_prod+"&rootName="+rootName+"&oneLevelName="+oneLevelName);
        RemoteFunctionCall<TransactionReceipt> call = register.setSubnodeOwner(SNSUtil.hashEns(rootName), SNSUtil.hashLabel(oneLevelName), toUserAddress);
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
    public CompletableFuture<TransactionReceipt> registerOrTransformAsync(String ownerPrivateKey,String toUserAddress,String rootName,String oneLevelName,String contractAddress) throws Exception{
        SNSRegister register = createRegister(ownerPrivateKey,contractAddress,web3Node_prod+"&rootName="+rootName+"&oneLevelName="+oneLevelName);
        RemoteFunctionCall<TransactionReceipt> call = register.setSubnodeOwner(SNSUtil.hashEns(rootName), SNSUtil.hashLabel(oneLevelName), toUserAddress);
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
    public TransactionReceipt transform(String ownerPrivateKey,String toUserAddress,String rootName,String oneLevelName,String contractAddress) throws Exception{
        //初始化web3j对象
        SNSRegister register = createRegister(ownerPrivateKey,contractAddress,null);
        RemoteFunctionCall<TransactionReceipt> call = register.setOwner(SNSUtil.hashEns(oneLevelName+ "." + rootName), toUserAddress);
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
    public CompletableFuture<TransactionReceipt> transformAsync(String ownerPrivateKey,String toUserAddress,String rootName,String oneLevelName,String contractAddress) throws Exception{
        //初始化web3j对象
        SNSRegister register = createRegister(ownerPrivateKey,contractAddress,null);
        RemoteFunctionCall<TransactionReceipt> call = register.setOwner(SNSUtil.hashEns(oneLevelName+ "." + rootName), toUserAddress);
        CompletableFuture<TransactionReceipt> transactionReceiptCompletableFuture = call.sendAsync();
        return transactionReceiptCompletableFuture;
    }

    public static void main(String[] args) throws Exception {
        StraitsRealmClient straitsRealmClient = new StraitsRealmClient();
//        TransactionReceipt receipt = registerOrTransform("0xd05c6f7658fc9f0d043c33021f53cf479b5a71052b226f60cc1576a4979c0498", "0x580DDF56De8780cab27412C92f88DfF0fC811898", "meta13", "12345", "0x399216284A57078A0970417e0a9f873d8647837A");
//        System.out.println(receipt.getTransactionHash()+"================"+receipt.isStatusOK());
       /* TransactionReceipt meta1 = transform("0x78e1259584d44928bd2868d9f4ed5763f66e79a2f99fb894aa307216a3e93eee", "0x29be7019e6A97a96AEa32F205854D8Bd984742DB", "meta13", "12345", "0x399216284A57078A0970417e0a9f873d8647837A");
        System.out.println(meta1.getTransactionHash()+"================"+meta1.isStatusOK());*/
        String s = straitsRealmClient.ownerOf("meta13", "12345", "0xd05c6f7658fc9f0d043c33021f53cf479b5a71052b226f60cc1576a4979c0498", "0x399216284A57078A0970417e0a9f873d8647837A");
        System.out.println(s);
    }
}
