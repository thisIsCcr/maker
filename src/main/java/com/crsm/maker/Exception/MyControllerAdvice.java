package com.crsm.maker.Exception;

import com.crsm.maker.base.BaseController;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 简易的异常处理，Controller
 */
@ControllerAdvice
public class MyControllerAdvice extends BaseController {

    /**
     * 全局异常处理
     * @param ex
     * @return
     */
    @SuppressWarnings("all")
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ModelAndView errorHandler(Exception ex){
        ex.printStackTrace();
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        modelAndView.setViewName("error");
        modelAndView.addObject("errorMsg",ex.getMessage());
        return modelAndView;
    }

    /**
     * shiro 异常处理
     * @param ex
     * @return
     */
    @SuppressWarnings("all")
    @ResponseBody
    @ExceptionHandler(value = ShiroException.class)
    public ModelAndView AuthorizationException(ShiroException ex){
        ex.printStackTrace();
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        modelAndView.setViewName("error");
        modelAndView.addObject("errorMsg",ex.getMessage());
        return modelAndView;
    }

}
