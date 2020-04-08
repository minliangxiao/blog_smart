package club.huangliang.blog_smart.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice/*这个注解可以拦截所有的@Controller*/
public class ControllerExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());//获取日志对象

    @ExceptionHandler(Exception.class)/*这个注解表示这个方法是处理异常的参数为处理异常的等级*/
    public ModelAndView exceptionHander(HttpServletRequest request, Exception e) throws Exception {
        logger.error("request URL : {},Exception : {}", request.getRequestURL(), e);//日志记录
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("url", request.getRequestURL());
        mv.addObject("exception", e);
        mv.setViewName("error/error");
        return mv;
    }
}
