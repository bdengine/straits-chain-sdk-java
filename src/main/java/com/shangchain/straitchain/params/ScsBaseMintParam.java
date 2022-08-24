package com.shangchain.straitchain.params;

import lombok.Getter;
import lombok.Setter;

/**
 * 2022/8/10
 * 基础铸造参数
 *
 * @author shangchain Junisyoan
 */

@Setter
@Getter
public class ScsBaseMintParam extends ScsIssueParam{
    /**
     * 数字藏品的文件属性链接
     * nftURI： 为json文件或txt文件的在线地址（推荐json,铸造后不可变）
     * 内容为：(仅支持https请求的图片地址)
     * {"attributes":[],"image":"https://xxxx.com/xxx.png","name":"xx"}
     */
    String nftUri;
    /**
     * 铸造数量
     */
    Integer count;
    /**
     * 数字藏品拥有者
     */
    String owner;
    /**
     * 备注
     */
    String remark;
}
