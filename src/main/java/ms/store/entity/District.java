package ms.store.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 省市区的数据实体类
 * @author maple
 * @create 2022-04-23 10:03
 */
@Data
public class District implements Serializable {
    private Integer id;
    private String parent; // 父区域的代号
    private String code; // 本身代号
    private String name;
}
