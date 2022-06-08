package ms.store.service.ex;

/**
 * 商品已被收藏时抛出的异常
 * @author maple
 * @create 2022-05-16 21:31
 */
public class ProductFavoriteException extends ServiceException {
    public ProductFavoriteException() {
        super();
    }

    public ProductFavoriteException(String message) {
        super(message);
    }

    public ProductFavoriteException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductFavoriteException(Throwable cause) {
        super(cause);
    }

    protected ProductFavoriteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
