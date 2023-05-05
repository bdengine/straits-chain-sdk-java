package com.shangchain.straitchain.utils;

import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

import java.nio.charset.StandardCharsets;

/**
 * <p> </p>
 *
 * @author
 * @since 2023/4/7 13:49
 */
public class SNSUtil {
    static String ZERO_ADDRESS = "0x0000000000000000000000000000000000000000";

    public static boolean isZeroAddress(String address) {
        return address.equals(ZERO_ADDRESS);
    }

    public static byte[] hashEns(String ens) {
        ens = ens.replaceAll("@", ".");

        return hashEns(ens.split("\\."));
    }

    public static byte[] hashEns(String[] ens) {
        byte[] r = new byte[]{};
        for (int i = 0; i < ens.length; i++) {
            if (r.length == 0) {
                r = Hash.sha3(Numeric.hexStringToByteArray(ens[i]));
            } else {

                r = Hash.sha3(mergeByteArrays(r, Hash.sha3(Numeric.hexStringToByteArray(ens[i]))));
            }

        }
        return r;
    }

    public static byte[] hashLabel(String label) {
        return Hash.sha3(DomainValidatorUtil.normalise(label).getBytes(StandardCharsets.UTF_8));
    }


    public static boolean validRootEns(String rootName) {
        if (rootName == null || rootName.isEmpty()) {
            return false;
        }
        if (rootName.contains("\\.") || rootName.contains("@")) {
            return false;
        }
        return true;
    }

    public static boolean validL1Ens(String l1Name) {
        if (l1Name == null || l1Name.isEmpty()) {
            return false;
        }
        int l1 = l1Name.split("@").length;
        int l2 = l1Name.split(".").length;
        if (l1 + l2 == 3) {
            return true;
        }
        return false;
    }

    public static byte[] mergeByteArrays(byte[] a, byte[] b) {
        int length = a.length + b.length;
        byte[] result = new byte[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

}
