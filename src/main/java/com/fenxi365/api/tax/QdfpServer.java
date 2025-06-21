package com.fenxi365.api.tax;

import cn.hutool.json.JSONUtil;
import com.fenxi365.api.Fenxi365Client;
import com.fenxi365.api.Result;
import com.fenxi365.open.model.dto.*;
import com.fenxi365.open.model.vo.*;

/**
 *  com.fenxi365.api.tax
 *  <p>Creation: 2025年06月18日</p>
 * @version 1.0
 * @author ____′↘夏悸
 */
public class QdfpServer {

    private final Fenxi365Client client;

    /**
     * 构建
     * @param fenxi365Client 客户端
     */
    public QdfpServer(Fenxi365Client fenxi365Client) {
        this.client = fenxi365Client;
    }

    /**
     * 获取人脸识别认证二维码（税务app）<br>
     * 0.这个接口调用之前要先调用登录接口完成登录操作<br>
     * 1.通过本接口可获取当前登录用户人脸识别认证使用的二维码文本信息。<br>
     * 2.因税局变更，在调完本接口，完成人脸识别后必须去请求：获取人脸识别认证结果，该扫脸认证方可生效！！！<br>
     * 注：这个接口返回的ewm字段，需要自行通过草料二维码等工具转换为二维码，然后才能用来扫脸
     */
    public Result<RzewmDto> rzewm(RzewmVo rzewmVo) {
        return this.client.execute("/api/tax/invoice/qdfp/rzewm", JSONUtil.toJsonStr(rzewmVo), () -> RzewmDto.class);
    }

    /**
     * 获取人脸识别结果<br/>
     * 通过本接口可获取当前登录用户人脸识别的结果
     */
    public Result<RzztcxDto> rzztcx(RzztcxVo rzztcxVo) {
        return this.client.execute("/api/tax/invoice/qdfp/rzztcx", JSONUtil.toJsonStr(rzztcxVo), () -> RzztcxDto.class);
    }

    /**
     * 数电发票开具接口<br/>
     * 业务说明<br/>
     * 业务系统通过本接口可实现开具全面数字化电子发票的开具（增值税专用发票，增值税普通发票）。<br/>
     * 特定业务已支持：“二手车发票”，"建筑服务发票"，"不动产经营租赁服务"，"不动产销售服务发票"，"拖拉机和联合收割机发票"，"旅客运输服务发票"，"机动车"，"货物运输服务发票"，"代收车船税"，"成品油"，"自产农产品销售发票"，"农产品收购发票"。<br/>
     * 请求与响应流程<br/>
     * 本接口支持数电发票的开具操作，默认以同步方式执行。若同步处理过程超过40秒，系统将自动将该请求转为异步处理模式，并同步返回（返回code=2001 message=因税局不稳定导致任务执行超时，本次请求已自动转为异步方式，可通过异步请求结果查询获取结果），对应异步请求结果查询接口，以确保用户界面的响应性。
     */

    public Result<FpkjZzsDto> fpkjZzs(FpkjZzsVo fpkjZzsVo) {
        return this.client.execute("/api/tax/invoice/qdfp/fpkjZzs", JSONUtil.toJsonStr(fpkjZzsVo), () -> FpkjZzsDto.class);
    }

    /**
     * 数电已开发票版式文件下载（单个）<br/>
     * 下载指定发票的PDF/OFD/XML格式版式文件
     */
    public Result<BswjxzDto> bswjxz(BswjxzVo bswjxzVo) {
        return this.client.execute("/api/tax/invoice/qdfp/bswjxz", JSONUtil.toJsonStr(bswjxzVo), () -> BswjxzDto.class);
    }

    /**
     * 数电开票-业务回调接口-异步请求结果查询<br/>
     * 获取异步请求下的业务处理结果。
     */
    public Result<AsynResultDto> asynResult(AsynResultVo asynResultVo) {
        return this.client.execute("/api/tax/invoice/qdfp/asynResult", JSONUtil.toJsonStr(asynResultVo), () -> AsynResultDto.class);
    }

    /**
     * 品名查询税收分类信息<br/>
     * 通过商品名称智能查询相匹配税收编码列表。
     */
    public Result<SpxxZnFmDto> spxxZnFm(SpxxZnFmVo spxxZnFmVo) {
        return this.client.execute("/api/tax/invoice/qdfp/spxxZnFm", JSONUtil.toJsonStr(spxxZnFmVo), () -> SpxxZnFmDto.class);
    }

    /**
     * 扫脸时长查询<br/>
     * 通过本接口可获取当前登录纳税人的风险等级，预警等级等授信信息。<br/><br/>
     * 特别提示：”办税(开票)人授信类别查询“接口，返回参数scanTime是扫脸二维码能设置的最大时长，然后在”设置二维码有效时长“接口里设置phxxz参数值与scanTime保持一致，就可以最大限度设置扫脸二维码有效时间，提升开票体验。
     */
    public Result<SxlbCxDto> sxlbCx(SxlbCxVo sxlbCxVo) {
        return this.client.execute("/api/tax/invoice/qdfp/sxlbCx", JSONUtil.toJsonStr(sxlbCxVo), () -> SxlbCxDto.class);
    }

    /**
     * 扫脸时长设置<br/>
     * 可通过此接口设置扫脸二维码的有效时长,登录人的身份必须是财务负责人或法定代表人。<br/><br/>
     * 特别提示：”办税(开票)人授信类别查询“接口，返回参数scanTime是扫脸二维码能设置的最大时长，然后在”设置二维码有效时长“接口里设置phxxz参数值与scanTime保持一致，就可以最大限度设置扫脸二维码有效时间，提升开票体验。
     */
    public Result<Boolean> smsc(SmscVo smscVo) {
        return this.client.execute("/api/tax/invoice/qdfp/smsc", JSONUtil.toJsonStr(smscVo), () -> Boolean.class);
    }
}
