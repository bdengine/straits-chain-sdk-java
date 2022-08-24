package com.shangchain.straitchain.params;

import lombok.Getter;
import lombok.Setter;

/**
 * 2022/8/22
 * 转移drop参数
 *
 * @author shangchain Junisyoan
 */

@Setter
@Getter
public class ScsTransferDropParam {
    /**
     * 转送地址
     */
    String fromAddress;
    /**
     * 接收地址
     */
    String toAddress;
    /**
     * 转送地址私钥
     */
    String privateKey;
    /**
     * drop值，1drop = 1人名币
     */
    Double drop;
}
