//package com.kobeszu.alert.custom.filter;
//
//import com.kobeszu.alert.context.LogContextVO;
//import org.springframework.web.servlet.NoHandlerFoundException;
//
///**
// * @author kobeszu@163.com
// * @date 2021-04-28 20:40
// */
//public class NoHandlerFoundExceptionFilter implements ILogContextFilter{
//
//    @Override
//    public boolean accept(LogContextVO logContextVO) {
//        return !(logContextVO.getThrowable() instanceof NoHandlerFoundException);
//    }
//}
