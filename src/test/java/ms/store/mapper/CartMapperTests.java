package ms.store.mapper;

import ms.store.entity.Cart;
import ms.store.vo.CartVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

/**
 * @author maple
 * @create 2022-04-24 17:10
 */
@SpringBootTest
public class CartMapperTests {
    @Autowired
    private CartMapper cartMapper;

    @Test
    void insertCartTest() {
        Cart cart= new Cart();
        cart.setCid(1);
        cart.setPid(25);
        cart.setUid(22);
        Integer rows = cartMapper.insertCart(cart);
        System.out.println("rows = " + rows);
    }

    @Test
    void updateNumByCidTest() {
        Integer rows = cartMapper.updateNumByCid(1, 12, "admin", new Date());
        System.out.println("rows = " + rows);
    }

    @Test
    void findByUidAndPidTest() {
        Cart cart = cartMapper.findByUidAndPid(22, 25);
        System.out.println("cart = " + cart);
    }

    @Test
    void findVOCartsByUidTest() {
        List<CartVO> list = cartMapper.findVOCartsByUid(22, "%el%");
        list.forEach(System.out::println);
    }

    @Test
    void findByCidTest() {
        Cart cart = cartMapper.findByCid(3);
        System.out.println("cart = " + cart);
    }

    @Test
    void findVOCartByCidTest() {
        cartMapper.findVOCartByCid(new Integer[]{1, 2, 3}).forEach(System.out::println);
    }

    @Test
    void deleteCartByCidTest() {
        System.out.println(cartMapper.deleteCartByCid(9));
    }

    @Test
    void deleteCartsByCidsTest() {
        Integer rows = cartMapper.deleteCartsByCids(new Integer[]{24, 25, 26});
        System.out.println("rows = " + rows);
    }
}
