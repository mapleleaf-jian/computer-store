package ms.store.service.impl;

import ms.store.entity.District;
import ms.store.mapper.DistrictMapper;
import ms.store.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author maple
 * @create 2022-04-23 10:56
 */
@Service
public class DistrictServiceImpl implements IDistrictService {
    @Autowired
    private DistrictMapper districtMapper;

    @Override
    public List<District> getDistrictsByParent(String parent) {
        List<District> result = districtMapper.findDistrictsByParent(parent);

        // 在进行网络数据传输时，为了尽量避免无效数据的传递，可以将无效数据设置为null，可以节省流量，并提升效率
        // 将id和parent设置为null(对与结果来说是无关信息)
        for (District district : result) {
            district.setId(null);
            district.setParent(null);
        }
        return result;
    }

    @Override
    public String getNameByCode(String code) {
        String name = districtMapper.findNameByCode(code);
        return name;
    }
}
