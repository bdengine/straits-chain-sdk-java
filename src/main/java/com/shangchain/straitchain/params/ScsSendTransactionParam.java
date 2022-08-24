package com.shangchain.straitchain.params;

import lombok.Data;

/**
 * 2022/4/27
 * 参数
 *
 * @author shangchain Junisyoan
 */

@Data
public class ScsSendTransactionParam {
    /**
     * 通行证地址
     */
    String from;
    /**
     * 通行证地址
     */
    String to;
    /**
     * gas费，可以填大一些，0x开头的16进制
     */
    String gas;
    /**
     * 交易的金额
     */
    String value;
    /**
     * 合约方法签名
     */
    String contractSignData;
}
