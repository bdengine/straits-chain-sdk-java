package com.shangchain.straitchain.params;

import lombok.Data;

/**
 * 2022/4/26
 * 存证参数
 *
 * @author shangchain Junisyoan
 */
@Data
public class StraitChainExistingEvidenceParam {
    String serviceId;
    String cid;
    String content;
    String contractSignHex;
}
