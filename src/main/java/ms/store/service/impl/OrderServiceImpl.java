package ms.store.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ms.store.entity.*;
import ms.store.mapper.OrderMapper;
import ms.store.service.IAddressService;
import ms.store.service.ICartService;
import ms.store.service.IOrderService;
import ms.store.service.IProductService;
import ms.store.service.ex.*;
import ms.store.util.LogInfo;
import ms.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author maple
 * @create 2022-04-26 20:40
 */
@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private IAddressService addressService;

    @Autowired
    private ICartService cartService;

    @Autowired
    private IProductService productService;

    @Override
    public Order createOrderByCids(Integer uid, String username, Integer aid, Integer[] cids) {
        Date date = new Date();
        Order order = createOrderInfo(aid, uid, username, date); // 封装除了 总价 之外的其他订单属性

        // 设置总价
        Long totalPrice = 0L;
        List<CartVO> cartVOs = cartService.getVOCartsByCid(cids, uid);
        // 计算商品总价
        for (CartVO cartVO : cartVOs) {
            totalPrice += cartVO.getRealPrice() * cartVO.getNum();
        }
        order.setTotalPrice(totalPrice);


        Integer rows = orderMapper.insertOrder(order);
        if (rows != 1) {
            throw new InsertException("创建订单时发生未知的异常！");
        }

        //获取oid
        Integer oid = order.getOid();

        //防止在插入后被删除导致 添加OrderItem信息 时出现异常
        Order result = orderMapper.findOrderByOid(oid);
        if (result == null) {
            throw new OrderNotFoundException("order数据不存在！");
        }

        //添加OrderItem信息
        for (CartVO cartVO : cartVOs) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOid(oid); //防止得到的oid为空，在之前应先查询一下新插入的order是否存在
            orderItem.setPid(cartVO.getPid());
            orderItem.setTitle(cartVO.getTitle());
            orderItem.setImage(cartVO.getImage());
            orderItem.setPrice(cartVO.getPrice());
            orderItem.setNum(cartVO.getNum());

            //四个日志信息
            orderItem = (OrderItem) LogInfo.setLogInfo(orderItem, date, username);

            rows = orderMapper.insertOrderItem(orderItem);
            if (rows != 1) {
                throw new InsertException("添加订单项数据时产生未知的异常！");
            }
        }

        return order;
    }

    @Override
    public Order createOrderByPid(Integer uid, String username, Integer aid, Integer pid, Integer num) {
        Date date = new Date();
        Order order = createOrderInfo(aid, uid, username, date); // 封装除了 总价 之外的其他订单属性

        //设置总价
        Product product = productService.getProductByIdSetNum(pid, num);
        if (product == null) {
            throw new ProductNotFoundException("商品信息不存在！");
        }
        Long price = product.getPrice();
        order.setTotalPrice(price * num);

        Integer rows = orderMapper.insertOrder(order);
        if (rows != 1) {
            throw new InsertException("创建订单时发生未知的异常！");
        }

        //获取oid
        Integer oid = order.getOid();

        //防止在插入后被删除导致 添加OrderItem信息 时出现异常
        Order result = orderMapper.findOrderByOid(oid);
        if (result == null) {
            throw new OrderNotFoundException("order数据不存在！");
        }

        //添加OrderItem信息，属性大多从product中获取
        OrderItem orderItem = new OrderItem();
        orderItem.setOid(oid);
        orderItem.setPid(product.getId());
        orderItem.setTitle(product.getTitle());
        orderItem.setImage(product.getImage());
        orderItem.setPrice(price);
        orderItem.setNum(num);

        //四个日志信息
        orderItem = (OrderItem) LogInfo.setLogInfo(orderItem, date, username);

        rows = orderMapper.insertOrderItem(orderItem);
        if (rows != 1) {
            throw new InsertException("添加订单项数据时产生未知的异常！");
        }

        return order;
    }

    public Order createOrderInfo(Integer aid, Integer uid, String username, Date date) {
        Address address = addressService.getAddressByAid(aid, uid);
        if (address == null) {
            throw new AddressNotFoundException("收货地址不存在！");
        }

        Order order = new Order();
        // 将收货地址信息赋给订单
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getPhone());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());
        order.setRecvAddress(address.getAddress());

        //其他信息
        order.setUid(uid);
        order.setOrderTime(date);
        order.setStatus(0);


        //4个日志信息
        order = (Order) LogInfo.setLogInfo(order, date, username);

        return order;
    }

    @Override
    public List<Order> getOrdersByUid(Integer uid, Integer status, String title) {
        List<Order> list;
        if (title == null) {
            list = orderMapper.findOrdersByUid(uid, status);
        } else {
            title = "%" + title + "%";
            list = orderMapper.findOrdersLikeTitle(uid, status, title);
        }

        for (Order order : list) {
            //将无关信息置为null
            order.setRecvPhone(null);
            order.setRecvProvince(null);
            order.setRecvCity(null);
            order.setRecvArea(null);
            order.setRecvAddress(null);
            order.setCreatedUser(null);
            order.setCreatedTime(null);
            order.setModifiedUser(null);
            order.setModifiedTime(null);
        }

        return list;
    }

    @Override
    public PageInfo<Order> getAllByUidAndPageInfo(Integer pageNum, Integer pageSize, Integer uid, Integer status, String title) {
        // 1. 分页的开始页的分页设置，分页一定要传递这两个参数
        PageHelper.startPage(pageNum, pageSize);

        // 2. 紧跟分页设置后的第一条SQL语句会被分页查询，在SQL语句后自动拼接limit子句
        List<Order> orders = getOrdersByUid(uid, status, title);

        // 3. pageInfo参数navigatePages表示显示的页码数量
        PageInfo<Order> orderPageInfo = new PageInfo<>(orders);

        return orderPageInfo;
    }

    @Override
    public List<OrderItem> getOrderItemsByOid(Integer oid) {
        List<OrderItem> data = orderMapper.findOrderItemsByOid(oid);
        if (data == null || data.size() == 0) {
            throw new OrderItemNotFoundException("订单项数据不存在！");
        }
        return data;
    }

    @Override
    public void deleteOrderAndItemsByOid(Integer oid, Integer uid) {
        Order order = orderMapper.findOrderByOid(oid);
        if (order == null) {
            throw new OrderNotFoundException("订单数据不存在！");
        }
        if (!order.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问！");
        }
        List<OrderItem> orderItems = orderMapper.findOrderItemsByOid(oid);
        if (orderItems == null || orderItems.size() == 0) {
            throw new OrderNotFoundException("订单项信息不存在！");
        }

        Integer orderRows = orderMapper.delOrderByOid(oid);
        if (orderRows != 1) {
            throw new DeleteException("删除订单信息时产生未知的异常！");
        }
        Integer itemsRows = orderMapper.delOrderItemByOid(oid);
        if (itemsRows != orderItems.size()) {
            throw new DeleteException("删除订单项时产生未知的异常！");
        }
    }

    @Override
    public Order getOrderByOidAllInfo(Integer oid, Integer uid) {
        Order order = orderMapper.findOrderByOid(oid);
        if (order == null) {
            throw new OrderNotFoundException("订单信息不存在！");
        }

        if (!order.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问！");
        }
        return order;
    }

    @Override
    public Order getOrderByOid(Integer oid, Integer uid) {
        Order order = getOrderByOidAllInfo(oid, uid);

        Order result = new Order();
        result.setUid(uid);
        result.setOid(oid);
        result.setTotalPrice(order.getTotalPrice());
        return result;
    }

    @Override
    public void updateStatusAndPayTime(Integer oid, String username, Integer status) {
        Date date = new Date();
        Integer rows = orderMapper.updateStatusAndPayTimeByOid(oid, status, date, date, username);
        if (rows != 1) {
            throw new UpdateException("更新订单数据时产生未知的异常！");
        }
    }
}
