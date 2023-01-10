# 海峡链开发者平台Java开发工具包

## 相关示例位于test文件夹下

## StraitChainClient 客户端使用说明

1. 将com.shangchain.straitchain下的源文件复制粘贴到对应的工程中，方便使用和修改
2. 初始化参数 com.shangchain.straitchain.config.StraitChainInit
```java
@Configuration
public class StraitChainInit {
    @Bean
    public StraitChainClient init(){
        StraitChainClient client = new StraitChainClient();
        client.setAppId(appId);
        client.setAppKey(appKey);
        // 这里url不需要加请求后缀，在client中会加上后缀/api/develop/straits/action
        client.setUrl(url);
        return client;
    }
}
```
3. 异常捕获，sdk没有对client请求进行全局异常的捕获，需要使用者在使用的时候对异常进行捕获处理，常用异常：NullPointerException、IORuntimeException、StraitChainException、SocketTimeoutException

## 代码示例
### 铸造nft
初始化客户端
```java
StraitChainClient client = new StraitChainClient();
client.setAppId(appId);
client.setAppKey(appKey);
client.setUrl("https://backend.straitchain.com/webclient");

```
部署合约
```java
// nft个数
int nftMintCount = 10;
String from = 通行证地址;
// 部署合约
String contractTxHash = client.scsDeployContract(from,nftMintCount);
```
查询合约地址
```java
// 根据合约哈希查询合约地址
String contractAddress = client.scsContractAddressByHash(contractTxHash);
```
铸造nft，参数不能为null，必须为空串
```java
StraitNftMintParam nftMintParam = new StraitNftMintParam();
nftMintParam.setNftName("这是nft名字");
nftMintParam.setCid("这是ipfs上传后的cid，没有可不要");
nftMintParam.setNftUri("http://nps.shang-chain.com:30023/profile/tmp/ahjdfhf.json");
nftMintParam.setCopyRight("版权方");
nftMintParam.setIssuer("发行方");
nftMintParam.setOperator("运营方");
nftMintParam.setRemark("这是一个备注");
nftMintParam.setCount(nftMintCount);
nftMintParam.setOwner(from);
nftMintParam.setContractAddress(contractAddress);
// 业务需求，全平台唯一。如：公司缩写+uuid
nftMintParam.setCollectSn("strait_chain_test_1");
// 对应的服务ID，没有就空
nftMintParam.setServiceId("");
String nftMintHash = client.scsNftMint(nftMintParam);
```
查询nft铸造状态
```java
// 查询铸造状态
List<NftMintDto> list = client.scsGetTokenByHash(nftMintHash);
for (NftMintDto dto : list) {
    // 铸造没完成为null
    System.out.println(dto.getHash());
    // 铸造没完成为null
    System.out.println(dto.getTokenId());
}
```
转移nft所有者
```java
// 转移nft
StraitChainSendRawTxParam param = new StraitChainSendRawTxParam();
param.setFrom(from);
String to = 用户的通行证地址;

param.setTo(to);
param.setContractAddress(contractAddress);
// 上面的tokenId
param.setTokenId(1);
// nft拥有者的私钥
String privateKey = nft拥有者私钥;
param.setPrivateKey(privateKey);
String transferHx = client.transferNftUser(param);
System.out.println(transferHx);
```
查询转移结果
```java
TransactionInfoDto dto = client.scsGetTransactionReceipt(transferHx);
if (dto.getStatus().equals("0x1")) {
    System.out.println("转移成功");
}
```

## 错误码 
com.shangchain.straitchain.constants.StraitChainErrorConstant

| 错误码   | 描述       |
|-------|----------|
| 10002 | 参数异常     |
| 10006 | 业务操作异常   |
| 10007 | 铸造等待中    |
| 20001 | 系统级别异常   |
| 20006 | 签名验签异常   |
| 30003 | 调用合约返回失败 |
| 30004 | 接口开放限制   |
