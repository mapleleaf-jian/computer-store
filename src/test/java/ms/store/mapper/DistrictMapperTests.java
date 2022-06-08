package ms.store.mapper;

import ms.store.entity.District;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author maple
 * @create 2022-04-23 10:15
 */
@SpringBootTest
public class DistrictMapperTests {

    @Autowired
    private DistrictMapper districtMapper;

    @Test
    void findDistrictsByParentTest() {
        List<District> list = districtMapper.findDistrictsByParent("110100");
        list.forEach(System.out::println);
    }

    @Test
    void findNameByCodeTest() {
        String name = districtMapper.findNameByCode("610000");
        System.out.println("name = " + name);
    }

}
