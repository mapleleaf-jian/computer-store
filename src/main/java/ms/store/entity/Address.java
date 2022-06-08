package ms.store.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author maple
 * @create 2022-04-22 20:53
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class Address extends BaseEntity{
    private Integer aid;
    private Integer uid;
    private String name;
    private String provinceName;
    private String provinceCode;
    private String cityName;
    private String cityCode;
    private String areaName;
    private String areaCode;
    private String zip; //邮编
    private String address;
    private String phone;
    private String tel;
    private String tag;
    private Integer isDefault;
}
