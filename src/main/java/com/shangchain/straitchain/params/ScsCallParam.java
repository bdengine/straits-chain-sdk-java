package com.shangchain.straitchain.params;

import lombok.Data;

/**
 * 2022/4/27
 * call参数
 *
 * @author shangchain Junisyoan
 */
@Data
public class ScsCallParam {
    String from;
    String to;
    String gas;
    /**
     * 调用scsCall的时候会自动填
     */
    String gasPrice;
    String value;
    String data;
}
