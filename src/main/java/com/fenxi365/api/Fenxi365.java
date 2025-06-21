package com.fenxi365.api;


import com.fenxi365.api.open.OpenServer;
import com.fenxi365.api.tax.*;
import com.fenxi365.open.model.enums.LoginType;
import com.fenxi365.open.model.enums.ServiceType;

import java.util.Map;

/**
 *  com.fenxi365.api
 *  <p>Creation: 2025年06月16日</p>
 * @version 1.0
 * @author ____′↘夏悸
 */
public interface Fenxi365 {

    /**
     * 设置登录类型
     * @param loginType 类型
     * @return this
     */
    Fenxi365 withLoginType(LoginType loginType);

    /**
     * 设置服务编码
     * @param serviceType QXY_SDKP,QXY_XQYSB
     * @return this
     */
    Fenxi365 withService(ServiceType serviceType);

    /**
     * 设置登录类型和服务编码
     * @param loginType 登录类型
     * @param serviceType 服务编码
     * @return this
     */
    Fenxi365 withLoginTypeAndService(LoginType loginType, ServiceType serviceType);

    /**
     * 设置请求头
     * @param headers 请求头
     * @return this
     */
    Fenxi365 withHeaders(Map<String, String> headers);

    /**
     * 获取企业服务
     * @return 服务对象
     */
    OpenServer getOpenServer();

    /**
     * 税务管理相关接口
     * @return 服务对象
     */
    TaxOrgServer getTaxOrgServer();

    /**
     * 数电开票相关接口
     * @return 服务对象
     */
    QdfpServer getQdfpServer();

    /**
     * 登录业务-自持模式-多账号
     * @return 服务对象
     */
    MultiaccServer getMultiaccServer();

    /**
     * 登录业务-自持模式-单账号
     * @return 服务对象
     */
    LoginServer getLoginServer();

    /**
     * 发票归集相关接口
     * @return 服务对象
     */
    GjServer getGjServer();

    /**
     * 红字发票相关接口
     * @return 服务对象
     */
    InvoiceRedServer getInvoiceRedServer();
}
