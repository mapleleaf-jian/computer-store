package ms.store.service;

import ms.store.entity.Address;
import ms.store.mapper.AddressMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author maple
 * @create 2022-04-22 22:08
 */
@SpringBootTest
public class AddressServiceTests {
    @Autowired
    private IAddressService addressService;

    @Autowired
    private AddressMapper addressMapper;

    @Test
    void addNewAddressTest() {
        Address address = new Address();
        address.setPhone("15896964444");
        addressService.addNewAddress(address, "davaid",16);
    }

    @Test
    void getAddressesByUidTest() {
        List<Address> list = addressService.getAddressesByUid(22);
        list.forEach(System.out::println);
    }

    @Test
    void setDefaultTest() {
        addressService.setDefault(4, 22, "管理员");
    }

    @Test
    void deleteAddressTest() {
        addressService.deleteAddress(13, 9, "admin");
    }

    @Test
    void getAddressByAidTest() {
        Address address = addressService.getAddressByAid(4, 22);
        System.out.println("address = " + address);
    }

    @Test
    void getAddressByAidAllInfoTest() {
        Address address = addressService.getAddressByAidAllInfo(23, 22);
        System.out.println("address = " + address);
    }

    @Test
    void updateAddressTest() {
        Address address = addressMapper.findAddressByAid(25);
        System.out.println(address);
        addressService.updateAddress(address, 22, "admin");
    }
}
