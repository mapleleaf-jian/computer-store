package ms.store.config;

import ms.store.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * 处理拦截器的注册
 * @author maple
 * @create 2022-04-20 22:31
 */
@Configuration
public class LoginInterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        HandlerInterceptor interceptor = new LoginInterceptor();
        List<String> patterns = Arrays.asList("/css/**", "/bootstrap3/**", "/images/**", "/js/**",
                "/web/product.html", "/web/index.html", "/web/login.html", "/web/register.html",
                "/users/login", "/users/reg", "/districts/**", "/products/**");

        registry.addInterceptor(interceptor)
                .addPathPatterns("/**") //拦截的请求
                .excludePathPatterns(patterns); //放行的请求
    }
}
