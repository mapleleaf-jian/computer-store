package ms.store.mapper;

import ms.store.entity.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author maple
 * @create 2022-04-24 13:28
 */
@Repository
public interface ProductMapper {
    /**
     * 查询热销商品
     * @return
     */
    List<Product> findHotList();

    /**
     * 查询近期商品
     * @return
     */
    List<Product> findLatestList();

    /**
     * 根据商品id查询商品信息
     * @param id
     * @return
     */
    Product findProductById(Integer id);

    Integer updateNumByPid(Integer num,
                           @Param("modifiedUser") String username,
                           @Param("modifiedTime") Date date,
                           @Param("id") Integer pid);
}
