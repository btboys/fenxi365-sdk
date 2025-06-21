package com.fenxi365.api.tax;

import cn.hutool.json.JSONUtil;
import com.fenxi365.api.Fenxi365Client;
import com.fenxi365.api.Result;
import com.fenxi365.open.model.dto.invoice.red.*;
import com.fenxi365.open.model.vo.invoice.red.*;
import lombok.RequiredArgsConstructor;

/**
 *  com.fenxi365.api.tax
 *  <p>Creation: 2025年06月21日</p>
 * @version 1.0
 * @author ____′↘夏悸
 */
@RequiredArgsConstructor
public class InvoiceRedServer {
    private final Fenxi365Client client;

    /**
     * 申请红字信息表
     * 对数电发票进行红冲前，需先申请红字信息确认单，可通过本接口发起红字信息确认单申请流程。<br/>
     * 对于重复提交红字确认申请单的，直接返回已经生成的红字申请单信息，如果红字发票开具成功的会返回红字发票信息<br/>
     * <br/>
     * 注：申请成功后，可以通过hzuuid去红字确认单明细信息进行查询，继而进行其他操作（红字确认单确认，撤销等）
     */
    public Result<HzqrxxSaveDto> hzqrxxSave(HzqrxxSaveVo hzqrxxSaveVo) {
        return this.client.execute("/api/tax/invoice/qdfp/hzqrxxSave", JSONUtil.toJsonStr(hzqrxxSaveVo), () -> HzqrxxSaveDto.class);
    }

    /**
     * 红字发票开具
     * 红字申请单保存如果返回未开具，可调用此接口开具红字发票<br/>
     * 如果已经红冲过的发票，再次调用此接口直接返回已经红冲过的红字发票信息
     */
    public Result<HzFpkjDto> hzFpkj(HzFpkjVo hzFpkjVo) {
        return this.client.execute("/api/tax/invoice/qdfp/hzFpkj", JSONUtil.toJsonStr(hzFpkjVo), () -> HzFpkjDto.class);
    }

    /**
     * 确认红字信息表
     * 购方发起红字信息确认单申请后，销方可通过该接口进行红字单确认，并自动开具相对应的负数发票。
     */
    public Result<HzqrxxConfirmDto> hzqrxxConfirm(HzqrxxConfirmVo hzqrxxConfirmVo) {
        return this.client.execute("/api/tax/invoice/qdfp/hzqrxxConfirm", JSONUtil.toJsonStr(hzqrxxConfirmVo), () -> HzqrxxConfirmDto.class);
    }

    /**
     * 撤销红字信息表
     * 购方（销方）发起红字信息确认单申请后，可通过该接口进行红字确认单撤销操作。
     */
    public Result<HzqrxxCancelDto> hzqrxxCancel(HzqrxxCancelVo hzqrxxCancelVo) {
        return this.client.execute("/api/tax/invoice/qdfp/hzqrxxCancel", JSONUtil.toJsonStr(hzqrxxCancelVo), () -> HzqrxxCancelDto.class);
    }

    /**
     * 查询红字信息列表
     * 对数电发票红冲时，需先完成红字信息确认单申请，本接口可查询本纳税主体下已申请或待确认的红字信息确认单列表。
     */
    public Result<HzqrxxListDto> hzqrxxList(HzqrxxListVo hzqrxxListVo) {
        return this.client.execute("/api/tax/invoice/qdfp/hzqrxxList", JSONUtil.toJsonStr(hzqrxxListVo), () -> HzqrxxListDto.class);
    }

    /**
     * 查询红字信息明细
     * 对数电发票红冲时，需先完成红字信息确认单申请，本接口可通过红字信息UUID查询对应的信息确认单处理（红冲）状态。
     */
    public Result<HzqrxxDetailDto> hzqrxxDetail(HzqrxxDetailVo hzqrxxDetailVo) {
        return this.client.execute("/api/tax/invoice/qdfp/hzqrxxDetail", JSONUtil.toJsonStr(hzqrxxDetailVo), () -> HzqrxxDetailDto.class);
    }

    /**
     * 红字发票概况统计
     * 该接口用于查询红字发票概况统计。
     */
    public Result<HzfptjxxcxDto> hzfptjxxcx(HzfptjxxcxVo hzfptjxxcxVo) {
        return this.client.execute("/api/tax/invoice/qdfp/hzfptjxxcx", JSONUtil.toJsonStr(hzfptjxxcxVo), () -> HzfptjxxcxDto.class);
    }
}
