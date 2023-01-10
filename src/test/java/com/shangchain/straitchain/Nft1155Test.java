package com.shangchain.straitchain;

import com.shangchain.straitchain.dto.Nft1155MintDetailDto;
import com.shangchain.straitchain.dto.Nft1155MintDto;
import com.shangchain.straitchain.dto.NftMintDto;
import com.shangchain.straitchain.dto.TransactionInfoDto;
import com.shangchain.straitchain.enums.ContractTypeEnum;
import com.shangchain.straitchain.params.*;
import org.web3j.abi.DefaultFunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 2023/1/9
 * 1155合约
 * @author shangchain Junisyoan
 */
public class Nft1155Test {
    public static void main(String[] args) {
        StraitChainClient client = new StraitChainClient();
//        client.setAppId("appId");
//        client.setAppKey("appKey");
//        client.setUrl("https://backend.straitchain.com/webclient");
        client.setTimeout(30000);
        client.setUrl("http://192.168.80.15/strait-chain-client-test");
        client.setAppId("Ist8KOqm");
        client.setAppKey("8d065964ab77cfa1917bdafa6c27e5dd605590ed");
        String from       = "0xc4244F49522C32E6181b759f35BE5EfA2f19d7f9";
        String to         = "0x171Fa07F54E730364ad843153e896427110F6ea2";
        String privateKey = "09f51b8fd9e4124e1b80e4ffd475a5a542a438177fed9d4f10d626958e16b1da";

        // 第一步
//        int nftMintCount = 10;
//        // 部署合约
//        String contractTxHash = client.scsDeployContract(from,nftMintCount, ContractTypeEnum.CONTRACT_1155);
//        // 0xb2554bd50f6b2719447bdface29e8182bcbc80e0b31ac792884b46d0024d515a
//        System.out.println(contractTxHash);

        // 第二部
//        // 根据合约哈希查询合约地址
//        String contractAddress = client.scsContractAddressByHash(contractTxHash);
//        // 0x6a5c95b106e883d4c6dbb0df8f32f3a10a53a962
//        System.out.println(contractAddress);

        // 第三步
//        // 开始铸造
//        StraitNftMintParam nftMintParam = new StraitNftMintParam();
//        nftMintParam.setNftName("这是nft名字");
////        nftMintParam.setCid("这是ipfs上传后的cid，没有可不要");
//        nftMintParam.setCid("");
////        nftMintParam.setNftUri("nft铸造的图片信息，示例：http://nps.shang-chain.com:30023/profile/tmp/ahjdfhf.json");
//        nftMintParam.setNftUri("https://cdnstrait.shang-chain.com/default/test.json");
//        nftMintParam.setCopyRight("版权方");
//        nftMintParam.setIssuer("发行方");
//        nftMintParam.setOperator("运营方");
//        nftMintParam.setRemark("这是一个备注");
//        nftMintParam.setCount(10);
//        nftMintParam.setOwner(from);
//        nftMintParam.setContractAddress("0x6a5c95b106e883d4c6dbb0df8f32f3a10a53a962");
//        // 业务需求，全平台唯一。如：公司缩写+uuid
//        nftMintParam.setCollectSn("strait_chain_test_3745732");
//        // 对应的服务ID，没有就空
//        nftMintParam.setServiceId("");
//        Nft1155MintDto dto = client.scs1155NftMint(nftMintParam);
        // 这里的返回值意思是，这批tokenId的nft藏品有count个，也就是你可以转移count次。如：这批编号为100的nft藏品，铸造了100个，可以转移100次
//        // {"tokenId":1,"count":10,"hash":"0xcfbb5775ac49d9e53ce3cf4022ff303dc00e817df3e9d94f0c20ad261f42270f"}
//        System.out.println(dto);

        // 第四步
//        // 转移nft
//        Nft1155SafeTransferFromParam param = new Nft1155SafeTransferFromParam();
//        param.setFrom(from);
//        param.setTo(to);
//        param.setContractAddress("0x6a5c95b106e883d4c6dbb0df8f32f3a10a53a962");
//        // 上面的tokenId
//        param.setTokenId(1L);
//        param.setAmount(9L);
//        param.setData("");
//        // from 的私钥
//        param.setPrivateKey(privateKey);
//        String transferHx = client.nft1155SafeTransferFrom(param);
//        // 0x55f65d53732accd23668c0f2622fa96df1540465801e0bcfcae78343b0a2b87d
//        System.out.println(transferHx);

        // 第五步
//        TransactionInfoDto receipt = client.scsGetTransactionReceipt("0x55f65d53732accd23668c0f2622fa96df1540465801e0bcfcae78343b0a2b87d");
//        if (receipt.isStatusOK()) {
//            System.out.println("转移成功");
//        }else {
//            System.out.println("失败");
//        }

        // 第六步
//        Nft1155BalanceOfParam param = new Nft1155BalanceOfParam();
//        param.setAccount(to);
//        param.setTokenId(1L);
//        param.setContractAddress("0x6a5c95b106e883d4c6dbb0df8f32f3a10a53a962");
//        Long s = client.nft1155BalanceOf(param);
//        System.out.println(s);

        // 批量验证
//        Nft1155BalanceOfBatchParam param = new Nft1155BalanceOfBatchParam();
//        param.setFrom(from);
//        param.setAccounts(Collections.singletonList(to));
//        param.setTokenIds(Collections.singletonList(1L));
//        param.setContractAddress("0x6a5c95b106e883d4c6dbb0df8f32f3a10a53a962");
//        List<Long> longs = client.nft1155BalanceOfBatch(param);
//        for (Long s : longs) {
//            System.out.println(s);
//        }

        // 铸造信息
//        Nft1155VerificationNftParam param = new Nft1155VerificationNftParam();
//        param.setTokenId(1L);
//        param.setFrom(from);
//        param.setContractAddress("0x6a5c95b106e883d4c6dbb0df8f32f3a10a53a962");
//        Nft1155MintDetailDto dto = client.nft1155VerificationNft(param);
//        System.out.println(dto.getTokenId());
//        System.out.println(dto.getMintOwner());
//        System.out.println(dto.getAmount());
//        for (String mintDatum : dto.getMintData()) {
//            System.out.println(mintDatum);
//        }
    }
}
