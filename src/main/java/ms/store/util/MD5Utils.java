package ms.store.util;

import org.springframework.util.DigestUtils;

/**
 * @author maple
 * @create 2022-04-20 16:09
 */
public class MD5Utils {
    /**
     * MD5 加密算法(三次)
     * @param password 原密码
     * @param salt 盐值
     * @return MD5 加密后的密码
     */
    public static String getMD5Password(String password, String salt) {
        //三次加密
        for (int i = 0; i < 3; i++) {
            //一般全为大写
            password = DigestUtils.md5DigestAsHex(salt.concat(password).concat(salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
