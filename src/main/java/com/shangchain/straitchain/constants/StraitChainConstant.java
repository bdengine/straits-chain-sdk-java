package com.shangchain.straitchain.constants;

/**
 * @author shangchain
 */
public class StraitChainConstant {
    /**
     * 返回海峡链版本
     */
    public static final String SCS_PROTOCOL_VERSION = "scs_protocolVersion";
    /**
     * 获取余额
     */
    public static final String SCS_GET_BALANCE = "scs_getBalance";
    /**
     * 获取nonce
     */
    public static final String SCS_GET_TRANSACTION_COUNT = "scs_getTransactionCount";
    /**
     * 返回指定块内的交易数量，使用哈希来指定块。
     */
    public static final String SCS_GET_BLOCK_TRANSACTION_COUNT_BY_HASH = "scs_getBlockTransactionCountByHash";
    /**
     * 返回指定块内的交易数量，使用块编号指定块。
     */
    public static final String SCS_GET_BLOCK_TRANSACTION_COUNT_BY_NUMBER = "scs_getBlockTransactionCountByNumber";

    /**
     * 返回指定地址的代码。
     */
    public static final String SCS_GET_CODE = "scs_getCode";
    /**
     * 使用如下公式计算以太坊签名：sign(keccak256("\x19Ethereum Signed Message:\n" + len(message) + message)))。
     */
    public static final String SCS_SIGN = "scs_sign";
    /**
     * 发送交易
     */
    public static final String SCS_SEND_TRANSACTION = "scs_sendTransaction";

    public static final String SCS_SEND_RAW_TRANSACTION = "scs_sendRawTransaction";

    /**
     * 估算手续费上限
     */
    public static final String SCS_ESTIMATE_GAS = "scs_estimateGas";

    /**
     * 返回具有指定哈希的块。
     */
    public static final String SCS_GET_BLOCK_BY_HASH = "scs_getBlockByHash";

    /**
     * 返回指定编号的块。
     */
    public static final String SCS_GET_BLOCK_BY_NUMBER = "scs_getBlockByNumber";

    /**
     * 根据交易hash获取信息
     */
    public static final String SCS_GET_TRANSACTION_BY_HASH = "scs_getTransactionByHash";

    public static final String SCS_BLOCK_NUMBER = "scs_blockNumber";
    /**
     * 获取gasPrice
     */
    public static final String SCS_GAS_PRICE = "scs_gasPrice";

    /**
     * 获取回执交易
     */
    public static final String SCS_GET_TRANSACTION_RECEIPT = "scs_getTransactionReceipt";

    /**
     * 铸造NFT
     */
    public static final String SCS_NFT_MINT = "scs_nft_mint";

    /**
     * 铸造NFT
     */
    public static final String SCS_DEPLOY_CONTRACT = "scs_deploy_contract";

    /**
     * 铸造NFT
     */
    public static final String SCS_CONTRACT_ADDRESS_BY_HASH = "scs_contractAddressByHash";

    /**
     * 获取TokenId
     */
    public static final String SCS_GET_TOKEN_BY_HASH = "scs_getTokenByHash";

    /**
     * 合约查询
     */
    public static final String SCS_CALL = "scs_call";

    public static final String SCS_EXISTING_EVIDENCE = "scs_existing_evidence";

    public static final String SCS_GET_EVIDENCE_CONTRACT_ADDRESS = "scs_get_evidence_contract_address";



    public static final String CONTRACT_TRANSFER_FROM = "transferFrom";

    public static final String CONTRACT_EVIDENCE = "evidence";
    public static final String CONTRACT_OWNER_OF = "ownerOf";

    public static final String SCS_NFT_MINT_ALONE = "scs_nft_mint_alone";

    public static final String SCS_DIGITAL_COLLECTION_MINT = "scs_digital_collection_mint";

    public static final String SCS_DIGITAL_COLLECTION_LIST = "scs_digital_collection_list";

    public static final String SCS_DIGITAL_COLLECTION_TRANSACTION = "scs_digital_collection_transaction";

    public static final String SCS_PAN_GU_EVIDENCE = "scs_pangu_evidence";

    /**
     * 4907合约方法
     */
    public static final String CONTRACT_4907_SET_USER = "setUser";
    public static final String CONTRACT_4907_USER_OF = "userOf";
    public static final String CONTRACT_4907_USER_EXPIRES = "userExpires";

    public static final String CONTRACT_TRANSFER_OF_OWNER_SHIP = "transferOfOwnerShip";
    public static final String CONTRACT_SET_NFT_LOCK_TIME_FLAG = "setNftLockTimeFlag";
    public static final String CONTRACT_GET_NFT_LOCK_TIME_FLAG = "getNftLockTimeFlag";
    public static final String CONTRACT_SET_NFT_LOCK_COUNT_FLAG = "setNftLockCountFlag";
    public static final String CONTRACT_GET_NFT_LOCK_COUNT_FLAG = "getNftLockCountFlag";
    public static final String CONTRACT_SET_NFT_LOCK_TIME = "setNftLockTime";
    public static final String CONTRACT_GET_NFT_LOCK_TIME = "getNftLockTime";
    public static final String CONTRACT_SET_NFT_LOCK_COUNT = "setNftLockCount";
    public static final String CONTRACT_GET_NFT_LOCK_COUNT = "getNftLockCount";
    public static final String CONTRACT_TOKEN_URI = "tokenURI";
    public static final String CONTRACT_TOTAL_SUPPLY = "totalSupply";
    public static final String CONTRACT_GET_OWNER_OF_CONTRACT = "getOwnerOfContract";

    /**
     * 铸造1155合约NFT
     */
    public static final String SCS_1155_NFT_MINT = "scs_1155_nft_mint";
    public static final String CONTRACT_1155_SAFE_TRANSFER_FROM = "safeTransferFrom";
    public static final String CONTRACT_1155_BALANCE_OF = "balanceOf";
    public static final String CONTRACT_1155_BALANCE_OF_BATCH = "balanceOfBatch";
    public static final String CONTRACT_1155_VERIFICATION_NFT = "verificationNft";

}
