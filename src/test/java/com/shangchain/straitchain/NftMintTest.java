package com.shangchain.straitchain;

/**
 * 2022/4/26
 * nft铸造，分六个步骤，每一步要分开
 *
 * @author shangchain Junisyoan
 */
public class NftMintTest {

    public static void main(String[] args) {

        StraitChainClient client = new StraitChainClient("appId","appKey");
        String from       = "通行证地址（钱包地址）";
        String to         = "通行证地址（钱包地址）";
        String privateKey = "私钥";

        // 第一步
//        int nftMintCount = 10;
//        // 部署合约
//        String contractTxHash = client.scsDeployContract(from,nftMintCount);
//        // 0x019974a1cc5d9b9ad04115753239f25472cd750789ac7bc18c778681e710bc2f
//        System.out.println(contractTxHash);

        // 第二部
//        // 根据合约哈希查询合约地址
//        String contractAddress = client.scsContractAddressByHash(contractTxHash);
//        // 0x3357e23df2e1b01a18c455ec6352b95dd9d0a0eb
//        System.out.println(contractAddress);

        // 第三步
//        // 开始铸造
//        StraitNftMintParam nftMintParam = new StraitNftMintParam();
//        nftMintParam.setNftName("这是nft名字");
//        nftMintParam.setCid("这是ipfs上传后的cid，没有可不要");
//        nftMintParam.setNftUri("nft铸造的图片信息，示例：http://nps.shang-chain.com:30023/profile/tmp/ahjdfhf.json");
//        nftMintParam.setCopyRight("版权方");
//        nftMintParam.setIssuer("发行方");
//        nftMintParam.setOperator("运营方");
//        nftMintParam.setRemark("这是一个备注");
//        nftMintParam.setCount(nftMintCount);
//        nftMintParam.setOwner(from);
//        nftMintParam.setContractAddress(contractAddress);
//        // 业务需求，全平台唯一。如：公司缩写+uuid
//        nftMintParam.setCollectSn("strait_chain_test_2");
//        // 对应的服务ID，没有就空
//        nftMintParam.setServiceId("");
//        String nftMintHash = client.scsNftMint(nftMintParam);
//        // 5e43dee3bad2494c9710f80880cba3d6
//        System.out.println(nftMintHash);

        // 第四步
//        // 查询铸造状态
//        List<NftMintDto> list = client.scsGetTokenByHash(nftMintHash);
//        for (NftMintDto dto : list) {
//            // 铸造没完成为null
//            System.out.println(dto.getHash());
//            // 铸造没完成为null
//            System.out.println(dto.getTokenId());
//
//            //0xffbabd6a02c9c7c418098e8e310d38267abe1d5f553c52121b35635a075144d1
//            //1
//            //0x38ddf7bb7851cbdbfba489db491f115c4314b582e7430a0d8728792b24e4a43a
//            //2
//            //0xd9d8f4233b352e5d416a3878683b0fe1549c08cf434b5196f561cdd305256424
//            //3
//            //0x9ba6c50f07e7d2c347f16f997337640b90a26cc9ee042839e052b6e90be8c02e
//            //4
//            //0xdf46e2dd8856896bb82bb1c32ccf83b45f3410e524c15e30b064ab2f5affdf31
//            //5
//            //0xb76d0c1955eb0afe964f5995a74596647c854153750b2b2ecd2d2f1c4fef400d
//            //6
//            //0x330ef17319811b5259eef43d52c513b36b0cf9773a2fb73c41a5ae5724c2630c
//            //7
//            //0x5c48f82ac1cc0265a6d27b004f929f7197e0230a430c50cbbe6a26f97aaf5e65
//            //8
//            //0x336c44c6cfb2bbe009f140ff9b349de455bbb2aa48ada1944c85fea3c85191a6
//            //9
//            //0x950d2b03f39eb313799a376773c9641b9338bdbdf2f917bd048147a62243d48e
//            //10
//        }

        // 第五步
//        // 转移nft
//        StraitChainSendRawTxParam param = new StraitChainSendRawTxParam();
//        param.setFrom(from);
//        param.setTo(to);
//        param.setContractAddress(contractAddress);
//        // 上面的tokenId
//        param.setTokenId(1);
//        // from 的私钥
//        param.setPrivateKey(privateKey);
//        String transferHx = client.transferNftUser(param);
//        System.out.println(transferHx);

        // 第六步
//        TransactionInfoDto receipt = client.scsGetTransactionReceipt("0x7090f99b5c8b83ba6081274547dce6f2f0e330e1cf3d0e292d85b397b3072a50");
//        if (receipt.isStatusOK()) {
//            System.out.println("转移成功");
//        }else {
//            System.out.println("失败");
//        }

        String owner = client.nftOwnerOfExcludeZero(
                "0x228a77cec9424b709ffb2705cae6898ec8a4e888",
                "0x228a77cec9424b709ffb2705cae6898ec8a4e888",
                512);
        System.out.println(owner);

    }


}
