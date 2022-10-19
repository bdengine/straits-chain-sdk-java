package com.shangchain.straitchain.params;

import lombok.Getter;
import lombok.Setter;

/**
 * 2022/10/19
 * 4907合约租赁对象信息参数
 *
 * @author shangchain Junisyoan
 */
@Setter
@Getter
public class StraitNft4907SetUserParam {
    /**
     * 发起人
     */
    String from;
    /**
     * 合约地址
     */
    String contractAddress;
    /**
     * 藏品编号
     */
    Integer tokenId;
    /**
     * 租赁人地址
     */
    String to;
    /**
     * 租赁到期时间，秒时间戳
     */
    Long expires;

    /**
     * 私钥，from的私钥
     */
    String privateKey;
}
