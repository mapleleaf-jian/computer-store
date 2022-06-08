package ms.store.service;

import ms.store.vo.CartVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author maple
 * @create 2022-04-24 19:15
 */
@SpringBootTest
public class CartServiceTests {
    @Autowired
    private ICartService cartService;

    @Test
    void addToCartTest() {
        // Integer rows = cartService.addToCart(22, 10000012, 11, "admin1");
        cartService.addToCart(22, 10000001, 11, "admin1");
    }

    @Test
    void getVOCartsByUidTest() {
        List<CartVO> carts = cartService.getVOCartsByUid(22, "Dell");
        carts.forEach(System.out::println);
    }

    @Test
    void addNumByCidTest() {
        Integer num = cartService.addNumByCid(3, 22, "admin");
        System.out.println("num = " + num);
    }

    @Test
    void reduceNumByCidTest() {
        Integer num = cartService.reduceNumByCid(6, 22, "admin");
        System.out.println("num = " + num);
    }

    @Test
    void getVOCartsByCidTest() {
        cartService.getVOCartsByCid(new Integer[]{1, 2, 3}, 22).forEach(System.out::println);
    }

    @Test
    void deleteCartByCidTest() {
        cartService.deleteCartByCid(7, 22);
    }

    @Test
    void deleteCartsByCidsTest() {
        cartService.deleteCartsByCids(new Integer[]{27, 28, 29}, 44);
    }
}
