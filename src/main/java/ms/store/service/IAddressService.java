package ms.store.service;

import ms.store.entity.Address;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 收货地址业务层接口
 * @author maple
 * @create 2022-04-22 21:44
 */
public interface IAddressService {
    /**
     * 添加一个新的收货地址
     * @param address 控制层传递过来的地址信息
     * @param username 创建人、修改人
     * @param uid 用户的uid
     */
    void addNewAddress(Address address, String username, Integer uid);

    /**
     * 根据用户id获取用户的收货地址信息
     * @param uid
     * @return
     */
    List<Address> getAddressesByUid(Integer uid);

    /**
     * 修改用户的某个收货地址为默认地址
     * @param aid 收货地址的id
     * @param uid 用户id
     * @param username 修改人
     */
    void setDefault(Integer aid, Integer uid, String username);

    /**
     * 删除一条收货地址
     * @param uid 用户id
     * @param aid 收货地址id
     * @param username 修改人
     */
    void deleteAddress(Integer uid, Integer aid, String username);

    /**
     * 根据aid查询收获地址信息，将无关信息设置为null
     * @param aid 地址id
     * @param uid 用户id
     * @return 地址信息(无关信息为null)
     */
    Address getAddressByAid(Integer aid, Integer uid);

    /**
     * 根据aid查询收获地址信息，保留地址的全部信息
     * @param aid 地址id
     * @param uid 用户id
     * @return 地址信息(保留全部信息)
     */
    Address getAddressByAidAllInfo(Integer aid, Integer uid);

    /**
     * 根据aid修改某条地址信息
     * @param address
     */
    void updateAddress(Address address, Integer uid, String username);
}
