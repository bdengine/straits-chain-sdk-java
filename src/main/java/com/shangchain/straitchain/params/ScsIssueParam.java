package com.shangchain.straitchain.params;

import lombok.Getter;
import lombok.Setter;

/**
 * 2022/8/8
 * 发行参数
 *
 * @author shangchain Junisyoan
 */

@Setter
@Getter
public class ScsIssueParam {
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
}
