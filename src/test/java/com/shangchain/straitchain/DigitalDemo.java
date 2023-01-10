package com.shangchain.straitchain;

import com.shangchain.straitchain.dto.DigitalMintDto;
import com.shangchain.straitchain.dto.TransactionInfoDto;
import com.shangchain.straitchain.params.DigitalCollectionMintParam;
import com.shangchain.straitchain.params.DigitalCollectionTransactionParam;
import com.shangchain.straitchain.params.StraitChainContractDcEvidenceSignHexParam;
import com.shangchain.straitchain.params.StraitChainExistingEvidenceParam;
import com.shangchain.straitchain.utils.StraitChainUtil;

import java.util.List;

/**
 * 2022/10/31
 * 数字藏品
 * @author shangchain Junisyoan
 */
public class DigitalDemo {
    public static void main(String[] args) {
        StraitChainClient client = new StraitChainClient();
        client.setAppId("Ist8KOqm");
        client.setAppKey("8d065964ab77cfa1917bdafa6c27e5dd605590ed");
//        client.setUrl("https://backend.straitchain.com/webclient");
        client.setUrl("http://192.168.80.15/strait-chain-client-test");
        String from       = "0xc4244f49522c32e6181b759f35be5efa2f19d7f9";
        String to         = "0x171Fa07F54E730364ad843153e896427110F6ea2";
        String privateKey = "09f51b8fd9e4124e1b80e4ffd475a5a542a438177fed9d4f10d626958e16b1da";

        // 第一步铸造
//        DigitalCollectionMintParam param = new DigitalCollectionMintParam();
//        param.setName("数字藏品");
//        param.setNumber("shangchain-test-digital-1");
//        param.setNftUri("https://cdnstrait.shang-chain.com/default/test.json");
//        param.setCopyRight("熵链科技");
//        param.setIssuer("熵链科技");
//        param.setOperator("熵链科技");
//        param.setRemark("这是备注");
//        param.setCount(10);
//        param.setOwner(from);
//
//        String txHash = client.scsDigitalCollectionMint(param);
//        // 94dbbe3092904bab9ba39d60594751dc
//        System.out.println(txHash);

//        // 第二部查询，数字藏品的铸造哈希相同，藏品编号(tokenId)不同
//        List<DigitalMintDto> digitalMintDtos = client.scsDigitalCollectionList("94dbbe3092904bab9ba39d60594751dc");
//        for (DigitalMintDto dto : digitalMintDtos) {
//            // 1..10  0xc995fa8128bcd06220194a202ff8447d41927f4d614369e8fa56b06f530d5e79
//            System.out.println(dto.getTokenId() + "\t" + dto.getHash());
//        }

//        // 第三步，获取方法签名
//        StraitChainContractDcEvidenceSignHexParam contractDcEvidenceSignHexParam = new StraitChainContractDcEvidenceSignHexParam();
//        contractDcEvidenceSignHexParam.setFrom(from);
//        contractDcEvidenceSignHexParam.setPrivateKey(privateKey);
//        contractDcEvidenceSignHexParam.setCid("");
//        contractDcEvidenceSignHexParam.setContent("存证");
//        String signHex = client.dcEvidenceSignHex(contractDcEvidenceSignHexParam);
//        // 0xf9010c8243bb8583156a3e07830249f09465ba82505b6d7ddaa7ac3488f70a32dd59a50c1380b8a4bd90cb330000000000000000000000000000000000000000000000000000000000000040000000000000000000000000000000000000000000000000000000000000006000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000006e5ad98e8af8100000000000000000000000000000000000000000000000000001ba0b870eeda009f3dd89440d1f97508b827c26b6a135f04418db6a06a0d03dec5a3a02cab696e317d64f5d8b23db54a547c0d40b0ec4cb2048680ef811db98aeb8716
//        System.out.println(signHex);


//        // 版权上练
//        StraitChainExistingEvidenceParam existingEvidenceParam = new StraitChainExistingEvidenceParam();
//        existingEvidenceParam.setServiceId("没有可以为空，必填");
//        existingEvidenceParam.setCid("ipfs上传吼返回的标识");
//        existingEvidenceParam.setContent("存证内容");
//        existingEvidenceParam.setContractSignHex(signHex);
//        String txHash = client.scsExistingEvidence(existingEvidenceParam);
//        // 0x1b60a76b27717353c89aa4ed7f84360f89dc3d807f4e43b42ad7997a5b57722d
//        System.out.println(txHash);

//        // 移交版权
//        DigitalCollectionTransactionParam param = new DigitalCollectionTransactionParam();
//        param.setFrom(from);
//        param.setTo(to);
//        param.setTokenId(1);
//        // 第一步骤的铸造返回哈希
//        param.setNumber("94dbbe3092904bab9ba39d60594751dc");
//        String txHash = client.scsDigitalCollectionTransaction(param);
//        // 0x7bbcb89c89a9319411e5ec333b046c15db6d6ad3fb758524c82e93defe7f139b
//        System.out.println(txHash);

        // 查看交易结果，也可以拿着去浏览器查
        TransactionInfoDto receipt = client.scsGetTransactionReceipt("0x7bbcb89c89a9319411e5ec333b046c15db6d6ad3fb758524c82e93defe7f139b");
        System.out.println(receipt.isStatusOK());
    }
}
