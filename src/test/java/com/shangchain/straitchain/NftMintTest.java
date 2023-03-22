package com.shangchain.straitchain;

import cn.hutool.core.thread.ThreadUtil;
import com.shangchain.straitchain.exception.StraitChainException;
import com.shangchain.straitchain.params.StraitNftMintParam;

/**
 * 2022/4/26
 * nft铸造，分六个步骤，每一步要分开
 *
 * @author shangchain Junisyoan
 */
public class NftMintTest {

    public static void main(String[] args) {

        StraitChainClient client = new StraitChainClient("Ist8KOqm","8d065964ab77cfa1917bdafa6c27e5dd605590ed");
        String from       = "0xc4244F49522C32E6181b759f35BE5EfA2f19d7f9";
        String to         = "通行证地址（钱包地址）";
        String privateKey = "09f51b8fd9e4124e1b80e4ffd475a5a542a438177fed9d4f10d626958e16b1da";
        client.setUrl("http://192.168.80.15/strait-chain-client-test/api/develop/straits/action");

        // deployContract: deployContract
        // 第一步
        int nftMintCount = 100000;
        // 部署合约
        String contractTxHash = null;
        for (int i = 0; i < 5; i++) {
            contractTxHash = client.scsDeployContract(from,nftMintCount);
            if (contractTxHash != null) {
                break;
            }else if (i == 4){
                throw new StraitChainException("合约部署失败，联系熵链技术人员");
            }
        }
        // 0xd1eb8d2c6d24da81a9feecf0c87cc4ca76b4109e045359cf188a21e0f6636018
        System.out.println(contractTxHash);

        // 第二部
        // 根据合约哈希查询合约地址，合约部署大概5 - 10秒，这里用睡眠
        ThreadUtil.sleep(10000);
        String contractAddress;
        try {
            contractAddress = client.scsContractAddressByHash(contractTxHash);
        } catch (StraitChainException | NullPointerException e) {
            // goto deployContract
            throw new StraitChainException("合约部署失败，返回第一步重新开始");
        }
        // 0xf62ac9fd73f15194cfa2cba93004c2749f2bd24c
        System.out.println(contractAddress);

        // 第三步
        // 开始铸造，100个1分钟左右，一万个30分钟左右
        StraitNftMintParam nftMintParam = new StraitNftMintParam();
        nftMintParam.setNftName("这是nft名字");
        nftMintParam.setCid("这是ipfs上传后的cid，没有可不要，填空字符串");
        nftMintParam.setNftUri("https://cdnstrait.shang-chain.com/default/test.json");
        nftMintParam.setCopyRight("版权方");
        nftMintParam.setIssuer("发行方");
        nftMintParam.setOperator("运营方");
        nftMintParam.setRemark("这是一个备注");
        nftMintParam.setCount(nftMintCount);
        nftMintParam.setOwner(from);
        nftMintParam.setContractAddress(contractAddress);
        nftMintParam.setCollectSn("业务需求，全平台唯一。如：公司缩写+藏品名称+uuid，不要写中文");
        nftMintParam.setServiceId("");
        String nftMintHash = client.scsNftMint(nftMintParam);
        // 5e43dee3bad2494c9710f80880cba3d6
        System.out.println(nftMintHash);

        // 预估一下至少nftMintCount * 6L秒，遇见排队会更久，大数量需要提前一天铸造，或者联系我们监控。
        ThreadUtil.sleep(nftMintCount * 6L);

//        // 第四步
//        // 查询铸造状态，铸造中查询会返回null，100个一分钟左右，一万个半小时左右，可以预估一下时间再查询，后面间隔10秒查一下
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

//        // 第五步
//        // 转移nft，拥有者转移给别人，私钥和地址要对应上才能转移成功。每笔转移之间间隔5秒左右，每笔交易链上会收0.03-0.08的手续费。
//        // 显示没钱那就是私钥不对
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

        // 查一下拥有者
        String owner = client.nftOwnerOfExcludeZero(
                from,
                contractAddress,
                512);
        System.out.println(owner);

    }


}
