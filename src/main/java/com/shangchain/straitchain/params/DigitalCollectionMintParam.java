package com.shangchain.straitchain.params;

import lombok.Getter;
import lombok.Setter;

/**
 * 2022/8/8
 * 数字藏品铸造参数
 *
 * @author shangchain Junisyoan
 */

@Getter
@Setter
public class DigitalCollectionMintParam extends ScsBaseMintParam{
    /**
     * 数字藏品名称
     */
    String name;
    /**
     * 系统唯一编码，如：公司英文名 + uuid
     */
    String number;
}
