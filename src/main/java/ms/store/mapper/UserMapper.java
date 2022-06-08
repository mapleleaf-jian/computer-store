package ms.store.mapper;

import java.util.Date;
import ms.store.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户持久层的接口
 * @author maple
 * @create 2022-04-20 10:47
 */
@Repository
public interface UserMapper {
    /**
     * 插入用户的数据
     * @param user
     * @return 受影响的行数，根据返回值判断是否执行成功
     */
    Integer insertUser(User user);

    /**
     * 根据用户名来查询用户的数据
     * @param username
     * @return 如果找到返回这个用户的数据，找不到返回null值
     */
    User findUserByName(String username);

    /**
     * 根据用户的uid来修改密码
     * @param password
     * @param uid
     * @param modifiedUser
     * @param time
     * @return 受影响的行数
     */
    Integer updatePasswordByUid(@Param("password") String password,
                                @Param("uid") Integer uid,
                                @Param("modifiedUser") String modifiedUser,
                                @Param("modifiedTime") Date time);

    /**
     * 根据uid查询用户信息
     * @param uid 用户id
     * @return
     */
    User findUserByUid(@Param("uid") Integer uid);

    /**
     * 根据uid更新用户的数据信息(phone, email, gender, modifiedUser, modifiedTime)
     * @param user 用户信息
     * @return 受影响的行数
     */
    Integer updateInfoByUid(User user);


    /**
     * 根据 uid更新头像(存储的是路径)
     * @param username
     * @param uid
     * @param avatar
     * @param time
     * @return
     */
    Integer updateAvatarByUid(@Param("modifiedUser") String username,
                              @Param("uid") Integer uid,
                              @Param("avatar") String avatar,
                              @Param("modifiedTime") Date time);
}
