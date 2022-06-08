package ms.store.mapper;

import ms.store.entity.District;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author maple
 * @create 2022-04-23 10:09
 */
@Repository
public interface DistrictMapper {
    /**
     * 根据父代号查询区域信息
     * @param parent 父代号
     * @return 某个父区域下所有的区域列表
     */
    List<District> findDistrictsByParent(String parent);

    /**
     * 根据code查询名称
     * @param code
     * @return
     */
    String findNameByCode(String code);
}
