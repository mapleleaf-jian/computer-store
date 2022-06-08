package ms.store.mapper;

import ms.store.entity.Cart;
import ms.store.vo.CartVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author maple
 * @create 2022-04-24 16:37
 */
@Repository
public interface CartMapper {
    /**
     * 插入购物车数据
     * @param cart 购物车数据
     * @return 受影响的行数
     */
    Integer insertCart(Cart cart);

    /**
     * 更新购物车某件商品的数量
     * @param cid 购物车id
     * @param num 更新后的数量
     * @param modifiedUser 修改人
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    Integer updateNumByCid(Integer cid, Integer num, String modifiedUser, Date modifiedTime);

    /**
     * 根据购物车id和商品id查询购物车数据，在添加购物车时，由于不知道cid，所以只能根据uid和pid查
     * @param uid 用户id
     * @param pid 商品id
     * @return 购物车数据
     */
    Cart findByUidAndPid(Integer uid, Integer pid);

    /**
     * 根据用户id查找VO的购物车数据,用户可增加查询条件title
     * @param uid 用户uid
     * @param title 查询条件，商品名称
     * @return
     */
    List<CartVO> findVOCartsByUid(Integer uid, String title);

    /**
     * 根据cid查询购物车信息
     * @param cid
     * @return
     */
    Cart findByCid(Integer cid);

    /**
     * 根据cid查询购物车数据
     * @param cids 购物车id数组
     * @return CartVO的集合
     */
    List<CartVO> findVOCartByCid(@Param("cids") Integer[] cids);

    /**
     * 根据cid删除购物车数据
     * @param cid
     * @return
     */
    Integer deleteCartByCid(Integer cid);

    /**
     * 根据cid数组删除购物车数据
     * @param cids
     * @return
     */
    Integer deleteCartsByCids(@Param("cids") Integer[] cids);
}
