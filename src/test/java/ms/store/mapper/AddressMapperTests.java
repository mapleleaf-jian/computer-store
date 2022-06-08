package ms.store.mapper;

import ms.store.entity.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

/**
 * @author maple
 * @create 2022-04-22 21:29
 */
@SpringBootTest
public class AddressMapperTests {
    @Autowired
    private AddressMapper addressMapper;

    @Test
    void insertAddressTest() {
        Address address = new Address();
        address.setUid(5);
        address.setName("tom");
        address.setPhone("12344445555");
        address.setTag("no");
        Integer rows = addressMapper.insertAddress(address);
        System.out.println("rows = " + rows);
    }

    @Test
    void countByUidTest() {
        Integer count = addressMapper.countByUid(5);
        System.out.println("count = " + count);
    }

    @Test
    void findAllByUidTest() {
        List<Address> list = addressMapper.findAllByUid(22);
        list.forEach(System.out::println);
    }

    @Test
    void findAddressByAidTest() {
        Address address = addressMapper.findAddressByAid(7);
        System.out.println(address);
    }

    @Test
    void updateNonDefaultTest() {
        Integer rows = addressMapper.updateNonDefault(22);
        System.out.println("rows = " + rows);
    }

    @Test
    void updateDefaultByAidTest() {
        Integer rows = addressMapper.updateDefaultByAid(7, "admin", new Date());
        System.out.println("rows = " + rows);
    }

    @Test
    void deleteAddressByAidTest() {
        Integer rows = addressMapper.deleteAddressByAid(2);
        System.out.println("rows = " + rows);
    }

    @Test
    void findLastModifiedAddressTest() {
        Address address = addressMapper.findLastModifiedAddress(22);
        System.out.println(address);
    }

    @Test
    void updateAddressByAidTest() {
        Address address = addressMapper.findAddressByAid(25);
        System.out.println(address);
        Integer rows = addressMapper.updateAddressByAid(address, 19);
        System.out.println("rows = " + rows);
    }

}