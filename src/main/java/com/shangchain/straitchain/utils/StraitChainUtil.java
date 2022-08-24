package com.shangchain.straitchain.utils;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 2022/4/26
 * 海峡链工具
 *
 * @author shangchain Junisyoan
 */
public class StraitChainUtil {

    private static final String HEX_PREFIX = "0x";

    /**
     * 对Map中的key进行排序
     * @param map 参数
     * @return a=1&b=2&c=3
     */
    public static String sortMapToString (Map<String,?> map){
        StringBuilder buffer = new StringBuilder();
        Map<String,?> sortMap = new TreeMap<>(map);
        for (String key : sortMap.keySet()) {
            buffer.append(key).append("=").append(map.get(key)).append("&");
        }
        String s = buffer.toString();
        if (s.length() != 0) {
            s = s.substring(0, buffer.length() - 1);
        }
        return s;
    }

    /**
     * 将参数转换为 JSONObject，进行MD5加密
     */
    public static String encryptDataByMd5(Map<String,?> object, String appKey){
        String sortMapToString = sortMapToString(object);
        return SecureUtil.md5(sortMapToString+appKey);
    }

    public static boolean verifySignData(String signData, Map<String,?> object, String appKey){
        String sortMapToString = sortMapToString(object);
        String verifyData = SecureUtil.md5(sortMapToString+appKey);
        return signData.equals(verifyData);
    }

    /**
     * 数组需要转成集合形式List
     */
    public static String encryptDataByMd5(List<Object> params, String appKey){
        StringBuilder buffer = new StringBuilder();
        for (Object param : params) {
            if (param != null) {
                buffer.append(param).append("&");
            }
        }
        String result = buffer.append(appKey).toString();
        return SecureUtil.md5(result);
    }

    public static boolean verifySignData(String signData, List<Object> params, String appKey){
        int lastIndex = params.size() - 1;
        Object paramSignData = params.get(lastIndex);
        if (!StrUtil.equals(paramSignData.toString(), signData)) {
            return false;
        }
        // 删除签名的数据
        params.remove(lastIndex);
        String md5 = encryptDataByMd5(params, appKey);
        return signData.equals(md5);
    }

    public static BigInteger toBigInteger(String hexValue){
        if (StrUtil.startWith(hexValue, HEX_PREFIX)) {
            hexValue = hexValue.replace(HEX_PREFIX, "");
        }
        if (!Validator.isHex(hexValue)) {
            throw new IllegalArgumentException("参数需要为16进制");
        }
        return new BigInteger(hexValue, 16);
    }
}
