package com.kobeszu.alert.chain;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import com.kobeszu.alert.channel.robot.IRobotAlarm;
import com.kobeszu.alert.utils.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @author kobeszu@163.com
 * @date 2021-04-21 18:17
 */
@Order(10)
public class RobotAlarmMsgHandler extends AbstractAlarmMsgHandler implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private String applicationName;

    private String ip;

    private Map<String, IRobotAlarm> robotAlarmMap;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public RobotAlarmMsgHandler() {
        ip = IpUtil.getLocalHost();

    }

    @PostConstruct
    public void init() {
        robotAlarmMap = applicationContext.getBeansOfType(IRobotAlarm.class);
        applicationName = applicationContext.getEnvironment().getProperty("spring.application.name");
    }

    private String getApplicationName() {
        return applicationName;
    }

    @Override
    public boolean accept(ILoggingEvent event) {
        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append(LocalDateTime.now().format(dateTimeFormatter)).append(" [")
                .append(getApplicationName()).append("]").append("[").append(ip).append("]");
        Map<String, String> mdcMap = MDC.getCopyOfContextMap();
        if (mdcMap != null) {
            mdcMap.forEach((k, v) -> {
                //根据需要，过滤一些信息
                if(!"local_ip".equals(k) && !"remote_ip".equals(k)) {
                    logBuilder.append("[").append(k).append(":").append(v).append("]");
                }
            });
        }

        IThrowableProxy throwableProxy = event.getThrowableProxy();
        if (throwableProxy != null) {
            IThrowableProxy cause = throwableProxy.getCause();
            if (cause != null) {
                StackTraceElementProxy[] stackTraceElement = cause.getStackTraceElementProxyArray();
                if (stackTraceElement != null) {
                    logBuilder.append(stackTraceElement[0].getSTEAsString()).append(" ")
                            .append(stackTraceElement[1].getSTEAsString()).append(" ")
                            .append(stackTraceElement[2].getSTEAsString()).append(" ");
                }

            }
            String codeLocation = throwableProxy.getStackTraceElementProxyArray()[0].getSTEAsString();
            logBuilder.append(throwableProxy.getClassName()).append(codeLocation).append(",");
        } else {
            logBuilder.append(event.getLoggerName()).append(" ");
        }
        logBuilder.append(event.getFormattedMessage());

        //do alarm
        robotAlarmMap.forEach((k, v) -> v.alarm(logBuilder.toString()));

        return true;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
