package com.kobeszu.alert.chain;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.springframework.core.annotation.Order;

/**
 * 只告警ERROR日志
 * @author kobeszu@163.com
 * @date 2021-04-21 17:59
 */
@Order(-1)
public class LevelAlarmMsgHandler extends AbstractAlarmMsgHandler {

    @Override
    public boolean accept(ILoggingEvent event) {
        return event.getLevel().levelInt == Level.ERROR_INT;
    }
}
