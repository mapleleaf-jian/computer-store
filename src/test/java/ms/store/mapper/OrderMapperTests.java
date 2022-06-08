package ms.store.mapper;

import ms.store.entity.Order;
import ms.store.entity.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

/**
 * @author maple
 * @create 2022-04-26 16:13
 */
@SpringBootTest
public class OrderMapperTests {
    @Autowired
    private OrderMapper orderMapper;

    @Test
    void insertOrderTest() {
        Order order = new Order();
        order.setOrderTime(new Date());
        order.setUid(22);
        order.setRecvName("小红");
        order.setRecvProvince("江苏省");
        Integer rows = orderMapper.insertOrder(order);
        System.out.println("rows = " + rows);
    }

    @Test
    void insertOrderItemTest() {
        OrderItem orderItem = new OrderItem();
        orderItem.setImage("path");
        orderItem.setOid(1);
        orderItem.setPid(10000001);
        orderItem.setTitle("beautiful");
        Integer rows = orderMapper.insertOrderItem(orderItem);
        System.out.println("rows = " + rows);
    }

    @Test
    void findOrderByOidTest() {
        System.out.println(orderMapper.findOrderByOid(1));
    }

    @Test
    void findOrdersByUidTest() {
        orderMapper.findOrdersByUid(22, null).forEach(System.out::println);
    }

    @Test
    void findOrderItemsByOidTest() {
        orderMapper.findOrderItemsByOid(3).forEach(System.out::println);
    }

    @Test
    void delOrderByOidTest() {
        Integer rows = orderMapper.delOrderByOid(2);
        System.out.println("rows = " + rows);
    }

    @Test
    void delOrderItemByOidTest() {
        Integer rows = orderMapper.delOrderItemByOid(2);
        System.out.println("rows = " + rows);
    }

    @Test
    void updateStatusAndPayTimeByOidTest() {
        Date date = new Date();
        Integer rows = orderMapper.updateStatusAndPayTimeByOid(15, 1, date, date, "admin");
        System.out.println("rows = " + rows);
    }

    @Test
    void findOrdersLikeTitleTest() {
        List<Order> list = orderMapper.findOrdersLikeTitle(22, null, "%dell%");
        System.out.println(list.size());
        list.forEach(System.out::println);
    }
}
