package com.shangchain.straitchain.utils;

import com.google.common.collect.ImmutableList;
import org.bitcoinj.crypto.*;
import org.bitcoinj.wallet.DeterministicSeed;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import sun.security.provider.SecureRandom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 2022/4/28
 * 通行证
 *
 * @author shangchain Junisyoan
 */
public class StraitChainWalletUtils {
    /**
     * path路径
     * m / purpose' / coin_type' / account' / change / address_index
     * purporse': 固定值44', 代表是BIP44
     * coin_type': 这个代表的是币种, 可以兼容很多种币, 比如BTC是0', ETH是60',btc一般是 m/44'/0'/0'/0,eth一般是 m/44'/60'/0'/0
     * account’：账号
     * change’: 0表示外部链（External Chain），用户接收比特币，1表示内部链（Internal Chain）,用于接收找零
     * address_index：钱包索引
     */
    private final static ImmutableList<ChildNumber> BIP44_ETH_ACCOUNT_ZERO_PATH =
            ImmutableList.of(new ChildNumber(44, true), new ChildNumber(60, true),
                    ChildNumber.ZERO_HARDENED, ChildNumber.ZERO);


    /**
     * 创建通行证
     * @throws MnemonicException.MnemonicLengthException
     */
    public static void createWallet() throws MnemonicException.MnemonicLengthException {
        SecureRandom secureRandom = new SecureRandom();
        byte[] entropy = new byte[DeterministicSeed.DEFAULT_SEED_ENTROPY_BITS / 8];
        secureRandom.engineNextBytes(entropy);

        //生成12位助记词
        List<String> str = MnemonicCode.INSTANCE.toMnemonic(entropy);
        //使用助记词生成通行证
        byte[] seed = MnemonicCode.toSeed(str, "");
        DeterministicKey masterPrivateKey = HDKeyDerivation.createMasterPrivateKey(seed);
        DeterministicHierarchy deterministicHierarchy = new DeterministicHierarchy(masterPrivateKey);
        DeterministicKey deterministicKey = deterministicHierarchy
                .deriveChild(BIP44_ETH_ACCOUNT_ZERO_PATH, false, true, new ChildNumber(0));
        byte[] bytes = deterministicKey.getPrivKeyBytes();
        ECKeyPair keyPair = ECKeyPair.create(bytes);
        //通过公钥生成通行证地址
        String address = Keys.getAddress(keyPair.getPublicKey());
        System.out.println();
        System.out.println("助记词：");
        System.out.println(str);
        System.out.println();
        System.out.println("地址：");
        System.out.println("0x"+address);
        System.out.println();
        System.out.println("私钥：");
        System.out.println("0x"+keyPair.getPrivateKey().toString(16));
        System.out.println();
        System.out.println("公钥：");
        System.out.println(keyPair.getPublicKey().toString(16));
    }

    /**
     * 创建通行证
     * @throws MnemonicException.MnemonicLengthException
     */
    public static Map<String,String> createWalletReturnMap()  throws MnemonicException.MnemonicLengthException {
        SecureRandom secureRandom = new SecureRandom();
        byte[] entropy = new byte[DeterministicSeed.DEFAULT_SEED_ENTROPY_BITS / 8];
        secureRandom.engineNextBytes(entropy);

        //生成12位助记词
        List<String> str = MnemonicCode.INSTANCE.toMnemonic(entropy);

        //使用助记词生成钱包种子
        byte[] seed = MnemonicCode.toSeed(str, "");
        DeterministicKey masterPrivateKey = HDKeyDerivation.createMasterPrivateKey(seed);
        DeterministicHierarchy deterministicHierarchy = new DeterministicHierarchy(masterPrivateKey);
        DeterministicKey deterministicKey = deterministicHierarchy
                .deriveChild(BIP44_ETH_ACCOUNT_ZERO_PATH, false, true, new ChildNumber(0));
        byte[] bytes = deterministicKey.getPrivKeyBytes();
        ECKeyPair keyPair = ECKeyPair.create(bytes);
        //通过公钥生成钱包地址
        String address = Keys.getAddress(keyPair.getPublicKey());
        Map<String,String> map = new HashMap<>(4);
        StringBuilder buffer = new StringBuilder();
        for (String s : str) {
            buffer.append(s).append(" ");
        }

        map.put("word", buffer.toString().trim());
        map.put("address","0x"+address);
        map.put("privateKey", "0x"+keyPair.getPrivateKey().toString(16));
        map.put("publicKey", keyPair.getPublicKey().toString(16));
        return map;
    }
}

