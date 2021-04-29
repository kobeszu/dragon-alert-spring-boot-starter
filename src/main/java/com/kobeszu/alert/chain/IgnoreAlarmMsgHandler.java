/*
package com.kobeszu.alert.chain;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.kobeszu.alert.context.LogContextVO;
import com.kobeszu.alert.custom.filter.ILogContextFilter;
import com.kobeszu.alert.utils.CommonUtil;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.ServiceLoader;

*/
/**
 * 根据异常信息去匹配过滤
 * @author kobeszu@163.com
 * @date 2021-04-28 20:24
 *//*

@Order(9)
public class IgnoreAlarmMsgHandler extends AbstractAlarmMsgHandler {

    //todo optimize
    ServiceLoader<ILogContextFilter> serviceLoader;

    @PostConstruct
    public void init() {
        serviceLoader = ServiceLoader.load(ILogContextFilter.class);
    }

    @Override
    public boolean accept(ILoggingEvent event) {
        LogContextVO logContextVO = CommonUtil.convert(event);
        if(null == logContextVO) {
            return true;
        }
        Iterator<ILogContextFilter> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            ILogContextFilter contextFilter = iterator.next();
            //只要外部应用过滤掉了，就不发告警
            if(!contextFilter.accept(logContextVO)) {
                return false;
            }
        }
        return true;
    }
}
*/
