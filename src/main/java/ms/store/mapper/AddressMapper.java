package ms.store.mapper;


import ms.store.entity.Address;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 收货地址持久层的接口
 * @author maple
 * @create 2022-04-22 21:00
 */
@Repository
public interface AddressMapper {
    /**
     * 插入用户的收货地址
     * @param address 收获地址数据
     * @return 受影响的行数
     */
    Integer insertAddress(Address address);

    /**
     * 根据用户id查询已存在的地址数
     * 定义这个接口的含义：假设在当前项目中，一个用户拥有的地址条数最大为20条，所以需要查询一下
     * @param uid 用户id
     * @return 当前用户的收获地址总条数
     */
    Integer countByUid(Integer uid);

    /**
     * 根据用户id查询用户的全部收货地址数据
     * @param uid
     * @return
     */
    List<Address> findAllByUid(Integer uid);

    /**
     * 根据aid查询收货地址数据
     * @param aid 收货地址id
     * @return 收货地址数据，找不到返回null
     */
    Address findAddressByAid(Integer aid);

    /**
     * 根据用户id将其所有地址修改为非默认
     * @param uid
     * @return 受影响的行数
     */
    Integer updateNonDefault(Integer uid);

    /**
     * 根据aid修改收货地址为默认
     * @param aid 收货地址id
     * @return 受影响的行数
     */
    Integer updateDefaultByAid(@Param("aid") Integer aid,
                               @Param("modifiedUser") String username,
                               @Param("modifiedTime") Date time);

    /**
     * 根据aid删除指定的收货地址
     * @param aid 收货地址的id
     * @return 受影响的行数
     */
    Integer deleteAddressByAid(Integer aid);

    /**
     * 根据用户uid查询最近一次修改过的收货地址
     * @param uid 用户id
     * @return 收货地址
     */
    Address findLastModifiedAddress(Integer uid);

    /**
     * 修改地址信息
     * @param address
     * @return
     */
    Integer updateAddressByAid(@Param("address") Address address, Integer aid);
}
