package ms.store.controller;

import com.github.pagehelper.PageInfo;
import ms.store.entity.Favorite;
import ms.store.service.IFavoriteService;
import ms.store.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author maple
 * @create 2022-05-16 10:24
 */
@RequestMapping("/favorites")
@RestController
public class FavoriteController extends BaseController{
    @Autowired
    private IFavoriteService favoriteService;

    @Deprecated
    @RequestMapping("/all")
    public JSONResult<List<Favorite>> getAllByUid(HttpSession session,
                                                  @RequestParam(required = false, value = "title") String title) {
        Integer uid = getUidFromSession(session);
        List<Favorite> data = favoriteService.getAllByUid(uid, title);
        return new JSONResult<>(OK, data);
    }

    @RequestMapping("/all_by_page")
    public JSONResult<PageInfo<Favorite>> getAllByPage(HttpSession session,
                     @RequestParam(required = true, defaultValue = "1", value = "pageNum") Integer pageNum,
                     @RequestParam(required = true, defaultValue = "12", value = "pageSize") Integer pageSize,
                     @RequestParam(required = false, value = "title") String title) {
        Integer uid = getUidFromSession(session);
        PageInfo<Favorite> pageInfo = favoriteService.getAllByPageInfo(pageNum, pageSize, uid, title);
        return new JSONResult<>(OK, pageInfo);
    }

    @RequestMapping("/exist/{pid}")
    public JSONResult<Boolean> existByPid(@PathVariable("pid") Integer pid,
                                          HttpSession session) {
        Integer uid = getUidFromSession(session);
        Favorite favorite = favoriteService.getByPid(pid, uid);
        Boolean data = favorite != null;
        return new JSONResult<>(OK, data);
    }

    @RequestMapping("/get_fid/{pid}")
    public JSONResult<Integer> getFidByPid(HttpSession session,
                                           @PathVariable("pid") Integer pid) {
        Integer uid = getUidFromSession(session);
        Favorite favorite = favoriteService.getByPid(pid, uid);
        Integer fid = favorite.getFid();
        return new JSONResult<>(OK, fid);
    }

    @RequestMapping("/add_fav/{pid}")
    public JSONResult<Void> addFav(HttpSession session,
                                   @PathVariable("pid") Integer pid) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        favoriteService.addFavorite(pid, username, uid);
        return new JSONResult<>(OK);
    }

    @RequestMapping("/del_fav/{fid}")
    public JSONResult<Void> delFav(HttpSession session,
                                   @PathVariable("fid") Integer fid) {
        Integer uid = getUidFromSession(session);
        favoriteService.deleteByFid(fid, uid);
        return new JSONResult<>(OK);
    }
}
