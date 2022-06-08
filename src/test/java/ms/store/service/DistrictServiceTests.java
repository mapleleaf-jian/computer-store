package ms.store.service;

import ms.store.entity.District;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author maple
 * @create 2022-04-23 11:05
 */
@SpringBootTest
public class DistrictServiceTests {
    @Autowired
    private IDistrictService districtService;

    @Test
    void getDistrictsByParentTest() {
        List<District> list = districtService.getDistrictsByParent("86");
        list.forEach(System.out::println);
    }

    @Test
    void getNameByCodeTest() {
        String name = districtService.getNameByCode("610000");
        System.out.println("name = " + name);
    }
}
