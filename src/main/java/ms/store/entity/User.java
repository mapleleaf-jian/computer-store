package ms.store.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author maple
 * @create 2022-04-20 10:32
 */
@EqualsAndHashCode(callSuper = true) //子类中的equals方法使用父类的属性也作为比较
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity implements Serializable {
    private Integer uid;
    private String username;
    private String password;
    private String salt;
    private String phone;
    private String email;
    private Integer gender;
    private String avatar;
    private Integer isDelete;

    // Generate: Getter and Setter、Generate hashCode() and equals()、toString()
}
