package com.shangchain.straitchain.params;

import lombok.Getter;
import lombok.Setter;

/**
 * 2022/8/9
 * 数字藏品交易参数
 *
 * @author shangchain Junisyoan
 */

@Setter
@Getter
public class DigitalCollectionTransactionParam {
    /**
     * 拥有者通行证地址
     */
    String from;
    /**
     * 接收者通行证地址
     */
    String to;
    /**
     * 数字藏品顺序标识
     */
    Integer tokenId;
    /**
     * 数字藏品铸造唯一标识
     */
    String number;
}
