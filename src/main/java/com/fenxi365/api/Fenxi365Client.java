package com.fenxi365.api;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fenxi365.api.exception.Fenxi365ClientException;
import com.fenxi365.api.open.OpenServer;
import com.fenxi365.api.tax.*;
import com.fenxi365.open.model.enums.LoginType;
import com.fenxi365.open.model.enums.ServiceType;
import lombok.NonNull;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 *  Fenxi365Client
 *  <p>Creation: 2025年06月16日</p>
 * @version 1.0
 * @author ____′↘夏悸
 */
public class Fenxi365Client implements Fenxi365 {
    private final String endpoint;
    private final String appId;
    private final String secretKey;

    private final OkHttpClient httpClient;
    private final ThreadLocal<LoginType> loginType = new ThreadLocal<>();
    private final ThreadLocal<ServiceType> service = new ThreadLocal<>();
    private final ThreadLocal<Map<String, String>> headers = new ThreadLocal<>();

    private OpenServer openServer;
    private TaxOrgServer taxOrgServer;
    private QdfpServer qdfpServer;
    private MultiaccServer multiaccServer;
    private LoginServer loginServer;
    private GjServer gjServer;
    private InvoiceRedServer invoiceRedServer;

    /**
     * 构建
     * @param endpoint  服务地址
     * @param appId     应用ID
     * @param secretKey 密钥
     */
    public Fenxi365Client(@NonNull String endpoint, @NonNull String appId, @NonNull String secretKey) {
        this.endpoint = endpoint;
        this.appId = appId;
        this.secretKey = secretKey;
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(Duration.ofSeconds(120))
                .callTimeout(Duration.ofSeconds(120))
                .eventListener(new EventListener() {
                    private final Logger log = Logger.getLogger(Fenxi365Client.class.getName());
                    private final ThreadLocal<Long> startTime = new ThreadLocal<>();

                    @Override
                    public void callStart(@NotNull Call call) {
                        startTime.set(System.currentTimeMillis());
                    }

                    @Override
                    public void callEnd(@NotNull Call call) {
                        long endTime = System.currentTimeMillis();
                        long duration = endTime - startTime.get();
                        log.info("请求" + call.request().url() + "耗时：" + duration + "ms");
                    }
                }).build();
        init();
    }

    /**
     * 构建
     * @param endpoint   服务地址
     * @param appId      应用ID
     * @param secretKey  密钥
     * @param httpClient http客户端
     */
    public Fenxi365Client(@NonNull String endpoint, @NonNull String appId, @NonNull String secretKey, @NonNull OkHttpClient httpClient) {
        this.endpoint = endpoint;
        this.appId = appId;
        this.secretKey = secretKey;
        this.httpClient = httpClient;
        init();
    }

    private void init() {
        openServer = new OpenServer(this);
        taxOrgServer = new TaxOrgServer(this);
        qdfpServer = new QdfpServer(this);
        multiaccServer = new MultiaccServer(this);
        loginServer = new LoginServer(this);
        gjServer = new GjServer(this);
        invoiceRedServer = new InvoiceRedServer(this);
    }

    @Override
    public Fenxi365 withLoginType(LoginType loginType) {
        this.loginType.set(loginType);
        return this;
    }

    @Override
    public Fenxi365 withService(ServiceType service) {
        this.service.set(service);
        return this;
    }

    @Override
    public Fenxi365 withLoginTypeAndService(LoginType loginType, ServiceType service) {
        this.loginType.set(loginType);
        this.service.set(service);
        return this;
    }

    @Override
    public Fenxi365 withHeaders(Map<String, String> headers) {
        this.headers.set(headers);
        return this;
    }


    /**
     * 获取企业服务
     * @return 企业服务
     */
    @Override
    public OpenServer getOpenServer() {
        return openServer;
    }

    @Override
    public TaxOrgServer getTaxOrgServer() {
        return taxOrgServer;
    }

    @Override
    public QdfpServer getQdfpServer() {
        return qdfpServer;
    }

    @Override
    public MultiaccServer getMultiaccServer() {
        return multiaccServer;
    }

    @Override
    public LoginServer getLoginServer() {
        return loginServer;
    }

    @Override
    public GjServer getGjServer() {
        return gjServer;
    }

    @Override
    public InvoiceRedServer getInvoiceRedServer() {
        return invoiceRedServer;
    }

    /**
     * 执行
     * @param api 接口
     * @param function 返回值处理
     * @return 返回值
     * @param <T>
     */
    public <T> Result<T> execute(String api, Function<JSONObject, T> function) {
        return execute(api, null, function, null);
    }


    /**
     * 执行
     * @param api 接口
     * @param resultType 返回值类型
     * @return 返回值
     * @param <T>
     */
    public <T> Result<T> execute(String api, Supplier<Class<T>> resultType) {
        return execute(api, null, null, resultType);
    }

    /**
     * 执行
     * @param api 接口
     * @param jsonBody 请求体
     * @param resultType 返回值类型
     * @return 返回值
     * @param <T>
     */
    public <T> Result<T> execute(String api, String jsonBody, Function<JSONObject, T> resultType) {
        return execute(api, jsonBody, resultType, null);
    }


