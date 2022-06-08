package ms.store.service;

import ms.store.vo.CartVO;

import java.util.List;

/**
 * @author maple
 * @create 2022-04-24 18:06
 */
public interface ICartService {
    /**
     * 将商品添加到购物车
     * @param uid 用户id
     * @param pid 商品id
     * @param num 新增的数量
     * @param username 用户名
     * @return 受影响的行数
     */
    void addToCart(Integer uid, Integer pid, Integer num, String username);

    /**
     * 根据用户id查找VO的购物车数据,用户可增加查询条件title
     * @param uid
     * @param title 查询条件，商品名称
     * @return
     */
    List<CartVO> getVOCartsByUid(Integer uid, String title);

    /**
     * 根据cid增加商品数量
     * @param cid 商品id
     * @param uid 用户id
     * @param username 修改者
     * @return 增加后新的数量
     */
    Integer addNumByCid(Integer cid, Integer uid, String username);

    /**
     * 根据cid减少商品数量
     * @param cid 商品id
     * @param uid 用户id
     * @param username 修改者
     * @return 增加后新的数量
     */
    Integer reduceNumByCid(Integer cid, Integer uid, String username);

    /**
     * 根据购物车id数组查找VO的购物车数据
     * @param cids
     * @return
     */
    List<CartVO> getVOCartsByCid(Integer[] cids, Integer uid);

    /**
     * 根据cid删除购物车数据
     * @param cid
     * @param uid
     * @return
     */
    void deleteCartByCid(Integer cid, Integer uid);

    /**
     * 根据cid数组删除购物车数据
     * @param cids 购物车id数组
     * @param uid 用户id
     */
    void deleteCartsByCids(Integer[] cids, Integer uid);
}
