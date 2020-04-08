package club.huangliang.blog_smart.interceptor;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration//配置类注解
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")//拦截admin下面的所有页面
                .excludePathPatterns("/admin")//放行/admin页面
                .excludePathPatterns("/admin/login");//放行/admin/login页面
    }
}
