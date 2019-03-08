package com.crsm.maker.Exception;

import com.crsm.maker.base.BaseController;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

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
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Map errorHandler(Exception ex){
        Map map=new HashMap();
        map.put("code",100);
        map.put("msg",ex.getMessage());
        return map;
    }

    /**
     * shiro 异常处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = ShiroException.class)
    public ModelAndView AuthorizationException(ShiroException ex){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        modelAndView.setViewName("error");
        modelAndView.addObject("errorMsg",ex.getMessage());
        return modelAndView;
    }

}
