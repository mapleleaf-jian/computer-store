package ms.store.service.ex;

/**
 * 商品剩余不足够的异常
 * @author maple
 * @create 2022-05-17 22:36
 */
public class ProductNotEnoughException extends ServiceException {
    public ProductNotEnoughException() {
        super();
    }

    public ProductNotEnoughException(String message) {
        super(message);
    }

    public ProductNotEnoughException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductNotEnoughException(Throwable cause) {
        super(cause);
    }

    protected ProductNotEnoughException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
