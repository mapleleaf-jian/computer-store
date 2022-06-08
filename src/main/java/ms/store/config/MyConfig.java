package ms.store.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;

/**
 * @author maple
 * @create 2022-04-22 14:26
 */
//@Configuration
public class MyConfig {
    //@Bean
//    public MultipartConfigElement multipartConfigElement() {
//        // 创建一个配置的工厂类对象
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//
//        // 设置需要创建的对象的相关信息
//        factory.setMaxFileSize(DataSize.of(10, DataUnit.MEGABYTES));
//        factory.setMaxRequestSize(DataSize.of(15, DataUnit.MEGABYTES));
//
//        // 使用工厂类来创建MultipartConfigElement对象
//        return factory.createMultipartConfig();
//    }
}
