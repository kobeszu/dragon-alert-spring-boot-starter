package com.kobeszu.alert.context;

import java.io.Serializable;

/**
 * 提供给外部应用过滤的上下文
 * @author laizhiqiang
 * @date 2021-04-28 20:28
 */
public class LogContextVO implements Serializable {

    //异常
    private Throwable throwable;

    //异常类名
    private String className;

    //错误信息
    private String message;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
