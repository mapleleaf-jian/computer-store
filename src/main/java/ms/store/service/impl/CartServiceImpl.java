package ms.store.service.impl;

import ms.store.entity.Cart;
import ms.store.entity.Product;
import ms.store.mapper.CartMapper;
import ms.store.mapper.ProductMapper;
import ms.store.service.ICartService;
import ms.store.service.ex.*;
import ms.store.util.LogInfo;
import ms.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author maple
 * @create 2022-04-24 18:10
 */
@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    private CartMapper cartMapper;

    // 购物车的业务层还依赖于购物车的持久层以及商品的持久层
    @Autowired
    private ProductMapper productMapper;

    @Override
    public void addToCart(Integer uid, Integer pid, Integer num, String username) {
        // 判断当前要添加的购物车是否在表中已存在
        Cart result = cartMapper.findByUidAndPid(uid, pid);
        Integer rows;
        Date date = new Date();
        if (result == null) { //表示这个商品从来没有被添加到表中，进行插入操作
            // 创建一个Cart对象
            Cart cart = new Cart();
            //补全数据
            Product product = productMapper.findProductById(pid);
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(num);
            cart.setPrice(product.getPrice());

            // 补全4个日志信息
            cart = (Cart) LogInfo.setLogInfo(cart, date, username);

            rows = cartMapper.insertCart(cart);
            if (rows != 1) {
                throw new InsertException("插入数据时产生未知的异常！");
            }
        } else { // 表示当前商品在购物车中已存在，进行修改操作
            rows = cartMapper.updateNumByCid(result.getCid(), result.getNum() + num, username, date);
            if (rows != 1) {
                throw new UpdateException("修改数据时产生未知的异常！");
            }
        }
    }

    @Override
    public List<CartVO> getVOCartsByUid(Integer uid, String title) {
        if (title != null) {
            title = "%" + title + "%";
        }
        return cartMapper.findVOCartsByUid(uid, title);
    }

    @Override
    public Integer addNumByCid(Integer cid, Integer uid, String username) {
        Cart cart = cartMapper.findByCid(cid);
        if (cart == null) {
            throw new CartNotFoundException("购物车信息不存在！");
        }

        if (cart.getUid() != uid) {
            throw new AccessDeniedException("非法访问！");
        }

        int newNum = cart.getNum() + 1;
        Integer rows = cartMapper.updateNumByCid(cid, newNum, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新数量时产生未知的异常！");
        }

        //返回新的购物车中商品的数量
        return newNum;
    }

    @Override
    public Integer reduceNumByCid(Integer cid, Integer uid, String username) {
        Cart cart = cartMapper.findByCid(cid);
        if (cart == null) {
            throw new CartNotFoundException("购物车信息不存在！");
        }

        if (cart.getUid() != uid) {
            throw new AccessDeniedException("非法访问！");
        }

        // 当前数量为0，直接返回0，即：在数量为0的情况下，点击减少依然是0
        if (cart.getNum() == 0) {
            return 0;
        }
        int newNum = cart.getNum() - 1;
        Integer rows = cartMapper.updateNumByCid(cid, newNum, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新数量时产生未知的异常！");
        }

        //返回新的购物车中商品的数量
        return newNum;
    }

    @Override
    public List<CartVO> getVOCartsByCid(Integer[] cids, Integer uid) {
        List<CartVO> res = cartMapper.findVOCartByCid(cids);

        //使用迭代器判断是否该商品属于当前用户
        Iterator<CartVO> it = res.iterator();
        while (it.hasNext()) {
            CartVO cartVO = it.next();
            if (!cartVO.getUid().equals(uid)) { //商品不属于当前用户，将其从集合中删除
                it.remove();
            }
        }
        return res;
    }

    @Override
    public void deleteCartByCid(Integer cid, Integer uid) {
        Cart cart = cartMapper.findByCid(cid);
        if (cart == null) {
            throw new CartNotFoundException("购物车数据不存在！");
        }

        if (!cart.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问！");
        }

        Integer rows = cartMapper.deleteCartByCid(cid);
        if (rows != 1) {
            throw new DeleteException("删除数据时产生未知的异常！");
        }
    }

    @Override
    public void deleteCartsByCids(Integer[] cids, Integer uid) {
        if (cids == null) {
            throw new DeleteException("传入的购物车cids数组为空");
        }
        for (Integer cid : cids) {
            Cart cart = cartMapper.findByCid(cid);
            if (cart == null) {
                throw new CartNotFoundException("购物车数据不存在！");
            }
            if (!cart.getUid().equals(uid)) {
                throw new AccessDeniedException("非法访问！");
            }
        }

        Integer rows = cartMapper.deleteCartsByCids(cids);
        if (rows < 1) {
            throw new DeleteException("删除购物车数据时出现未知的异常！");
        }
    }
}
