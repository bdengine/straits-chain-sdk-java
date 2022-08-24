package com.shangchain.straitchain.params;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shangchain
 */
@Data
public class StraitChainParam {
    public StraitChainParam() {
        jsonrpc= "2.0";
        id = 1;
        params = new ArrayList<>();
    }

    private String jsonrpc;
    private String method;
    private List<Object> params;
    private Integer id;
}
