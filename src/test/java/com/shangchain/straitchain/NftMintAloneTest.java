package com.shangchain.straitchain;

/**
 * 2022/8/9
 * 单次铸造
 *
 * @author shangchain Junisyoan
 */

public class NftMintAloneTest {

    public static void main(String[] args) {
        StraitChainClient client = new StraitChainClient();
        client.setAppId("appId");
        client.setAppKey("appKey");
        client.setUrl("https://backend.straitchain.com");
        String from       = "通行证地址";
        String to         = "通行证地址";
        String privateKey = "通行证私钥";


        // 第一步
//        int nftMintCount = 10;
//        // 部署合约
//        String contractTxHash = client.scsDeployContract(from,nftMintCount);
//        // 0xd5caf896532ee64a781047f7a5c2d1e8c22cf7f0cc48843f3a9ccf24ef0f8e5d
//        System.out.println(contractTxHash);

        // 第二部
//        // 根据合约哈希查询合约地址
//        String contractAddress = client.scsContractAddressByHash(contractTxHash);
//        // 0x8aa0860527eb64e0dfd4f1a16002fe1f84f6fd7a
//        System.out.println(contractAddress);

        // 第三步，不能选择铸造几个，只能铸造一个
//        StraitNftMintParam param = new StraitNftMintParam();
//        param.setNftName("nft名称");
//        param.setCid("ipfs上传后返回的标识，没有可以不要");
//        param.setNftUri("");
//        param.setCopyRight("");
//        param.setIssuer("");
//        param.setOperator("");
//        param.setRemark("");
//        param.setCount(1);
//        param.setOwner(from);
//        param.setContractAddress(contractAddress);
//        param.setCollectSn("唯一标识，如：公司缩写+uuid");
//        param.setServiceId("服务id，没有填空串");
//        NftMintDto nftMintDto = client.scsNftMintAlone(param);
//        //1
//        System.out.println(nftMintDto.getTokenId());
//        //0x24b453afe9775cd89478a13331156ab6302b7a5669c8ee10dda763577f4adb0f
//        System.out.println(nftMintDto.getHash());
    }
}
