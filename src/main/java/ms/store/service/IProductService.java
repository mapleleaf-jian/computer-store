package ms.store.service;

import com.github.pagehelper.PageInfo;
import ms.store.entity.Favorite;
import ms.store.entity.Product;

import java.util.List;

/**
 * @author maple
 * @create 2022-04-24 13:41
 */
public interface IProductService {
    /**
     * 查询全部热销商品
     * @return
     */
    List<Product> getHotList();

    /**
     * 分页查询热销商品
     * @param pageNum 当前页码数
     * @param pageSize 每页显示的数据条数
     * @return
     */
    PageInfo<Product> getHotListByPage(Integer pageNum, Integer pageSize);

    /**
     * 查询全部最新商品
     * @return
     */
    List<Product> getLatestList();

    /**
     * 分页查询全部最新商品
     * @param pageNum 当前页码数
     * @param pageSize 每页显示的数据条数
     * @return
     */
    PageInfo<Product> getLatestListByPage(Integer pageNum, Integer pageSize);

    /**
     * 根据商品id查询商品信息
     * @param id
     * @return
     */
    Product getProductById(Integer id);

    /**
     * 根据商品id查询商品信息，并将返回值的商品信息的数量设置为num
     * @param id
     * @param num
     * @return
     */
    Product getProductByIdSetNum(Integer id, Integer num);

    /**
     * 在支付成功后，根据oid更改相应商品的数量
     * @param oid
     * @param username
     */
    void updateNumByOid(Integer oid, String username);
}
