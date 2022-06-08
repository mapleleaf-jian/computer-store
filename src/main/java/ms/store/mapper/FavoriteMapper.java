package ms.store.mapper;

import ms.store.entity.Favorite;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author maple
 * @create 2022-05-16 9:55
 */
@Repository
public interface FavoriteMapper {
    /**
     * 根据用户uid查询所有收藏的商品,用户可指定title为查询条件
     * @param uid 用户uid
     * @param title 商品名称
     * @return
     */
    List<Favorite> findAllByUid(Integer uid, String title);

    /**
     * 根据商品pid和用户uid查询收藏信息
     * @param pid 商品pid
     * @param uid 用户uid
     * @return 收藏信息
     */
    Favorite findByPid(Integer pid, Integer uid);

    /**
     * 插入用户收藏信息
     * @param favorite
     * @return
     */
    Integer insertFavorite(Favorite favorite);

    /**
     * 根据fid删除一个收藏信息
     * @param fid
     * @return
     */
    Integer deleteByFid(Integer fid);

    /**
     * 根据fid查询收藏信息
     * @param fid
     * @return
     */
    Favorite findByFid(Integer fid);
}
