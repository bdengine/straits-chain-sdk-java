package com.shangchain.straitchain.service;

import com.shangchain.straitchain.exception.StraitChainException;

import java.io.File;

/**
 * 2022/4/26
 * ipfs接口
 *
 * @author shangchain Junisyoan
 */
public interface Ipfs {

    /**
     * 上传到ipfs，文件上传很慢，文件不要太大
     * @param address 发送者地址
     * @param file 文件
     * @return cid
     */
    String ipfsUpload(String address, File file) throws StraitChainException;

}
