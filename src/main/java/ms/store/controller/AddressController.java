package ms.store.controller;

import ms.store.entity.Address;
import ms.store.service.IAddressService;
import ms.store.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author maple
 * @create 2022-04-22 22:12
 */
@RestController
@RequestMapping("/addresses")
public class AddressController extends BaseController {
    @Autowired
    private IAddressService addressService;

    @RequestMapping("/add_address")
    public JSONResult<Void> addAddress(Address address, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.addNewAddress(address, username, uid);
        return new JSONResult<Void>(OK);
    }

    @RequestMapping({"/", ""})
    public JSONResult<List<Address>> getAddressByUid(HttpSession session) {
        Integer uid = getUidFromSession(session);
        List<Address> list = addressService.getAddressesByUid(uid);
        return new JSONResult<>(OK, list);
    }

    @RequestMapping("/set_default/{aid}") //Restful风格
    public JSONResult<Void> setDefault(HttpSession session,
                                       @PathVariable("aid") Integer aid) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.setDefault(aid, uid, username);
        return new JSONResult<>(OK);
    }

    @RequestMapping("/delete_address/{aid}")
    public JSONResult<Void> deleteAddress(HttpSession session, @PathVariable("aid") Integer aid) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.deleteAddress(uid, aid, username);
        return new JSONResult<>(OK);
    }

    @RequestMapping("/get_address/{aid}")
    public JSONResult<Address> getAddressByAid(HttpSession session, @PathVariable("aid") Integer aid) {
        Integer uid = getUidFromSession(session);
        Address address = addressService.getAddressByAidAllInfo(aid, uid);
        return new JSONResult<>(OK, address);
    }

    @RequestMapping("/update/{aid}")
    public JSONResult<Void> updateAddress(HttpSession session, @ModelAttribute() Address address) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.updateAddress(address, uid, username);
        return new JSONResult<>(OK);
    }
}
