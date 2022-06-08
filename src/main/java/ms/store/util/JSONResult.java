package ms.store.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * JSON格式的数据响应
 * @author maple
 * @create 2022-04-20 16:24
 */
@Getter
@Setter
@NoArgsConstructor
public class JSONResult<E> implements Serializable {
    /** 状态码 */
    private Integer state;

    /** 状态描述信息 */
    private String message;

    /** 响应的数据 */
    private E data;

    public JSONResult(Integer state) {
        this.state = state;
    }

    public JSONResult(Integer state, E data) {
        this.state = state;
        this.data = data;
    }

    /** 出现异常时调用 */
    public JSONResult(Throwable e) {
        super();
        // 获取异常对象中的异常信息
        this.message = e.getMessage();
    }
}
