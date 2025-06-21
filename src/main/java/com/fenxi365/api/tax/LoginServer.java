package com.fenxi365.api.tax;

import cn.hutool.json.JSONUtil;
import com.fenxi365.api.Fenxi365Client;
import com.fenxi365.api.Result;
import com.fenxi365.open.model.dto.*;
import com.fenxi365.open.model.vo.*;

/**
 *  登录业务-自持模式-单账号
 *  <p>Creation: 2025年06月18日</p>
 * @version 1.0
 * @author ____′↘夏悸
 */
public class LoginServer {
    private final Fenxi365Client client;

    /**
     * 构建
     * @param fenxi365Client 客户端
     */
    public LoginServer(Fenxi365Client fenxi365Client) {
        this.client = fenxi365Client;
    }

    /**
     * 登录税局发送短信验证码 <br/>
     * 该接口用于36个地区新版电子税局登录和新版电票平台登录。如税局登录需要短信验证码登录，则触发短信发送。 注：广东、浙江、需要短信验证码，该接口可直接登录。
     */
    public Result<EtaxCookieDto> etaxcookie(EtaxCookieVo etaxCookieVo) {
        return this.client.execute("/api/tax/login/ext/etaxcookie", JSONUtil.toJsonStr(etaxCookieVo), () -> EtaxCookieDto.class);
    }

    /**
     * 登录税局上传短信验证码 <br/>
     * 该接口用于36个地区税局的新版登录，同登录税局发送短信验证码接口成对出现。 <br/>
     * 1、同一个区域同一个自然人只能存在一个有效登录，同一个自然人在同一个税号下的不同身份也是无法同时登录的 <br/>
     * 2、调用这个接口之前先调用 登录税局发送短信验证码 接口发送短信验证码
     */
    public Result<EtaxPushSmsDto> etaxpushsms(EtaxPushSmsVo etaxPushSmsVo) {
        return this.client.execute("/api/tax/login/ext/etaxpushsms", JSONUtil.toJsonStr(etaxPushSmsVo), () -> EtaxPushSmsDto.class);
    }

    /**
     * 校验税局缓存是否有效 <br/>
     * 该接口用于36个地区的异步登录成功后，缓存的dppt cookie是否有效。 <br/>
     * 返回data为true或false，表示修改成功或失败
     */
    public Result<Boolean> checkAsyncLoginSjCache(CheckAsyncLoginSjCacheVo checkAsyncLoginSjCacheVo) {
        return this.client.execute("/api/tax/login/ext/checkAsyncLoginSjCache", JSONUtil.toJsonStr(checkAsyncLoginSjCacheVo), () -> Boolean.class);
    }

    /**
     * 校验税务APP是否可快速登录 <br/>
     * 该接口用于检查APP账户是否可以快速登录（是否需要发送短信）
     */
    public Result<Boolean> checkAppCache(CheckAsyncLoginSjCacheVo checkAsyncLoginSjCacheVo) {
        return this.client.execute("/api/tax/login/ext/checkAppCache", JSONUtil.toJsonStr(checkAsyncLoginSjCacheVo), () -> Boolean.class);
    }

    /**
     * 多账号上传Cookie接口 <br/>
     * 用于用户自行登录获取税局Cookie后，通过该接口直接上传税局有效Cookie进行登录。 <br/>
     * 2,3 类型只支持办税员管理业务
     */
    public Result<UploadTokenByAccountIdDto> uploadTokenByAccountId(UploadTokenByAccountIdVo uploadTokenByAccountIdVo) {
        return this.client.execute("/api/tax/login/ext/uploadTokenByAccountId", JSONUtil.toJsonStr(uploadTokenByAccountIdVo), () -> UploadTokenByAccountIdDto.class);
    }

    /**
     * 单账号上传Cookie接口 <br/>
     * 用于用户自行登录获取税局Cookie后，通过该接口直接上传税局有效Cookie进行登录。
     */
    public Result<UploadTokenDto> uploadToken(UploadTokenVo uploadTokenVo) {
        return this.client.execute("/api/tax/login/ext/uploadToken", JSONUtil.toJsonStr(uploadTokenVo), () -> UploadTokenDto.class);
    }

    /**
     * 登出 <br/>
     * 该接口用登出
     */
    public Result<Boolean> loginOut(LoginOutVo loginOutVo) {
        return this.client.execute("/api/tax/login/ext/loginOut", JSONUtil.toJsonStr(loginOutVo), () -> Boolean.class);
    }


    /**
     * 获取登录税局二维码 <br/>
     * 该接口用于获取登录税局二维码，用户可以通过扫描该二维码进行税局登录。
     */
    public Result<ScanGetQrDto> getScanLoginQrCode(ScanGetQrVo scanGetQrVo) {
        return this.client.execute("/api/tax/login/scanLogin/dpptCookie", JSONUtil.toJsonStr(scanGetQrVo), () -> ScanGetQrDto.class);
    }

    /**
     * 扫二维码登录税局 <br/>
     * 手机扫码登录税局二维码接口获取到的二维码，进行税局登录。
     */
    public Result<QrcodeLoginDto> scanQrCodeLogin(QrcodeLoginVo qrcodeLoginVo) {
        return this.client.execute("/api/tax/login/scanLogin/qrcodeLogin", JSONUtil.toJsonStr(qrcodeLoginVo), () -> QrcodeLoginDto.class);
    }
}
