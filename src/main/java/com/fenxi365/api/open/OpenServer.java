package com.fenxi365.api.open;

import cn.hutool.json.JSONUtil;
import com.fenxi365.api.Fenxi365Client;
import com.fenxi365.api.Result;
import com.fenxi365.open.model.vo.open.InvoiceRewriteVo;
import com.fenxi365.open.model.vo.open.OrgVo;

/**
 *  开放平台系统操作
 */
public class OpenServer {
    private final Fenxi365Client client;

    /**
     * 构建
     * @param fenxi365Client 客户端
     */
    public OpenServer(Fenxi365Client fenxi365Client) {
        this.client = fenxi365Client;
    }

    /**
     * 新增税号
     * 在开放平台中新增税号，用于后续的发票开具
     */
    public Result<Boolean> orgCreate(OrgVo orgVo) {
        return this.client.execute("/open/inner/org/create", JSONUtil.toJsonStr(orgVo), () -> Boolean.class);
    }

    /**
     * 发票回写
     * 用于在开票失败的时候，为更新发票信息中的相关字段信息
     */
    public Result<Boolean> invoiceRewrite(InvoiceRewriteVo invoiceRewriteVo) {
        return this.client.execute("/open/inner/invoice/rewrite", JSONUtil.toJsonStr(invoiceRewriteVo), () -> Boolean.class);
    }
}
