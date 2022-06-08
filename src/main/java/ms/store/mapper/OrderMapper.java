package ms.store.mapper;

import ms.store.entity.Order;
import ms.store.entity.OrderItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author maple
 * @create 2022-04-26 15:39
 */
@Repository
public interface OrderMapper {
    /**
     * 插入订单数据
     * @param order
     * @return 受影响的行数
     */
    Integer insertOrder(Order order);

    /**
     * 插入订单项的数据
     * @param orderItem
     * @return 受影响的行数
     */
    Integer insertOrderItem(OrderItem orderItem);

    /**
     * 根据oid查询订单信息
     * @param oid
     * @return
     */
    Order findOrderByOid(Integer oid);

    /**
     * 根据用户uid，如果有支付状态，则查询条件加上支付状态，查询所有订单信息，根据创建时间由新到老排序
     * @param uid 用户id
     * @param status 订单状态
     * @return
     */
    List<Order> findOrdersByUid(Integer uid, Integer status);

    /**
     * 根据oid查询订单的项 信息
     * @param oid
     * @return
     */
    List<OrderItem> findOrderItemsByOid(Integer oid);

    /**
     * 根据uid,status,title查询Order集合
     * @param uid 用户uid
     * @param status 订单状态,可以为null
     * @param title 商品简介
     * @return
     */
    List<Order> findOrdersLikeTitle(Integer uid, Integer status, String title);

    /**
     * 根据订单oid删除某个订单信息
     * @param oid 订单oid
     * @return 受影响的行数
     */
    Integer delOrderByOid(Integer oid);

    /**
     * 根据订单id删除某个订单下的所有订单项信息
     * @param oid 订单oid
     * @return 受影响的行数
     */
    Integer delOrderItemByOid(Integer oid);

    /**
     * 修改某个oid订单的状态和付款时间
     * @param oid 订单oid
     * @param date 修改日期
     * @param username 修改人
     * @return 受影响的行数
     */
    Integer updateStatusAndPayTimeByOid(Integer oid,
                                        Integer status,
                                        @Param("payTime") Date payTime,
                                        @Param("modifiedTime") Date date,
                                        @Param("modifiedUser") String username);
}