    /**
     * 执行
     * @param api 接口
     * @param jsonBody 请求体
     * @param resultType 返回值类型
     * @return 返回值
     * @param <T>
     */
    public <T> Result<T> execute(String api, String jsonBody, Supplier<Class<T>> resultType) {
        return execute(api, jsonBody, null, resultType);
    }

    /**
     * 执行
     * @param api
     * @param jsonBody 请求体
     * @param function 返回值处理
     * @param supplier 返回值类型
     * @return 返回值
     * @param <T>
     */
    private <T> Result<T> execute(String api, String jsonBody, Function<JSONObject, T> function, Supplier<Class<T>> supplier) {
        if (StrUtil.isBlank(jsonBody)) {
            jsonBody = "";
        }
        // 构造请求体（以 JSON 为例）
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(jsonBody, JSON);

        String timestamp = System.currentTimeMillis() + "";
        String nonce = randomString(8);

        Request.Builder builder = new Request.Builder()
                .url(getEndpoint() + (api.startsWith("/") ? api : "/" + api))
                .header("referer", "https://open.admin.fenxi365.com")
                .header("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36 Edg/128.0.0.0 fenxi365 sdk")
                .header("appId", appId)
                .header("timestamp", timestamp)
                .header("nonce", nonce)
                .header("signature", signature(timestamp, nonce, jsonBody));

        if (headers.get() != null) {
            headers.get().forEach(builder::header);
        }

        if (loginType.get() != null) {
            builder.header("loginType", loginType.get().name().toLowerCase());
        }

        if (service.get() != null) {
            builder.header("service", service.get().name());
        }

        // 写个post请求
        Request request = builder.post(body).build();
        try (okhttp3.Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new Fenxi365ClientException("请求错误,Response Code：" + response.code());
            }
            if (response.body() == null) {
                throw new Fenxi365ClientException("请求错误,Response Body is null");
            }
            JSONObject entries = JSONUtil.parseObj(response.body().string());
            Result<?> bean = entries.toBean(Result.class);
            if (bean.isSuccess()) {
                if (function != null) {
                    return Result.ok(function.apply(entries));
                } else {
                    return Result.ok(entries.get("data", supplier.get()));
                }
            } else {
                return Result.fail(bean.getCode(), bean.getMsg());
            }
        } catch (Exception e) {
            throw new Fenxi365ClientException("Fenxi365 Open Sdk请求程序错误," + e.getMessage() + ",URL：" + request.url(), e);
        }
    }

    private String getEndpoint() {
        if (StrUtil.isBlank(endpoint)) {
            throw new Fenxi365ClientException("服务地址不能为空");
        } else {
            if (endpoint.endsWith("/"))
                return endpoint.substring(0, endpoint.length() - 1);
            return endpoint;
        }
    }

    /**
     * 签名
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param reqBodyJson 请求体
     * @return 签名
     */
    private String signature(String timestamp, String nonce, String reqBodyJson) {
        return md5Hex(appId + secretKey + timestamp + nonce + reqBodyJson);
    }

    /**
     * 计算MD5
     * @param str 字符串
     * @return MD5
     */
    private String md5Hex(String str) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(str.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : array) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString().toLowerCase();
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 calculation failed", e);
        }
    }

    /**
     * 生成随机字符串
     * @param len 长度
     * @return 随机字符串
     */
    private String randomString(int len) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(len);
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public static Fenxi365ClientBuilder builder() {
        return new Fenxi365ClientBuilder();
    }


    /**
     *  com.fenxi365.api
     * @version 1.0
     * @author ____′↘夏悸
     */
    public static final class Fenxi365ClientBuilder {
        private String endpoint;
        private String appId;
        private String secretKey;
        private OkHttpClient httpClient;

        /**
         * 服务地址
         * @param endpoint 服务地址
         * @return Fenxi365ClientBuilderImpl
         */
        public Fenxi365ClientBuilder endpoint(String endpoint) {
            this.endpoint = endpoint;
            return this;
        }

        /**
         * 应用ID
         * @param appId 应用ID
         * @return Fenxi365ClientBuilderImpl
         */
        public Fenxi365ClientBuilder appId(String appId) {
            this.appId = appId;
            return this;
        }

        /**
         * 密钥
         * @param secretKey 密钥
         * @return Fenxi365ClientBuilderImpl
         */
        public Fenxi365ClientBuilder secretKey(String secretKey) {
            this.secretKey = secretKey;
            return this;
        }

        /**
         * http客户端
         * @param httpClient http客户端
         * @return Fenxi365ClientBuilderImpl
         */
        public Fenxi365ClientBuilder httpClient(OkHttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        /**
         * 构建
         * @return Fenxi365
         */
        public Fenxi365 build() {
            if (httpClient == null) {
                return new Fenxi365Client(endpoint, appId, secretKey);
            }
            return new Fenxi365Client(endpoint, appId, secretKey, httpClient);
        }
    }
}
