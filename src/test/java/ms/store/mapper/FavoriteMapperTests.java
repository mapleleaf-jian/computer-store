package ms.store.mapper;

import ms.store.entity.Favorite;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author maple
 * @create 2022-05-16 10:10
 */
@SpringBootTest
public class FavoriteMapperTests {
    @Autowired
    private FavoriteMapper favoriteMapper;

    @Test
    void findAllByUidTest() {
        favoriteMapper.findAllByUid(22, "%戴尔%").forEach(System.out::println);
    }

    @Test
    void findByPidTest() {
        Favorite favorite = favoriteMapper.findByPid(10000002, 22);
        System.out.println("favorite = " + favorite);
    }

    @Test
    void insertFavorite() {
        Favorite favorite = new Favorite();
        favorite.setUid(19);
        favorite.setPid(10000001);
        Integer rows = favoriteMapper.insertFavorite(favorite);
        System.out.println("rows = " + rows);
    }

    @Test
    void deleteByFidTest() {
        System.out.println(favoriteMapper.deleteByFid(10));
    }

    @Test
    void findByFidTest() {
        System.out.println(favoriteMapper.findByFid(11));
    }
}
