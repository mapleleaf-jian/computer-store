package ms.store.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 作为实体类的基类，需要实现Serializable接口
 * @author maple
 * @create 2022-04-20 10:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity implements Serializable {
    private String createdUser;
    private Date createdTime;
    private String modifiedUser;
    private Date modifiedTime;

    // Generate: Getter and Setter、Generate hashCode() and equals()、toString()
}
