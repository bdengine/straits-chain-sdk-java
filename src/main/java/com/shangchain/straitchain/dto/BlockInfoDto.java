package com.shangchain.straitchain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 2022/4/27
 * 块信息，参考文档
 *
 * @author shangchain Junisyoan
 */
@NoArgsConstructor
@Data
public class BlockInfoDto {

    public String logsBloom;
    public String totalDifficulty;
    public String receiptsRoot;
    public String extraData;
    public String baseFeePerGas;
    public List<Transactions> transactions;
    public String nonce;
    public String miner;
    public String difficulty;
    public String gasLimit;
    public String number;
    public String gasUsed;
    public List<String> uncles;
    public String sha3Uncles;
    public String size;
    public String transactionsRoot;
    public String stateRoot;
    public String mixHash;
    public String parentHash;
    public String hash;
    public String timestamp;

    @NoArgsConstructor
    @Data
    public static class Transactions {
        public String blockHash;
        public String transactionIndex;
        public String type;
        public String nonce;
        public String input;
        public String r;
        public String s;
        public String v;
        public String blockNumber;
        public String gas;
        public String from;
        public String to;
        public String value;
        public String hash;
        public String gasPrice;
    }
}
