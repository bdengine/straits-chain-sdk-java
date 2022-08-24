package com.shangchain.straitchain.constants;

/**
 * 2022/6/27
 * 错误码常量
 *
 * @author shangchain Junisyoan
 */
public class StraitChainErrorConstant {
    private StraitChainErrorConstant() {
    }

    /**
     * 成功标志
     */
    public static final int RET_CODE_OK = 0;
    /**
     * 参数错误
     */
    public static final int RET_CODE_PAR_ERROR = 10002;
    /**
     * 业务异常
     */
    public static final int RET_CODE_LOCK_FAIL = 10006;
    /**
     * 等待铸造
     */
    public static final int RET_CODE_NFT_WAIT = 10007;
    /**
     * 系统错误
     */
    public static final int RET_CODE_SYS = 20001;
    /**
     * 签名错误
     */
    public static final int RET_CODE_SIGN_ERR = 20006;
    /**
     * 调用合约返回失败
     */
    public static final int RET_CODE_CONTRACT_RES_ERR = 30003;
    /**
     * 链上接口开放
     */
    public static final int RET_CODE_CONTRACT_RES_ = 30004;

}
