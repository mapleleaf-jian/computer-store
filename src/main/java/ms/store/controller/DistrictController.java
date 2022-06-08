package ms.store.controller;

import ms.store.entity.District;
import ms.store.service.IDistrictService;
import ms.store.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author maple
 * @create 2022-04-23 13:03
 */
@RestController
@RequestMapping("/districts") //获取地区这个请求应该在用户未登录时也生效，因此将此请求加入到 登录拦截器 的exclude中
public class DistrictController extends BaseController {
    @Autowired
    private IDistrictService districtService;

    // 表示拦截/districts结尾的所有请求, 如/districts/ 和 /districts
    @RequestMapping({"/", ""})
    public JSONResult<List<District>> getDistrictsByParent(@RequestParam("parent") String parent) {
        List<District> list = districtService.getDistrictsByParent(parent);
        return new JSONResult<>(OK, list);
    }
}
