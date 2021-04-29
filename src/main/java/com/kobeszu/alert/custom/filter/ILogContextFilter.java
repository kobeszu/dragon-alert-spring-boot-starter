package com.kobeszu.alert.custom.filter;

import com.kobeszu.alert.context.LogContextVO;

/**
 * @author kobeszu@163.com
 * @date 2021-04-28 20:37
 */
public interface ILogContextFilter {

    /**
     *
     * @param logContextVO
     * @return true: 接受此日志事件 false: 拒绝
     */
    boolean accept(LogContextVO logContextVO);
}
