package com.shangchain.straitchain.params;

import lombok.Data;

/**
 * <p>根域名注册 </p>
 *
 * @author kexiaodong
 * @since 2023/4/7 15:36
 */
@Data
public class ScsRealmNameRegisterParam {
    /**
     * 根域名
     */
    String realmName;

    /**
     * 域名所属者地址
     */
    String ownerAddress;
}
