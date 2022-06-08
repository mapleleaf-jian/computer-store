package ms.store;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// MapperScan注解指定当前项目中的Mapper接口路径的位置，在项目启动的时候会自动加载所有的接口
@MapperScan("ms.store.mapper")
@SpringBootApplication
public class MystoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(MystoreApplication.class, args);
    }

}
