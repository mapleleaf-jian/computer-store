package ms.store.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 订单中的商品数据实体类
 * @author maple
 * @create 2022-04-26 15:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderItem extends BaseEntity implements Serializable {
    private Integer id;
    private Integer oid;
    private Integer pid;
    private String title;
    private String image;
    private Long price; //单价
    private Integer num;
}
