package ms.store.service.impl;

import ms.store.entity.Address;
import ms.store.mapper.AddressMapper;
import ms.store.service.IAddressService;
import ms.store.service.IDistrictService;
import ms.store.service.ex.*;
import ms.store.util.LogInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author maple
 * @create 2022-04-22 21:44
 */
@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private IDistrictService districtService;

    @Autowired
    private AddressMapper addressMapper;

    @Value("${user.address.max-count}") // 读取配置文件中的值，注入到该属性上
    private Integer maxCount;

    @Override
    public void addNewAddress(Address address, String username, Integer uid) {
        // 统计该用户的收货地址条数
        Integer count = addressMapper.countByUid(uid);
        if (count >= maxCount) {
            throw new AddressCountLimitException("收货地址条数超过上限(" + maxCount + ")！");
        }

        // 根据表单传递的三个code，查询相应的name，并放入address对象中
        address = setNameByCode(address);

        // 设置是否为默认地址(将每个用户设置的第一个地址设置为默认地址)
        Integer isDefault = count == 0 ? 1 : 0;
        address.setIsDefault(isDefault);

        address.setUid(uid);

        //补全四项日志
        Date date = new Date();
        address = (Address) LogInfo.setLogInfo(address, date, username);

        Integer rows = addressMapper.insertAddress(address);
        if (rows != 1) {
            throw new InsertException("插入数据时产生未知的异常");
        }
    }

    @Override
    public List<Address> getAddressesByUid(Integer uid) {
        List<Address> list = addressMapper.findAllByUid(uid);
        for (Address address : list) {
            address.setUid(null);
            address.setZip(null);
            address.setTel(null);
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            address.setCreatedUser(null);
            address.setCreatedTime(null);
            address.setModifiedUser(null);
            address.setModifiedTime(null);
        }
        return list;
    }

    @Override
    public void setDefault(Integer aid, Integer uid, String username) {
        Address address = addressMapper.findAddressByAid(aid);
        if (address == null) {
            throw new AddressNotFoundException("收货地址不存在！");
        }

        //检测当前获取到的地址数据的归属
        if (!address.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问！");
        }

        //将uid的所有收货地址设置为非默认
        Integer rows = addressMapper.updateNonDefault(uid);
        if (rows < 1) {
            throw new UpdateException("更新数据时产生未知的异常！");
        }

        // 将某条收货地址设置为默认收货地址
        rows = addressMapper.updateDefaultByAid(aid, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新数据时产生未知的异常！");
        }
    }

    @Override
    public void deleteAddress(Integer uid, Integer aid, String username) {
        //检测当前aid的收货地址是否存在
        Address address = addressMapper.findAddressByAid(aid);
        if (address == null) {
            throw new AddressNotFoundException("收货地址不存在！");
        }

        //检测当前获取到的地址数据的归属
        if (!address.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问！");
        }

        //删除数据
        Integer rows = addressMapper.deleteAddressByAid(aid);
        if (rows != 1) {
            throw new DeleteException("删除数据时产生未知的异常！");
        }

        // 删除数据后，uid用户没有其他收货地址，直接返回
        Integer count = addressMapper.countByUid(uid);
        if (count == 0) {
            return ;
        }

        //如果删除的收货地址不是默认地址，直接返回
        if (address.getIsDefault() == 0) {
            return ;
        }

        //将最近修改的收货地址设置为默认地址
        Address lastModifiedAddress = addressMapper.findLastModifiedAddress(uid);
        rows = addressMapper.updateDefaultByAid(lastModifiedAddress.getAid(), username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新数据时产生未知的异常！");
        }
    }

    @Override
    public Address getAddressByAid(Integer aid, Integer uid) {
        Address address = getAddressByAidAllInfo(aid, uid);

        //将无关的属性设置为null
        address.setProvinceCode(null);
        address.setCityCode(null);
        address.setAreaCode(null);
        address.setCreatedUser(null);
        address.setCreatedTime(null);
        address.setModifiedUser(null);
        address.setModifiedTime(null);

        return address;
    }

    @Override
    public Address getAddressByAidAllInfo(Integer aid, Integer uid) {
        Address address = addressMapper.findAddressByAid(aid);
        if (address == null) {
            throw new AddressNotFoundException("收货地址信息不存在！");
        }

        if (!address.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问！");
        }
        return address;
    }

    @Override
    public void updateAddress(Address address, Integer uid, String username) {
        Integer aid = address.getAid();

        Address result = addressMapper.findAddressByAid(aid);
        if (result == null) {
            throw new AddressNotFoundException("收货地址信息不存在！");
        }

        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问！");
        }

        // 根据表单传递的三个code，查询相应的name，并放入address对象中
        address = setNameByCode(address);

        //补全日志信息
        Date date = new Date();
        address = (Address) LogInfo.setLogInfo(address, date, username);

        Integer rows = addressMapper.updateAddressByAid(address, aid);
        if (rows != 1) {
            throw new UpdateException("修改收货地址时出现未知的异常！");
        }
    }

    public Address setNameByCode(Address address) {
        // 根据表单传递的三个code，查询相应的name，并放入address对象中
        String provinceName = districtService.getNameByCode(address.getProvinceCode());
        String cityName = districtService.getNameByCode(address.getCityCode());
        String areaName = districtService.getNameByCode(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);
        return address;
    }
}
