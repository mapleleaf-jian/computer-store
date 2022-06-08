package ms.store.service;

import com.github.pagehelper.PageInfo;
import ms.store.entity.Order;
import ms.store.entity.OrderItem;
import ms.store.mapper.OrderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author maple
 * @create 2022-04-26 21:38
 */
@SpringBootTest
public class OrderServiceTests {
    @Autowired
    private IOrderService orderService;

    @Test
    void createOrderByCidsTest() {
        Order order = orderService.createOrderByCids(22, "ming", 8, new Integer[]{3, 4, 5});
        System.out.println("order = " + order);
    }

    @Test
    void getOrdersByUidTest() {
        orderService.getOrdersByUid(22, null, "dell").forEach(System.out::println);
    }

    @Test
    void getOrderItemsByOidTest() {
        orderService.getOrderItemsByOid(35).forEach(System.out::println);
    }

    @Test
    void deleteOrderAndItemsByOidTest() {
        orderService.deleteOrderAndItemsByOid(3, 22);
    }

    @Test
    void createOrderByPidTest() {
        Order order = orderService.createOrderByPid(22, "admin", 8, 10000001, 3);
        System.out.println("order = " + order);
    }

    @Test
    void getOrderByOidAllInfoTest() {
        System.out.println(orderService.getOrderByOidAllInfo(22, 22));
    }

    @Test
    void getOrderByOidTest() {
        Order order = orderService.getOrderByOid(30, 22);
        System.out.println("order = " + order);
    }

    @Test
    void getAllByUidAndPageInfoTest() {
        PageInfo<Order> pageInfo = orderService.getAllByUidAndPageInfo(2, 3, 22, null, "dell");
        pageInfo.getList().forEach(System.out::println);
    }

    @Test
    void updateStatusAndPayTimeTest() {
        orderService.updateStatusAndPayTime(16, "admin", 1);
    }
}
