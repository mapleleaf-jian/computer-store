package ms.store.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 表示购物车的VO类
 * @author maple
 * @create 2022-04-25 10:45
 */
@Data
public class CartVO implements Serializable {
    private Integer cid;
    private Integer uid;
    private Integer pid;
    private String image;
    private String title;
    private Long price;
    private Integer num;
    private Long realPrice;
}
