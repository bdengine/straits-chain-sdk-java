package com.shangchain.straitchain.utils;

import org.web3j.crypto.Hash;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.Sign;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpType;
import org.web3j.utils.Numeric;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * <p> </p>
 *
 * @author
 * @since 2023/4/7 13:49
 */
public class SNSUtil {
    public static final String ZERO_ADDRESS = "0x0000000000000000000000000000000000000000";
    public static final byte[] EMPTY = new byte[32];

    public static boolean isZeroAddress(String address) {
        return address.equals(ZERO_ADDRESS);
    }

    public static byte[] hashEns(String ens) {
        ens = DomainValidatorUtil.validateAndEncode(ens);

        return hashEns(ens.split("\\."));
    }


    public static byte[] hashLabel(String label) {
        return Hash.sha3(DomainValidatorUtil.normalise(label).getBytes(StandardCharsets.UTF_8));
    }


    public static byte[] hashEns(String[] ens) {
        return nameHash(ens);
    }

    private static byte[] nameHash(String[] labels) {
        if (labels.length != 0 && !labels[0].equals("")) {
            String[] tail;
            if (labels.length == 1) {
                tail = new String[0];
            } else {
                tail = (String[]) Arrays.copyOfRange(labels, 1, labels.length);
            }

            byte[] remainderHash = nameHash(tail);
            byte[] result = Arrays.copyOf(remainderHash, 64);
            byte[] labelHash = Hash.sha3(labels[0].getBytes(StandardCharsets.UTF_8));
            System.arraycopy(labelHash, 0, result, 32, labelHash.length);
            return Hash.sha3(result);
        } else {
            return EMPTY;
        }
    }

    public static byte[] mergeByteArrays(byte[] a, byte[] b) {
        int length = a.length + b.length;
        byte[] result = new byte[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }


    public static byte[] encode(RawTransaction rawTransaction, Sign.SignatureData signatureData) {
        List<RlpType> values = TransactionEncoder.asRlpValues(rawTransaction, signatureData);
        RlpList rlpList = new RlpList(values);
        return RlpEncoder.encode(rlpList);
    }

}
