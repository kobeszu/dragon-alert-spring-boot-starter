package com.kobeszu.alert.chain;

import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * @author laizhiqiang
 * @date 2021-04-21 16:56
 */
public interface AlarmMsgHandler {

    boolean accept(ILoggingEvent event);
}
