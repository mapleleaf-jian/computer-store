package ms.store.service.ex;

/**
 * 非法访问的异常(访问的数据不是当前登录用户的收货地址数据)
 * @author maple
 * @create 2022-04-23 20:41
 */
public class AccessDeniedException extends ServiceException{
    public AccessDeniedException() {
        super();
    }

    public AccessDeniedException(String message) {
        super(message);
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessDeniedException(Throwable cause) {
        super(cause);
    }

    protected AccessDeniedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}