package com.fenxi365.api.exception;

/**
 *  com.fenxi365.api.exception
 *  <p>Creation: 2025年06月17日</p>
 * @version 1.0
 * @author ____′↘夏悸
 */
public class Fenxi365ClientException extends RuntimeException {
    /**
     * 构建
     * @param message 消息
     */
    public Fenxi365ClientException(String message) {
        super(message);
    }

    /**
     * 构建
     * @param message 消息
     * @param cause 异常
     */
    public Fenxi365ClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
