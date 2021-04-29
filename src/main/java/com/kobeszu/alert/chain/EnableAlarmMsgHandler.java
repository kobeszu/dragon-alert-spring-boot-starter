package com.kobeszu.alert.chain;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.kobeszu.alert.constant.RobotConstant;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

/**
 * 解决运行中配置变更，不重启服务，直接改变行为
 * @author kobeszu@163.com
 * @date 2021-04-21 17:42
 */

@Order(1)
public class EnableAlarmMsgHandler extends AbstractAlarmMsgHandler implements ApplicationContextAware {


    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public boolean accept(ILoggingEvent event) {
        Environment environment = applicationContext.getEnvironment();
        return Boolean.parseBoolean(environment.getProperty(RobotConstant.ALERT_ENABLED_KEY));
    }
}
