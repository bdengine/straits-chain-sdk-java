package com.shangchain.straitchain.params;

import lombok.Data;

/**
 * 2023/3/2
 * 盘古参数
 *
 * @author shangchain Junisyoan
 */

@Data
public class PanGuEvidenceParam {
    /**
     * 业务编码主键
     */
    String dataInnerPrimaryKey;
    /**
     * 业务方内部的主键
     */
    String dataInnerPrimaryIssueId;
    /**
     * 业务方可提供过滤的域
     */
    String dataInnerSecondIssueId;
    /**
     * 业务数据描述
     */
    String dataDesc;
    /**
     * 数据原文
     */
    String dataValue;
    /**
     * 是否哈希，1哈希，0不哈希，必填
     */
    Integer dataNeedHash;
}
