package ms.store.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import ms.store.entity.Product;
import ms.store.service.IProductService;
import ms.store.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author maple
 * @create 2022-04-24 13:52
 */
@RestController
@RequestMapping("/products")
public class ProductController extends BaseController {
    @Autowired
    private IProductService productService;

    @Deprecated
    @RequestMapping("/hot_list")
    public JSONResult<List<Product>> getHotList() {
        List<Product> hotList = productService.getHotList();
        return new JSONResult<>(OK, hotList);
    }

    @RequestMapping("/hot_list_page")
    public JSONResult<PageInfo<Product>> getHotListByPage(
            @RequestParam(required = true, defaultValue = "1", value = "pageNum") Integer pageNum,
            @RequestParam(required = true, defaultValue = "4", value = "pageSize") Integer pageSize) {
        PageInfo<Product> hotListPageInfo = productService.getHotListByPage(pageNum, pageSize);

        return new JSONResult<>(OK, hotListPageInfo);
    }

    @Deprecated
    @RequestMapping("/latest_list")
    public JSONResult<List<Product>> getLatestList() {
        List<Product> latestList = productService.getLatestList();
        return new JSONResult<>(OK, latestList);
    }

    @RequestMapping("/latest_list_page")
    public JSONResult<PageInfo<Product>> getLatestListByPage(
            @RequestParam(required = true, defaultValue = "1", value = "pageNum") Integer pageNum,
            @RequestParam(required = true, defaultValue = "4", value = "pageSize") Integer pageSize) {
        PageInfo<Product> latestListPageInfo = productService.getLatestListByPage(pageNum, pageSize);
        return new JSONResult<>(OK, latestListPageInfo);
    }

    @RequestMapping("/details/{id}")
    public JSONResult<Product> getProductById(@PathVariable("id") Integer id) {
        Product product = productService.getProductById(id);
        return new JSONResult<>(OK, product);
    }

    @RequestMapping("/purchase")
    public JSONResult<Product> purchase(@RequestParam("pid") Integer id, @RequestParam("num") Integer num) {
        Product product = productService.getProductByIdSetNum(id, num);
        return new JSONResult<Product>(OK, product);
    }

    @RequestMapping("/update_num/{oid}")
    public JSONResult<Void> updateNumByOid(HttpSession session, @PathVariable("oid") Integer oid) {
        String username = getUsernameFromSession(session);
        productService.updateNumByOid(oid, username);
        return new JSONResult<>(OK);
    }
}
