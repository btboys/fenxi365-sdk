package com.fenxi365.api.tax;

import cn.hutool.json.JSONUtil;
import com.fenxi365.api.Fenxi365Client;
import com.fenxi365.api.Result;
import com.fenxi365.open.model.dto.*;
import com.fenxi365.open.model.vo.*;

/**
 *  发票归集
 *  <p>Creation: 2025年06月18日</p>
 * @version 1.0
 * @author ____′↘夏悸
 */
public class GjServer {

    private final Fenxi365Client client;

    /**
     * 构建
     * @param fenxi365Client 客户端
     */
    public GjServer(Fenxi365Client fenxi365Client) {
        this.client = fenxi365Client;
    }

    /**
     * 发起发票归集任务（异步方式） <br>
     * 该接口用于发起企业进销项发票归集任务，该接口是异步接口，不会立即返回归集结果，需要通过查询归集任务状态接口查询<br>
     * 归集任务的执行状态，待归集成功后，可以进一步通过获取发票数据接口来获取发票数据
     */
    public Result<CollectTaskDto> collectTask(CollectTaskVo collectTaskVo) {
        return this.client.execute("/api/tax/api/tax/invoice/gj/collectTask", JSONUtil.toJsonStr(collectTaskVo), () -> CollectTaskDto.class);
    }

    /**
     * 查询异步方式发起的发票归集任务状态<br>
     * 该接口可用于查询：<br>1、发票归集任务的状态 <br>2、发票作废和红冲状态归集任务的状态
     */
    public Result<CollectTaskStatusDto> collectTaskStatus(CollectTaskStatusVo collectTaskStatusVo) {
        return this.client.execute("/api/tax/invoice/gj/collectTaskStatus", JSONUtil.toJsonStr(collectTaskStatusVo), () -> CollectTaskStatusDto.class);
    }

    /**
     * 获取发票数据 <br>
     * 该接口用于归集企业近5年内的进销项发票 需要在归集任务处理成功之后调用
     */
    public Result<CollectTaskPageDataDto> getCollectTaskPageData(CollectTaskPageDataVo collectTaskPageDataVo) {
        return this.client.execute("/api/tax/invoice/gj/getCollectTaskPageData", JSONUtil.toJsonStr(collectTaskPageDataVo), () -> CollectTaskPageDataDto.class);
    }

    /**
     * 发起发票作废和红冲状态归集任务 <br>
     * 该接口用于企业指定开票月份，针对作废和红冲状态的发票发起归集任务，任务发起后，调用【查询归集任务状态】接口查询任务状态和结果。
     */
    public Result<CollectTaskDto> redInvoiceCollectTask(RedInvoiceCollectTaskVo redInvoiceCollectTaskVo) {
        return this.client.execute("/api/tax/invoice/gj/redInvoiceCollectTask", JSONUtil.toJsonStr(redInvoiceCollectTaskVo), () -> CollectTaskDto.class);
    }

    /**
     * 申请全量发票汇总信息
     * 该接口用于申请全量发票汇总信息
     * 注：开票日期止不能大于当前日期
     **/
    public Result<QueryFpsljeseHjDto> queryFpsljeseHj(QueryFpsljeseHjVo queryFpsljeseHjVo) {
        return this.client.execute("/api/tax/invoice/gj/queryFpsljeseHj", JSONUtil.toJsonStr(queryFpsljeseHjVo), () -> QueryFpsljeseHjDto.class);
    }

    /**
     * 获取全量发票汇总信息 <br>
     * 根据taskId查询全量发票汇总信息
     */
    public Result<GetFpsljeseHjDto> getFpsljeseHj(GetFpsljeseHjVo getFpsljeseHjVo) {
        return this.client.execute("/api/tax/invoice/gj/getFpsljeseHj", JSONUtil.toJsonStr(getFpsljeseHjVo), () -> GetFpsljeseHjDto.class);
    }

    /**
     * 撤销归集待执行任务 <br>
     * 该接口用于撤销处于待执行状态的归集任务
     */
    public Result<TerminateCollectionTaskDto> terminateCollectionTask(TerminateCollectionTaskVo terminateCollectionTaskVo) {
        return this.client.execute("/api/tax/invoice/gj/terminateCollectionTask", JSONUtil.toJsonStr(terminateCollectionTaskVo), () -> TerminateCollectionTaskDto.class);
    }

    /**
     * 同步发起归集任务 <br>
     * 该接口用于同步发起企业进销项发票归集任务，最大归集发票数量5000条 <br>
     * 注：开票日期止不能大于当前日期 <br>
     */
    public Result<SyncCollectTaskDto> syncCollectTask(SyncCollectTaskVo syncCollectTaskVo) {
        return this.client.execute("/api/tax/invoice/gj/syncCollectTask", JSONUtil.toJsonStr(syncCollectTaskVo), () -> SyncCollectTaskDto.class);
    }

    /**
     * 同步发起发票基础信息归集任务 <br>
     * 该接口用于同步发起企业进销项发票基础信息归集任务 <br>
     * 注：开票日期止不能大于当前日期 <br>
     */
    public Result<SyncFpjbxxDto> syncFpjbxx(SyncFpjbxxVo syncFpjbxxVo) {
        return this.client.execute("/api/tax/invoice/gj/syncFpjbxx", JSONUtil.toJsonStr(syncFpjbxxVo), () -> SyncFpjbxxDto.class);
    }

}
