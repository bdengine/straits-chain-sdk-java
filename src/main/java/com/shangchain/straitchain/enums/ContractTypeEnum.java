package com.shangchain.straitchain.enums;

/**
 * 2022/10/19
 * version 2.2.0
 * 合约类型
 *
 * @author shangchain Junisyoan
 */
public enum ContractTypeEnum{
    /**
     * 默认合约
     */
    DEFAULT(0),

    /**
     * 4907合约
     */
    CONTRACT_4907(1),


    ;
    private final int value;

    ContractTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
