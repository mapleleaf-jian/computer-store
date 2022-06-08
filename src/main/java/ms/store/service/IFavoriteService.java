package ms.store.service;

import com.github.pagehelper.PageInfo;
import ms.store.entity.Favorite;

import java.util.List;

/**
 * @author maple
 * @create 2022-05-16 10:15
 */
public interface IFavoriteService {
    /**
     * 根据用户uid查询所有收藏的商品, 用户可指定title为查询条件
     * @param uid
     * @return
     */
    List<Favorite> getAllByUid(Integer uid, String title);

    /**
     * 根据商品pid和uid查询收藏信息
     * @param uid 用户uid
     * @param pid 商品pid
     * @return
     */
    Favorite getByPid(Integer pid, Integer uid);

    /**
     * 添加一条收藏信息
     * @param pid 商品pid
     * @param username 修改者
     * @param uid 用户uid
     */
    void addFavorite(Integer pid, String username, Integer uid);

    /**
     * 根据fid删除一个收藏信息
     * @param fid
     * @param uid
     */
    void deleteByFid(Integer fid, Integer uid);

    /**
     * 分页显示某个uid所有的收藏信息,用户可指定title为查询条件
     * @param pageNum 页码数
     * @param pageSize 页面大小
     * @param uid 用户uid
     * @param title 查询条件
     * @return
     */
    PageInfo<Favorite> getAllByPageInfo(Integer pageNum, Integer pageSize, Integer uid, String title);
}
