package ms.store.service;

import ms.store.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 用户业务层接口
 * @author maple
 * @create 2022-04-20 15:28
 */
public interface IUserService {
    /**
     * 用户注册方法
     * 首次注册需指定基类中的四个属性，用户只需指定username和password,其余信息在注册完成后可自己补全
     * @param user
     */
    void registerUser(User user);

    /**
     * 用户登录方法
     * 将当前登录成功的用户数据以当前用户对象的形式进行返回。
     * 状态管理：可以将数据保存在cookie或session中，可以避免重复度很高的数据多次频繁操作数据进行获取(用户id存放在session中，用户头像存放于cookie中)
     * @param username
     * @param password
     * @return 当前匹配的用户数据，如果没有则返回null
     */
    User login(String username, String password);

    /**
     * 修改密码
     * @param username
     * @param uid
     * @param oldPwd
     * @param newPwd
     * @Description 这里的username和uid是作为参数传进来的，在控制层会调用业务层的这个方法，那时再从session中获取这两个值
     */
    void changePassword(String username, Integer uid, String oldPwd, String newPwd);

    /**
     * 根据uid查询用户信息
     * @param uid 用户id
     * @return
     */
    User getUserByUid(Integer uid);


    /**
     * 更新用户的数据操作
     * @param username 用户名称
     * @param uid 用户id
     * @param user 用户对象的数据
     */
    void changeUserInfo(String username, Integer uid, User user);

    /**
     * 修改用户的头像
     * @param avatar 用户头像的路径
     * @param username 用户名称
     * @param uid 用户id
     */
    void changeAvatar(String avatar, String username, Integer uid);
}
