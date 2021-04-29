package com.kobeszu.alert.utils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;
import com.kobeszu.alert.context.LogContextVO;

/**
 * @author laizhiqiang
 * @date 2021-04-29 9:32
 */
public class CommonUtil {

    private CommonUtil() {
    }

    public static LogContextVO convert(ILoggingEvent event) {
        if((event instanceof LoggingEvent)) {
            IThrowableProxy throwableProxy = event.getThrowableProxy();
            LogContextVO logContextVO = new LogContextVO();
            logContextVO.setClassName(throwableProxy.getClassName());
            logContextVO.setMessage(throwableProxy.getMessage());
            logContextVO.setThrowable(((ThrowableProxy)throwableProxy).getThrowable());
            return logContextVO;
        }
        return null;
    }
}
