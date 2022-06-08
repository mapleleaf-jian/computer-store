package ms.store.mapper;

import ms.store.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

/**
 * @author maple
 * @create 2022-04-24 13:40
 */
@SpringBootTest
public class ProductMapperTests {
    @Autowired
    private ProductMapper productMapper;

    @Test
    void findHotListTest() {
        List<Product> hotList = productMapper.findHotList();
        hotList.forEach(System.out::println);
    }

    @Test
    void findLatestListTest() {
        productMapper.findLatestList().forEach(System.out::println);
    }

    @Test
    void findProductByIdTest() {
        System.out.println(productMapper.findProductById(10000001));
    }

    @Test
    void updateNumByPidTest() {
        System.out.println(productMapper.updateNumByPid(950, "admin", new Date(), 1111));
    }
}
