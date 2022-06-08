package ms.store.controller;

import ms.store.controller.ex.*;
import ms.store.entity.BaseEntity;
import ms.store.service.ex.*;
import ms.store.util.JSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 表示控制层类的基类，抽取Controller中处理异常的代码，进行异常的捕获处理
 * @author maple
 * @create 2022-04-20 16:42
 */
public class BaseController {
    /** 操作成功的状态码 */
    public static final int OK = 200;

    /**
     * 请求处理方法
     * 自动将异常对象传递到此方法的参数列表上
     * 当前项目产生了异常，被统一拦截到此方法中，返回值就是需要传递给前端的数据
     * @param e
     * @return
     */
    @ExceptionHandler({ServiceException.class, FileUploadException.class}) //用于统一处理抛出的异常
    public JSONResult<Void> handleException(Throwable e) {
        JSONResult<Void> result = new JSONResult<>();
        if (e instanceof UsernameDuplicatedException) {
            result.setState(4000);
            result.setMessage(e.getMessage());
        } else if (e instanceof UsernameNotFoundException) {
            result.setState(4001);
            result.setMessage(e.getMessage());
        } else if (e instanceof PasswordNotMatchException) {
            result.setState(4002);
            result.setMessage(e.getMessage());
        } else if (e instanceof AddressCountLimitException) {
            result.setState(4003);
            result.setMessage(e.getMessage());
        } else if (e instanceof AddressNotFoundException) {
            result.setState(4004);
            result.setMessage(e.getMessage());
        } else if (e instanceof AccessDeniedException) {
            result.setState(4005);
            result.setMessage(e.getMessage());
        } else if (e instanceof ProductNotFoundException) {
            result.setState(4006);
            result.setMessage(e.getMessage());
        } else if (e instanceof CartNotFoundException) {
            result.setState(4007);
            result.setMessage(e.getMessage());
        } else if (e instanceof OrderNotFoundException) {
            result.setState(4008);
            result.setMessage(e.getMessage());
        } else if (e instanceof ProductFavoriteException) {
            result.setState(4009);
            result.setMessage(e.getMessage());
        } else if (e instanceof FavoriteNotFoundException) {
            result.setState(4010);
            result.setMessage(e.getMessage());
        } else if (e instanceof OrderItemNotFoundException) {
            result.setState(4011);
            result.setMessage(e.getMessage());
        } else if (e instanceof ProductNotEnoughException) {
            result.setState(4012);
            result.setMessage(e.getMessage());
        } else if (e instanceof InsertException) {
            result.setState(5000);
            result.setMessage(e.getMessage());
        } else if (e instanceof UpdateException) {
            result.setState(5001);
            result.setMessage(e.getMessage());
        } else if (e instanceof DeleteException) {
            result.setState(5002);
            result.setMessage(e.getMessage());
        } else if (e instanceof FileEmptyException) {
            result.setState(6000);
            result.setMessage(e.getMessage());
        } else if (e instanceof FileSizeException) {
            result.setState(6001);
            result.setMessage(e.getMessage());
        } else if (e instanceof FileTypeException) {
            result.setState(6002);
            result.setMessage(e.getMessage());
        } else if (e instanceof FileStateException) {
            result.setState(6003);
            result.setMessage(e.getMessage());
        } else if (e instanceof FileUploadIOException) {
            result.setState(6004);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 方法不可更改，所以设置为final
     * 获取session对象中的uid
     * @param session
     * @return
     */
    protected final Integer getUidFromSession(HttpSession session) {
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     * 方法不可更改，所以设置为final
     * 获取session对象中的username
     * @param session
     * @return
     */
    protected final String getUsernameFromSession(HttpSession session) {
        return session.getAttribute("username").toString();
    }
}
