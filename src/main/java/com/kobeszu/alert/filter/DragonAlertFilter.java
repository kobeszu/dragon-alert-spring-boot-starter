package com.kobeszu.alert.filter;

import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.kobeszu.alert.current.RobotAlarmThreadPool;
import com.kobeszu.alert.chain.AlarmMsgHandler;
import org.springframework.core.annotation.Order;

import java.util.*;

/**
 * @author kobeszu@163.com
 * @date 2021-04-21 16:12
 */
public class DragonAlertFilter extends Filter<ILoggingEvent> {

    private static final List<AlarmMsgHandler> handleChainList = new ArrayList<>();

    public static void registerHandler(AlarmMsgHandler msgHandler) {
        handleChainList.add(msgHandler);
        Collections.sort(handleChainList, Comparator.comparing(e -> e.getClass().getDeclaredAnnotation(Order.class).value()));
    }

    public static void unregisterHandler(AlarmMsgHandler alarmMsgStrategy) {
        handleChainList.remove(alarmMsgStrategy);
    }

    @Override
    public FilterReply decide(ILoggingEvent iLoggingEvent) {
        if(RobotAlarmThreadPool.getPool() == null) {
            return FilterReply.NEUTRAL;
        }

        RobotAlarmThreadPool.getPool().execute(() -> {
            for (AlarmMsgHandler handler : handleChainList) {
                if (!handler.accept(iLoggingEvent)) {
                    break;
                }
            }
        });
        return FilterReply.NEUTRAL;
    }
}
