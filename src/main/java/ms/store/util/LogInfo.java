package ms.store.util;

import ms.store.entity.BaseEntity;

import java.util.Date;

/**
 * 封装4个日志信息
 * @author maple
 * @create 2022-05-10 22:02
 */
public class LogInfo {
    public static BaseEntity setLogInfo(BaseEntity baseEntity, Date date, String username) {
        baseEntity.setCreatedUser(username);
        baseEntity.setModifiedUser(username);
        baseEntity.setCreatedTime(date);
        baseEntity.setModifiedTime(date);
        return baseEntity;
    }
}
