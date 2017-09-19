package com.gakki.love.exception;

import com.gakki.love.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 林漠
 * \* Date: 2017/9/16
 * \* Time: 1:26
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@ControllerAdvice
@Slf4j
public class GakkiExceptionHandler implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception exception) {

        ModelAndView modelAndView = new ModelAndView();
        log.error("",exception);
        return setMessage(modelAndView,exception);
    }

    private static ModelAndView setMessage(ModelAndView modelAndView, Exception exception) {
        String ex = exception.getClass().getSimpleName();
        String errorMessage = exception.getMessage();
        switch (ex) {
            case "GakkiException":
                errorMessage = (StringUtils.hasText(errorMessage)) ? "系统异常,请稍后再试" : errorMessage;
                break;
            default:
                errorMessage = "系统被程序猿玩儿坏啦，看看其他东东坏没  ^_^\"";
                break;
        }
        modelAndView.addObject("errorMessage", errorMessage);
        modelAndView.setViewName("error");
        return modelAndView;
    }

}