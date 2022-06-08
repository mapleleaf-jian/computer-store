package ms.store.service;

import com.github.pagehelper.PageInfo;
import ms.store.entity.Order;
import ms.store.entity.OrderItem;

import java.util.List;
import java.util.Map;

/**
 * @author maple
 * @create 2022-04-26 16:29
 */
public interface IOrderService {

    /**
     * 根据传入的购物车cid数组，新建一个订单信息
     * @param uid 用户id
     * @param username 用户名
     * @param aid 收货地址id
     * @param cids 购物车id数组
     * @return 订单信息
     */
    Order createOrderByCids(Integer uid, String username, Integer aid, Integer[] cids);

    /**
     * 根据传入的商品pid和商品数量num，新建一个订单信息
     * @param uid 用户uid
     * @param username 用户名
     * @param aid 地址aid
     * @param pid 商品pid
     * @param num 商品的数量
     * @return 订单信息
     */
    Order createOrderByPid(Integer uid, String username, Integer aid, Integer pid, Integer num);

    /**
     * 根根据用户uid，如果有支付状态，则查询条件加上支付状态，查询所有订单信息，根据创建时间由新到老排序
     * 可增加title为查询条件
     * @param uid 用户id
     * @param status 订单状态
     * @param title 商品title
     * @return
     */
    List<Order> getOrdersByUid(Integer uid, Integer status, String title);

    /**
     * 根据订单oid查询所有的订单项信息
     * @param oid
     * @return
     */
    List<OrderItem> getOrderItemsByOid(Integer oid);

    /**
     * 根据用户uid删除订单信息以及其下的订单项
     * @param oid 订单oid
     * @param uid 用户uid
     */
    void deleteOrderAndItemsByOid(Integer oid, Integer uid);

    /**
     * 根据订单oid和用户uid查询订单全部信息
     * @param oid 订单oid
     * @param uid 用户uid
     * @return 订单全部信息
     */
    Order getOrderByOidAllInfo(Integer oid, Integer uid);

    /**
     * 根据订单oid和用户uid查询订单信息，返回总价信息
     * @param oid 订单oid
     * @param uid 用户uid
     * @return 订单信息
     */
    Order getOrderByOid(Integer oid, Integer uid);

    /**
     * 分页查询某个用户所有的订单信息
     * @param pageNum 当前页码数，从1开始
     * @param pageSize 每页显示的数据条数
     * @param uid 用户uid
     * @param status 订单状态
     * @param title 商品title
     * @return pageHelper插件内置的分页PageBean对象
     */
     PageInfo<Order> getAllByUidAndPageInfo(Integer pageNum, Integer pageSize, Integer uid, Integer status, String title);

    /**
     * 修改某个oid的订单的状态和支付时间
     * @param oid 订单oid
     * @param username 修改人
     * @param status 状态
     */
     void updateStatusAndPayTime(Integer oid, String username, Integer status);
}
