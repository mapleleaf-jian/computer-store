package ms.store.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单数据的实体类
 * @author maple
 * @create 2022-04-26 15:30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Order extends BaseEntity implements Serializable {
    private Integer oid;
    private Integer uid;
    private String recvName; //收货人姓名
    private String recvPhone;
    private String recvProvince;
    private String recvCity;
    private String recvArea;
    private String recvAddress;
    private Long totalPrice;
    private Integer status; // 状态：0-未支付，1-已支付，2-已取消，3-已发货，4-已完成
    private Date orderTime; //下单时间
    private Date payTime;// 支付时间
}
