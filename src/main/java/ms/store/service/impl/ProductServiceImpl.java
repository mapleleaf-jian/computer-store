package ms.store.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ms.store.entity.OrderItem;
import ms.store.entity.Product;
import ms.store.mapper.OrderMapper;
import ms.store.mapper.ProductMapper;
import ms.store.service.IProductService;
import ms.store.service.ex.OrderItemNotFoundException;
import ms.store.service.ex.ProductNotEnoughException;
import ms.store.service.ex.ProductNotFoundException;
import ms.store.service.ex.UpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author maple
 * @create 2022-04-24 13:41
 */
@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Product> getHotList() {
        List<Product> list = productMapper.findHotList();
        for (Product product : list) {
            product.setPriority(null);
            product.setCreatedUser(null);
            product.setCreatedTime(null);
            product.setModifiedUser(null);
            product.setModifiedTime(null);
        }
        return list;
    }

    @Override
    public PageInfo<Product> getHotListByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        List<Product> hotList = getHotList();

        PageInfo<Product> productPageInfo = new PageInfo<>(hotList);

        return productPageInfo;
    }

    @Override
    public List<Product> getLatestList() {
        List<Product> list = productMapper.findLatestList();
        for (Product product : list) {
            product.setPriority(null);
            product.setCreatedUser(null);
            product.setCreatedTime(null);
            product.setModifiedUser(null);
            product.setModifiedTime(null);
        }
        return list;
    }

    @Override
    public PageInfo<Product> getLatestListByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        List<Product> latestList = getLatestList();

        PageInfo<Product> productPageInfo = new PageInfo<>(latestList);

        return productPageInfo;
    }

    @Override
    public Product getProductById(Integer id) {
        Product product = productMapper.findProductById(id);
        if (product == null) {
            throw new ProductNotFoundException("商品不存在！");
        }
        //将商品的部分属性设置为null
        product.setPriority(null);
        product.setCreatedUser(null);
        product.setCreatedTime(null);
        product.setModifiedUser(null);
        product.setModifiedTime(null);

        return product;
    }

    @Override
    public Product getProductByIdSetNum(Integer id, Integer num) {
        Product product = getProductById(id);
        product.setNum(num);
        return product;
    }

    @Override
    public void updateNumByOid(Integer oid, String username) {
        List<OrderItem> orderItems = orderMapper.findOrderItemsByOid(oid);
        if (orderItems == null || orderItems.size() == 0) {
            throw new OrderItemNotFoundException("订单项数据不存在！");
        }

        for (OrderItem orderItem : orderItems) {
            // 查询商品信息
            Integer pid = orderItem.getPid();
            Product product = productMapper.findProductById(pid);

            // 设置更新后的商品数量
            Integer num = orderItem.getNum();
            Integer oldNum = product.getNum();
            if (oldNum < num) {
                throw new ProductNotEnoughException("剩余商品不足够，补齐后发货！");
            }

            Integer newNum = oldNum - num;

            // 更新商品信息
            Date date = new Date();
            Integer rows = productMapper.updateNumByPid(newNum, username, date, pid);
            if (rows != 1) {
                throw new UpdateException("更新商品信息时产生未知的异常！");
            }
        }
    }
}
