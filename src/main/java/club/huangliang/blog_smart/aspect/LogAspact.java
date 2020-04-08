package club.huangliang.blog_smart.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component//组件扫描
public class LogAspact {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());//拿到日志记录器

    //这样子写相当于一个继承方法下面的每一个用了log()方法的都继承了execution(* club.huangliang.blog_smart.web.*(..))
    @Pointcut("execution(* club.huangliang.blog_smart.web.*.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();//获取类名和方法名
        Object[] args = joinPoint.getArgs();//获取传入目标方法的参数对象
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        //写进日志
        logger.info("Request: {}", requestLog);//{}表示一个对象


    }

    @After("log()")
    public void doAfter() {

    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterRuturn(Object result) {
        //直接将方法的返回类型写进日志
        logger.info("Result: {}", result);
    }

    public class RequestLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }


    }
}
