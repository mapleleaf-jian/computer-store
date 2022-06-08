package ms.store.service;

import com.github.pagehelper.PageInfo;
import ms.store.entity.Favorite;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author maple
 * @create 2022-05-16 10:18
 */
@SpringBootTest
public class FavoriteServiceTests {
    @Autowired
    private IFavoriteService favoriteService;

    @Test
    void getAllByUidTest() {
        favoriteService.getAllByUid(22, "戴尔").forEach(System.out::println);
    }

    @Test
    void getByPidTest() {
        System.out.println(favoriteService.getByPid(10000001, 19));
    }

    @Test
    void addFavoriteTest() {
        favoriteService.addFavorite(10000002, "admin", 18);
    }

    @Test
    void deleteByFidTest() {
        favoriteService.deleteByFid(6, 22);
    }

    @Test
    void getAllByPageInfoTest() {
        PageInfo<Favorite> pageInfo =
                favoriteService.getAllByPageInfo(1, 12, 22, null);
        System.out.println(pageInfo.getPages());
        pageInfo.getList().forEach(System.out::println);
    }
}
