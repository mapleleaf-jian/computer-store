package ms.store.mapper;

import ms.store.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @author maple
 * @create 2022-04-20 13:51
 */
// 表示标注当前的类是一个测试类，不会随项目一块打包
@SpringBootTest
public class UserMapperTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void insertUserTest() {
        User user = new User();
        user.setUsername("tom");
        user.setPassword("123");
        Integer rows = userMapper.insertUser(user);
        System.out.println("rows = " + rows);
    }

    @Test
    public void findUserByNameTest(){
        User user = userMapper.findUserByName("tim");
        System.out.println("user = " + user);
    }

    @Test
    public void findUserByUidTest() {
        User user = userMapper.findUserByUid(19);
        System.out.println("user = " + user);
    }

    @Test
    public void updatePasswordByUidTest() {
        Integer rows = userMapper.updatePasswordByUid("321", 2, "管理员", new Date());
        System.out.println("rows = " + rows);
    }

    @Test
    public void updateInfoByUidTest() {
        User user = new User();
        user.setUid(6);
        user.setEmail("dam@qq.com");
        user.setPhone("10086");
        user.setGender(0);
        user.setModifiedUser("管理员");
        user.setModifiedTime(new Date());
        Integer rows = userMapper.updateInfoByUid(user);
        System.out.println("rows = " + rows);
    }

    @Test
    public void updateAvatarByUidTest() {
        Integer rows = userMapper.updateAvatarByUid("管理员", 9, "path", new Date());
        System.out.println("rows = " + rows);
    }
}
