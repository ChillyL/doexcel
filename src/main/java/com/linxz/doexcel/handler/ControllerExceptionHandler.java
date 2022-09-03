package com.linxz.doexcel.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @auther ChillyLin
 * @date 2020/6/17
 * 拦截异常
 */
//拦截所有标注有Controller的控制器
@ControllerAdvice
public class ControllerExceptionHandler {

    //    获取日志
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //    页面输出后台信息
    @ExceptionHandler(Exception.class)  //标识这个方法用于异常处理，拦截所有Exception
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        logger.info("Hello !!  你又出错了 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//      “{}”是占位符，传值入
        logger.error("Requst URL : {} , Exception : {}", request.getRequestURL(), e);

//        查看出现的错误是否有注解指定状态，将有注解@ResponseStatus的自定义异常抛出
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("url", request.getRequestURL());
        mv.addObject("exception", e);
        mv.setViewName("error/error");   //传入页面
        return mv;
    }

    @ExceptionHandler(MultipartException.class)
    public String handleError1(MultipartException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", e.getCause().getMessage());
        return "redirect:/admin/uploadStatus";
    }
}
