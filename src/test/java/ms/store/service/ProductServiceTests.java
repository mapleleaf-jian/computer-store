package ms.store.service;

import com.github.pagehelper.PageInfo;
import ms.store.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author maple
 * @create 2022-04-24 13:49
 */
@SpringBootTest
public class ProductServiceTests {
    @Autowired
    private IProductService productService;

    @Test
    void getHotListTest() {
        List<Product> hotList = productService.getHotList();
        for (Product product : hotList) {
            System.out.println(product);
        }
    }

    @Test
    void getHotListByPageTest() {
        PageInfo<Product> hotList = productService.getHotListByPage(1, 4);
        hotList.getList().forEach(System.out::println);
    }

    @Test
    void getLatestListTest() {
        productService.getLatestList().forEach(System.out::println);
    }

    @Test
    void getLatestListByPageTest() {
        PageInfo<Product> hotList = productService.getLatestListByPage(1, 4);
        hotList.getList().forEach(System.out::println);
    }

    @Test
    void getProductByIdTest() {
        System.out.println(productService.getProductById(10000001));
    }

    @Test
    void getProductByIdSetNumTest() {
        System.out.println(productService.getProductByIdSetNum(10000001, 3));
    }

    @Test
    void updateNumByOidTest() {
        productService.updateNumByOid(23, "admin");
    }
}
