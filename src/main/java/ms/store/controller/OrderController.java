package ms.store.controller;

import com.github.pagehelper.PageInfo;
import ms.store.entity.Order;
import ms.store.entity.OrderItem;
import ms.store.service.IOrderService;
import ms.store.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author maple
 * @create 2022-04-26 21:42
 */
@RestController
@RequestMapping("/orders")
public class OrderController extends BaseController {
    @Autowired
    private IOrderService orderService;

    @RequestMapping("/create_by_cids")
    public JSONResult<Order> createOrderByCids(HttpSession session, Integer aid, Integer[] cids) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        Order data = orderService.createOrderByCids(uid, username, aid, cids);
        return new JSONResult<>(OK, data);
    }

    @RequestMapping("/create_by_pid")
    public JSONResult<Order> createOrderByPid(HttpSession session, Integer aid, Integer pid, Integer num) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        Order data = orderService.createOrderByPid(uid, username, aid, pid, num);
        return new JSONResult<>(OK, data);
    }

    @RequestMapping("/get/{oid}")
    public JSONResult<Order> getByOid(HttpSession session, @PathVariable("oid") Integer oid) {
        Integer uid = getUidFromSession(session);
        Order order = orderService.getOrderByOidAllInfo(oid, uid);
//        List<Order> list = new ArrayList<>();
//        list.add(order);
        return new JSONResult<>(OK, order);
    }

    @Deprecated
    @RequestMapping("/all")
    public JSONResult<List<Order>> allOrders(HttpSession session,
                                             @RequestParam(required = false, value = "status") Integer status,
                                             @RequestParam(required = false, value = "title") String title) {
        Integer uid = getUidFromSession(session);
        List<Order> data = orderService.getOrdersByUid(uid, status, title);
        return new JSONResult<>(OK, data);
    }

    @RequestMapping("/all_by_page")
    public JSONResult<PageInfo<Order>> allOrdersByPageInfo(HttpSession session,
           @RequestParam(required = false, value = "status") Integer status,
           @RequestParam(required = true, defaultValue = "1", value = "pageNum") Integer pageNum,
           @RequestParam(required = true, defaultValue = "3", value = "pageSize") Integer pageSize,
           @RequestParam(required = false, value = "title") String title) {
        Integer uid = getUidFromSession(session);

        PageInfo<Order> page = orderService.getAllByUidAndPageInfo(pageNum, pageSize, uid, status, title);
        return new JSONResult<>(OK, page);
    }

    @RequestMapping("/items/{oid}")
    public JSONResult<List<OrderItem>> allItemsByOid(@PathVariable("oid") Integer oid) {
        List<OrderItem> items = orderService.getOrderItemsByOid(oid);
        return new JSONResult<>(OK, items);
    }

    @RequestMapping("/delete/{oid}")
    public JSONResult<Void> deleteOrder(HttpSession session, @PathVariable("oid") Integer oid) {
        Integer uid = getUidFromSession(session);
        orderService.deleteOrderAndItemsByOid(oid, uid);
        return new JSONResult<>(OK);
    }

    @RequestMapping("/get_info/{oid}")
    public JSONResult<Order> getAllInfoByOid(HttpSession session, @PathVariable("oid") Integer oid) {
        Integer uid = getUidFromSession(session);
        Order order = orderService.getOrderByOidAllInfo(oid, uid);
        return new JSONResult<>(OK, order);
    }

    @RequestMapping("/get_tol_price/{oid}")
    public JSONResult<Order> getOrderByOid(HttpSession session, @PathVariable("oid") Integer oid) {
        Integer uid = getUidFromSession(session);
        Order data = orderService.getOrderByOid(oid, uid);
        return new JSONResult<>(OK, data);
    }

    @RequestMapping("/update_stat_paytime")
    public JSONResult<Void> updateStatusAndPayTime(HttpSession session, Integer status, Integer oid) {
        String username = getUsernameFromSession(session);
        orderService.updateStatusAndPayTime(oid, username, status);
        return new JSONResult<>(OK);
    }
}
