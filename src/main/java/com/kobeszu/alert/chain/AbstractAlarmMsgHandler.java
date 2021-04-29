package com.kobeszu.alert.chain;

import com.kobeszu.alert.filter.DragonAlertFilter;

/**
 * @author kobeszu@163.com
 * @date 2021-04-21 18:08
 */
public abstract class AbstractAlarmMsgHandler implements AlarmMsgHandler {

    public void register() {
        DragonAlertFilter.registerHandler(this);
    }
}
