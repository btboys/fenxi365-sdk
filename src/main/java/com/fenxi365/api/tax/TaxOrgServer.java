package com.fenxi365.api.tax;

import cn.hutool.json.JSONUtil;
import com.fenxi365.api.Fenxi365Client;
import com.fenxi365.api.Result;
import com.fenxi365.open.model.dto.*;
import com.fenxi365.open.model.vo.LoadOrgTaxInfoVo;
import com.fenxi365.open.model.vo.ProductCancelVo;
import com.fenxi365.open.model.vo.ProductPurchaseModifyVo;
import com.fenxi365.open.model.vo.ProductPurchaseVo;
import lombok.NonNull;

import java.util.List;

/**
 *  税务管理
 */
public class TaxOrgServer {

    private final Fenxi365Client client;

    /**
     * 构建
     * @param fenxi365Client 客户端
     */
    public TaxOrgServer(Fenxi365Client fenxi365Client) {
        this.client = fenxi365Client;
    }

    /**
     * 订购产品
     * 1、第一次订购在订购完成后会返回企业ID，订购成功后返回aggOrgId即为当前的企业ID，这个值后面请求都会用到，要保存起来
     * 2、这个接口可以反复调用，但是纳税人识别号和企业名称多次调用时必须一致，否则视为创建新的企业，如果识别号相同，公司名称不同也是会抛出错误的
     * 3、基于2中的问题如果税号、区域、企业名称在订购过程中有错误，需要修改，可以调用订购修改接口进行修改
     * 4、订购成功后应该尽快调用账号创建接口完成账号创建，这一步操作对后续其它操作至关重要！！
     */
    public Result<ProductPurchaseDto> productPurchase(ProductPurchaseVo productPurchaseVo) {
        return this.client.execute("/api/tax/org/productPurchase", JSONUtil.toJsonStr(productPurchaseVo), () -> ProductPurchaseDto.class);
    }

    /**
     * 订购修改
     * 返回data为true或false，表示修改成功或失败
     * 产品订购修改用于解决客户在产品订购时录入错误的税号、区域、企业名称而无无法修改的问题。
     */
    public Result<ProductPurchaseModifyDto> productPurchaseModify(ProductPurchaseModifyVo productPurchaseModifyVo) {
        return this.client.execute("/api/tax/org/productPurchaseModify", JSONUtil.toJsonStr(productPurchaseModifyVo), () -> ProductPurchaseModifyDto.class);
    }

    /**
     * 订购取消
     * 订购取消是订购产品的反向操作。
     */
    public Result<ProductCancelDto> productCancel(ProductCancelVo productCancelVo) {
        return this.client.execute("/api/tax/org/productCancel", JSONUtil.toJsonStr(productCancelVo), () -> ProductCancelDto.class);
    }

    /**
     * 订购查询
     * 查询当前有效的订购列表信息。
     */
    public Result<List<ProductListDto>> productList(String aggOrgId) {
        return this.client.execute("/api/tax/org/productList?aggOrgId=" + aggOrgId, body -> body.getJSONArray("data").toList(ProductListDto.class));
    }

    /**
     * 取消企业授权
     * 返回data为true或false，表示取消成功或失败
     * 功能即为删除企业信息
     * 1）当与服务的企业客户解除合作关系（不再使用本平台接口）时，请调用删除接口，删除该企业。否则如该企业已使用预采集功能，平台每月初会自动进行历史月份发票采集，因历史月份发票每月仅可采集一次，将造成该企业自身无法采集到历史月份发票数据。
     * 2）企业删除以后，不可操作；如果再次创建，会创建新的企业，并做统计。（如果相同企业删除再创建，会得到新的ID）
     */
    public Result<Boolean> deleteOrg(@NonNull String aggOrgId) {
        return this.client.execute("/api/tax/org/delete?aggOrgId=" + aggOrgId, () -> Boolean.class);
    }

    /**
     * 查询企业信息
     * 1、用于查询从税局读取企业信息状态
     * 2、接口返回字段较多，以具体返回结果为准
     */
    public Result<QueryOrgInfoDto> queryOrgInfo(@NonNull String aggOrgId) {
        return this.client.execute("/api/tax/org/queryOrgInfo?aggOrgId=" + aggOrgId, () -> QueryOrgInfoDto.class);
    }

    /**
     * 发起采集企业税务信息
     * 该接口用于发起采集企业税务信息任务，任务创建成功后会返回一个taskId，此taskId可以用于查询任务状态信息。
     * 如果要调用上传申报材料、上传申报等接口，要先进行企业税务信息采集，采集完成后方可进行
     */
    public Result<LoadOrgTaxInfoDto> loadOrgTaxInfo(LoadOrgTaxInfoVo loadOrgTaxInfoVo) {
        return this.client.execute("/api/tax/org/loadOrgTaxInfo", JSONUtil.toJsonStr(loadOrgTaxInfoVo), () -> LoadOrgTaxInfoDto.class);
    }

    /**
     * 查询企业税务信息状态
     * 该接口用于查询从税局读取企业信息状态。
     * 返回data为true或false，表示修改成功或失败
     */
    public Result<Boolean> hasReadSJInfo(@NonNull String aggOrgId) {
        return this.client.execute("/api/tax/org/hasReadSJInfo?aggOrgId=" + aggOrgId, () -> Boolean.class);
    }
}
