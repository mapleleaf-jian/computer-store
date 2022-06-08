package ms.store.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 收藏类
 * @author maple
 * @create 2022-05-15 20:24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Favorite extends BaseEntity implements Serializable {
    private Integer fid;
    private Integer pid;
    private Integer uid;
    private String image;
    private Long price;
    private String title;
}
