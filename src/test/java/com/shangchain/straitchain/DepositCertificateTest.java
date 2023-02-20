package com.shangchain.straitchain;

import com.shangchain.straitchain.params.StraitChainExistingEvidenceParam;

/**
 * 2022/4/26
 * 存证示例
 *
 * @author shangchain Junisyoan
 */
public class DepositCertificateTest {
    public static void main(String[] args) {
        StraitChainClient client = new StraitChainClient();
        client.setAppId("appId");
        client.setAppKey("appKey");
        client.setUrl("https://backend.straitchain.com");
        String from       = "通行证地址";
        String to         = "通行证地址";
        String privateKey = "通行证私钥";

//        // 上传文件到ipfs
//        String cid = client.ipfsUpload(from, new File("C:/Users/shangchain/Desktop/1.avi"));
//        // QmVMtZqABQeiPSrS2vA9zaJnM689YqbArWz9aijCeZ7NFF
//        System.out.println(cid);
        String cid = "QmVMtZqABQeiPSrS2vA9zaJnM689YqbArWz9aijCeZ7NFF";

//        // 获取方法编码
//        StraitChainContractDcEvidenceSignHexParam contractDcEvidenceSignHexParam = new StraitChainContractDcEvidenceSignHexParam();
//        contractDcEvidenceSignHexParam.setFrom(from);
//        contractDcEvidenceSignHexParam.setPrivateKey(privateKey);
//        contractDcEvidenceSignHexParam.setCid(cid);
//        contractDcEvidenceSignHexParam.setContent("存证");
//        String signData = client.dcEvidenceSignHex(contractDcEvidenceSignHexParam);
//        System.out.println(signData);
        String signData = "0xf9014b81cd8583156a3e078301adb094ce0fdafbc19abd193cce4d46ae254ae90403b2dc80b8e4bd90cb33000000000000000000000000000000000000000000000000000000000000004000000000000000000000000000000000000000000000000000000000000000a0000000000000000000000000000000000000000000000000000000000000002e516d564d745a71414251656950537253327641397a614a6e4d3638395971624172577a3961696a43655a374e46460000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000006e5ad98e8af8100000000000000000000000000000000000000000000000000001ba0c3b5a6ad206c1431e51dd601dfff0e459794a7c4cec5686b0b545d15844f2587a02100068b7267978fedd60eb6dc8fd9b18fd58828814893c16ba2904f5ce4ae75";


        StraitChainExistingEvidenceParam existingEvidenceParam = new StraitChainExistingEvidenceParam();
        existingEvidenceParam.setServiceId("没有可以为空，必填");
        existingEvidenceParam.setCid(cid);
        existingEvidenceParam.setContent("存证内容");
        existingEvidenceParam.setContractSignHex(signData);

        // 存证
        String txHash = client.scsExistingEvidence(existingEvidenceParam);
        // 0x6c22d02457a9b59fec37bd00a7160de82bea228031581c47874ac7c6bbb2d019
        System.out.println(txHash);
    }
}
