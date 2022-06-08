package ms.store.controller;

import ms.store.service.ICartService;
import ms.store.util.JSONResult;
import ms.store.vo.CartVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author maple
 * @create 2022-04-24 19:19
 */
@RestController
@RequestMapping("/carts")
public class CartController extends BaseController {
    @Autowired
    private ICartService cartService;

    @RequestMapping("/add_cart")
    public JSONResult<Void> addToCart(Integer pid,
                                      HttpSession session,
                                      @RequestParam(required = true, defaultValue = "1", value = "num") Integer num) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        cartService.addToCart(uid, pid, num, username);
        return new JSONResult<Void>(OK);
    }

    @RequestMapping({"/", ""})
    public JSONResult<List<CartVO>> getVOByUid(HttpSession session,
                                               @RequestParam(required = false, value = "title") String title) {
        List<CartVO> list = cartService.getVOCartsByUid(getUidFromSession(session), title);
        return new JSONResult<>(OK, list);
    }

    @RequestMapping("/add_num/{cid}")
    public JSONResult<Integer> addNum(HttpSession session, @PathVariable("cid") Integer cid) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        Integer data = cartService.addNumByCid(cid, uid, username);
        return new JSONResult<Integer>(OK, data);
    }

    @RequestMapping("/reduce_num/{cid}")
    public JSONResult<Integer> reduceNum(HttpSession session, @PathVariable("cid") Integer cid) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        Integer data = cartService.reduceNumByCid(cid, uid, username);
        return new JSONResult<Integer>(OK, data);
    }

    @RequestMapping("/list")
    public JSONResult<List<CartVO>> getByCid(HttpSession session, Integer[] cids) {
        Integer uid = getUidFromSession(session);
        List<CartVO> data = cartService.getVOCartsByCid(cids, uid);
        return new JSONResult<>(OK, data);
    }

    @RequestMapping("/delete/{cid}")
    public JSONResult<Void> deleteCart(HttpSession session, @PathVariable("cid") Integer cid) {
        Integer uid = getUidFromSession(session);
        cartService.deleteCartByCid(cid, uid);
        return new JSONResult<>(OK);
    }

    @RequestMapping("/delete_carts")
    public JSONResult<Void> deleteCarts(HttpSession session, Integer[] cids) {
        Integer uid = getUidFromSession(session);
        cartService.deleteCartsByCids(cids, uid);
        return new JSONResult<>(OK);
    }
}
