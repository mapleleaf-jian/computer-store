package ms.store.service;

import ms.store.entity.User;
import ms.store.service.ex.InsertException;
import ms.store.service.ex.UsernameDuplicatedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * @author maple
 * @create 2022-04-20 15:51
 */
@SpringBootTest
public class UserServiceTests {
    @Autowired
    private IUserService userService;

    @Test
    void registerUserTest() {
        try {
            User user = new User();
            user.setUsername("dam03");
            user.setPassword("123456");
            userService.registerUser(user);
            System.out.println("Register success!");
        } catch (UsernameDuplicatedException e) {
            System.out.println(e.getClass().getSimpleName()); //获取类的对象，再获取类的名称
            System.out.println(e.getMessage());//获取异常的具体描述信息
        } catch (InsertException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }


    @Test
    void loginTest() {
        User user = userService.login("davaid", "123456");
        System.out.println("user = " + user);
    }

    @Test
    void changePasswordTest() {
        userService.changePassword("davaid", 16, "123456", "123");
    }

    @Test
    void getUserByUidTest() {
        User user = userService.getUserByUid(19);
        System.out.println("user = " + user);
    }

    @Test
    void changeUserInfoTest() {
        User user = new User();
        user.setEmail("lucy@q.com");
        user.setPhone("10010");
        user.setGender(1);
        userService.changeUserInfo("管理员", 14, user);
    }

    @Test
    void changeAvatar() {
        userService.changeAvatar("path", "dam1", 8);
    }
}
