package ms.store.service;

import ms.store.entity.District;

import java.util.List;

/**
 * @author maple
 * @create 2022-04-23 10:56
 */
public interface IDistrictService {
    /**
     * 根据父代号查询区域信息(省市区)
     * @param parent 父代号
     * @return 多个区域的信息
     */
    List<District> getDistrictsByParent(String parent);

    /**
     * 根据code查询名称
     * @param code
     * @return
     */
    String getNameByCode(String code);
}
