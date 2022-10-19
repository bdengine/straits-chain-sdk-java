package com.shangchain.straitchain;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.shangchain.straitchain.dto.NftMintDto;
import com.shangchain.straitchain.dto.TransactionInfoDto;
import com.shangchain.straitchain.enums.ContractTypeEnum;
import com.shangchain.straitchain.params.StraitNft4907SetUserParam;
import com.shangchain.straitchain.params.StraitNftMintParam;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 2022/10/19
 *
 * @author shangchain Junisyoan
 */
public class Nft4907Test {
    public static void main(String[] args) {

        StraitChainClient client = new StraitChainClient();
        client.setAppId("appId");
        client.setAppKey("appKey");
        client.setUrl("https://backend.straitchain.com/webclient");
        String from       = "通行证地址（钱包地址）";
        String to         = "通行证地址（钱包地址）";
        String privateKey = "私钥";

        // 第一步
//        int nftMintCount = 5;
//        // 部署合约
//        String contractTxHash = client.scsDeployContract(from,nftMintCount, ContractTypeEnum.CONTRACT_4907);
//        // 0x530adf59ec21a5f4c3e145b533661a1751c87feaeb4175173796ac9a61f2db71
//        System.out.println(contractTxHash);

        // 第二部
//        // 根据合约哈希查询合约地址
//        String contractAddress = client.scsContractAddressByHash(contractTxHash);
//        // 0xa83fd60fda796cab760d343a331fb1d6c91facb9
//        System.out.println(contractAddress);

        // 第三步
//        // 开始铸造
//        StraitNftMintParam nftMintParam = new StraitNftMintParam();
//        nftMintParam.setNftName("4907合约sdk测试");
//        nftMintParam.setCid("这是ipfs上传后的cid，没有可不要");
//        nftMintParam.setNftUri("https://cdnstrait.shang-chain.com/default/test.json");
//        nftMintParam.setCopyRight("版权方");
//        nftMintParam.setIssuer("发行方");
//        nftMintParam.setOperator("运营方");
//        nftMintParam.setRemark("这是一个备注");
//        nftMintParam.setCount(nftMintCount);
//        nftMintParam.setOwner(from);
//        nftMintParam.setContractAddress(contractAddress);
//        // 业务需求，全平台唯一。如：公司缩写+uuid
//        nftMintParam.setCollectSn("strait_chain_test_1_4907");
//        // 对应的服务ID，没有就空
//        nftMintParam.setServiceId("");
//        String nftMintHash = client.scsNftMint(nftMintParam);
//        // af03a90e43b446719be6b38f6587702d
//        System.out.println(nftMintHash);

        // 第四步
//        // 查询铸造状态
//        List<NftMintDto> list = client.scsGetTokenByHash(nftMintHash);
//        for (NftMintDto dto : list) {
//            // 铸造没完成为null
//            System.out.println(dto.getHash());
//            // 铸造没完成为null
//            System.out.println(dto.getTokenId());
//            //0xe656ad85062a48d2bbc9261506a47a49a42d2bee21f1dd78badfb3eb96265bc9
//            //1
//            //0x7942a4020c3f30e876abde7a295027e087b97a0574a73b014487491e5cb79786
//            //2
//            //0x64ca8fcfc643fc6e0a517f71e40edf19a665252c9ad99cb5885d55682e3c44b0
//            //3
//            //0xa268d6f8708638166d427791a8d5a7487c17b7581d6be710d3c3fd7aae8b2665
//            //4
//            //0x581d7deb8e019ff103d344360997c18ebaffd9c9195cbf664e610a10c43d8add
//            //5
//            //Disconnected from the target VM, address: '127.0.0.1:58734', transport: 'socket'
//            //
//            //Process finished with exit code 0
//        }

        // 第五步 租赁nft
//        // 租赁一天
//        Calendar calendar = new GregorianCalendar();
//        calendar.setTime(new Date());
//        calendar.add(Calendar.DATE, 1);
//        long l = calendar.getTimeInMillis() / 1000;
//        System.out.println(l);
//        StraitNft4907SetUserParam param = new StraitNft4907SetUserParam();
//        param.setFrom(from);
//        param.setExpires(l);
//        param.setTo(to);
//        param.setPrivateKey(privateKey);
//        param.setTokenId(tokenId);
//        param.setContractAddress(contractAddress);
//        String transferHx = client.nft4907SetUser(param);
//        // 0x132759ca4a89bde26abfd08a66a6e402d1a7fc51d3820ff5c06fe5f4ee5669ab
//        System.out.println(transferHx);

        // 第六步 查询交易是否成功 0x132759ca4a89bde26abfd08a66a6e402d1a7fc51d3820ff5c06fe5f4ee5669ab
//        TransactionInfoDto receipt = client.scsGetTransactionReceipt(txHash);
//        System.out.println(receipt.isStatusOK());

        // 第七步 查询租赁，nft所属人还是原来的人，只是租出去
//        String owner = client.nft4907UserOfExcludeZero(from,contractAddress,tokenId);
//        System.out.println(owner);

        // 第八步 查询租赁时间
//        Long timestamp = client.nft4907UserExpires(from, contractAddress, tokenId);
//        System.out.println(DateUtil.format(new Date(timestamp * 1000), DatePattern.NORM_DATETIME_PATTERN));
    }
}
