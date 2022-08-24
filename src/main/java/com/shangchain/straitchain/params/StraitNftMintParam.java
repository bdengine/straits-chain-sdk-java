package com.shangchain.straitchain.params;

import lombok.Data;

/**
 * 2022/4/26
 * 铸造合约参数
 *
 * @author shangchain Junisyoan
 */

@Data
public class StraitNftMintParam {
    /**
     * 藏品名
     */
    String nftName;

    /**
     * 文件存储ipfs服务标识cid
     */
    String cid;

    /**
     * token的json串，参考接口文档
     */
    String nftUri;

    /**
     * 版权方
     */
    String copyRight;

    /**
     * 发行方
     */
    String issuer;

    /**
     * 运营方
     */
    String operator;

    /**
     * 备注
     */
    String remark;

    /**
     * 铸造个数，必须和合约内一致
     */
    Integer count;

    /**
     * 通行证地址
     */
    String owner;

    /**
     * 部署的合约地址
     */
    String contractAddress;

    /**
     * 藏品唯一标识。根据业务设定，海峡链平台唯一。uuid或其他唯一标识，如：straitchain_xiamen_nft_qni34ytb3yowtv7y3vuayweroq
     */
    String collectSn;

    /**
     * 海峡链平台上的服务id
     */
    String serviceId;
}
