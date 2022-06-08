package ms.store.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ms.store.entity.Favorite;
import ms.store.entity.Product;
import ms.store.mapper.FavoriteMapper;
import ms.store.service.IFavoriteService;
import ms.store.service.IProductService;
import ms.store.service.ex.*;
import ms.store.util.LogInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author maple
 * @create 2022-05-16 10:16
 */
@Service
public class FavoriteServiceImpl implements IFavoriteService {
    @Autowired
    private FavoriteMapper favoriteMapper;

    @Autowired
    private IProductService productService;

    @Override
    public List<Favorite> getAllByUid(Integer uid, String title) {
        if (title != null) {
            title = "%" + title + "%";
        }
        List<Favorite> list = favoriteMapper.findAllByUid(uid, title);
        return list;
    }

    @Override
    public Favorite getByPid(Integer pid, Integer uid) {
        Favorite favorite = favoriteMapper.findByPid(pid, uid);
        return favorite;
    }

    @Override
    public void addFavorite(Integer pid, String username, Integer uid) {
        Favorite fav = favoriteMapper.findByPid(pid, uid);
        if (fav != null) {
            throw new ProductFavoriteException("该商品已被收藏！");
        }

        Product product = productService.getProductById(pid);
        if (product == null) {
            throw new ProductNotFoundException("商品信息不存在！");
        }

        Favorite favorite = new Favorite();
        favorite.setPid(pid);
        favorite.setUid(uid);
        favorite.setImage(product.getImage());
        favorite.setPrice(product.getPrice());
        favorite.setTitle(product.getTitle());

        Date date = new Date();
        favorite = (Favorite) LogInfo.setLogInfo(favorite, date, username);

        Integer rows = favoriteMapper.insertFavorite(favorite);
        if (rows != 1) {
            throw new InsertException("添加用户收藏信息时产生未知的异常！");
        }
    }

    @Override
    public void deleteByFid(Integer fid, Integer uid) {
        Favorite favorite = favoriteMapper.findByFid(fid);
        if (favorite == null) {
            throw new FavoriteNotFoundException("收藏信息不存在！");
        }

        if (!uid.equals(favorite.getUid())) {
            throw new AccessDeniedException("非法访问！");
        }

        Integer rows = favoriteMapper.deleteByFid(fid);
        if (rows != 1) {
            throw new DeleteException("删除收藏信息时产生未知的异常！");
        }
    }

    @Override
    public PageInfo<Favorite> getAllByPageInfo(Integer pageNum, Integer pageSize, Integer uid, String title) {
        PageHelper.startPage(pageNum, pageSize);

        List<Favorite> list = getAllByUid(uid, title);

        PageInfo<Favorite> favoritePageInfo = new PageInfo<>(list);

        return favoritePageInfo;
    }
}
