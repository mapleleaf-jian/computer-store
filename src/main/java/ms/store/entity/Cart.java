package ms.store.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author maple
 * @create 2022-04-24 16:27
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Cart extends BaseEntity implements Serializable {
    private Integer cid;
    private Integer uid;
    private Integer pid;
    private Long price; // 加入时商品单价
    private Integer num; //数量
}
