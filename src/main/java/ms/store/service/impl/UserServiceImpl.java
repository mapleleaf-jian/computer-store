package ms.store.service.impl;

import ms.store.entity.User;
import ms.store.mapper.UserMapper;
import ms.store.service.IUserService;
import ms.store.service.ex.*;
import ms.store.util.LogInfo;
import ms.store.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * 用户模块业务层接口
 * @author maple
 * @create 2022-04-20 15:28
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper usermapper;

    @Override
    public void registerUser(User user) {
        String username = user.getUsername();

        //调用findUserByName(username)方法判断用户是否被注册过
        User userResult = usermapper.findUserByName(username);

        //判断结果集是否不为null，若不为null，说明数据库中存在同名用户，抛出用户名被占用的异常
        if (userResult != null) {
            throw new UsernameDuplicatedException("用户名被占用！");
        }


        //密码加密处理的实现：MD5：串 + password + 串 --> MD5算法加密，连续加密3次
        //一般串是盐值，即一个随机的字符串
        //获取盐值，一般盐值全为大写
        String salt = UUID.randomUUID().toString().toUpperCase();
        //MD5加密
        String oldPwd = user.getPassword();
        //将密码和盐值作为一个整体进行加密处理，忽略原有密码强度，提升了数据的安全性
        String newPwd = MD5Utils.getMD5Password(oldPwd, salt);
        user.setPassword(newPwd);
        //补全数据(盐值)
        user.setSalt(salt);

        // 补全数据(isDelete)
        user.setIsDelete(0);



        // 补全数据(基类中的4个日志字段信息)
        Date date = new Date();
        user = (User) LogInfo.setLogInfo(user, date, username);


        //执行用户注册功能，使用rows判断注册成功与否
        Integer rows = usermapper.insertUser(user);
        if (rows != 1) {
            throw new InsertException("用户注册过程中产生了未知的异常！");
        }
    }

    @Override
    public User login(String username, String password) {
        //根据用户名称查询用户是否存在
        User loginUser = usermapper.findUserByName(username);
        if (loginUser == null) {
            throw new UsernameNotFoundException("用户不存在！");
        }

        //检测用户密码是否匹配
        String salt = loginUser.getSalt();
        String oldPwd = loginUser.getPassword();
        String newPwd = MD5Utils.getMD5Password(password, salt);
        if (!newPwd.equals(oldPwd)) {
            throw new PasswordNotMatchException("密码不匹配！");
        }

        //判断字段 is_delete 的值，为1表示已被逻辑删除
        if (loginUser.getIsDelete() == 1) {
            throw new UsernameNotFoundException("用户不存在！");
        }

        // 返回的用户数据是为了辅助其他页面做数据展示使用(uid、username、avatar),所以新建一个对象，重新封装属性,进而缩小页面间传输用户数据的大小，提升性能
        User user = new User();
        user.setUid(loginUser.getUid());
        user.setUsername(loginUser.getUsername());
        // 返回有用户的头像
        user.setAvatar(loginUser.getAvatar());
        return user;
    }

    @Override
    public void changePassword(String username, Integer uid, String oldPwd, String newPwd) {
        // 根据uid获取用户数据
        User result = usermapper.findUserByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UsernameNotFoundException("用户不存在！");
        }

        // 将输入的原密码 与数据库中的密码对比
        String salt = result.getSalt();
        String oldMD5Pwd = MD5Utils.getMD5Password(oldPwd, salt);
        if (!oldMD5Pwd.equals(result.getPassword())) {
            throw new PasswordNotMatchException("密码错误！");
        }

        //将传递过来的原密码和新密码对比，判断是否不同
        if (oldPwd.equals(newPwd)) {
            throw new PasswordNotMatchException("输入的新密码不能与原密码相同！");
        }

        // 更新密码
        String newMD5Pwd = MD5Utils.getMD5Password(newPwd, salt);
        Integer rows = usermapper.updatePasswordByUid(newMD5Pwd, uid, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新数据时产生未知的异常！");
        }
    }

    @Override
    public User getUserByUid(Integer uid) {
        User user = usermapper.findUserByUid(uid);
        if (user == null || user.getIsDelete() == 1) {
            throw new UsernameNotFoundException("用户不存在！");
        }

        // 为了缩减前后端传输的数据大小，将前端需要的数据封装一个User对象，并返回
        User resUser = new User();
        resUser.setUsername(user.getUsername());
        resUser.setPhone(user.getPhone());
        resUser.setEmail(user.getEmail());
        resUser.setGender(user.getGender());
        return resUser;
    }

    @Override
    public void changeUserInfo(String username, Integer uid, User user) {
        User result = usermapper.findUserByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UsernameNotFoundException("用户不存在！");
        }

        //参数user只包含前端的数据：username, phone, email, gender, 需要将其他信息也封装进去
        user.setUid(uid);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());

        // 更新用户信息
        Integer rows = usermapper.updateInfoByUid(user);
        if (rows != 1) {
            throw new UpdateException("更新数据时产生了未知的异常！");
        }
    }

    @Override
    public void changeAvatar(String avatar, String username, Integer uid) {
        // 查询当前的数据是否存在
        User result = usermapper.findUserByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UsernameNotFoundException("用户不存在！");
        }

        Integer rows = usermapper.updateAvatarByUid(username, uid, avatar, new Date());
        if (rows != 1) {
            throw new UpdateException("更新用户头像产生未知的异常！");
        }
    }

}
