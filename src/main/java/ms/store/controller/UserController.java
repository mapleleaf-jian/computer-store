package ms.store.controller;

import ms.store.controller.ex.*;
import ms.store.entity.User;
import ms.store.service.IUserService;
import ms.store.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author maple
 * @create 2022-04-20 16:24
 */
@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    /**
     * 文件上传单个文件最大值，10M，这里单位为bytes
     */
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;

    /**
     * 文件上传的类型
     */
    public static final List<String> AVATAR_TYPE = new ArrayList<>();

    // 初始化集合 AVATAR_TYPE
    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }



    /**
     * 注册方法
     * SpringBoot约定大于配置，开发思想来完成，省略大量的配置甚至注解
     * 接收数据方式1：请求处理方法的参数列表设置为pojo类型来接收前端的数据
     * SpringBoot会将前端的url地址中的参数名和pojo类的属性名进行比较，如果这两个名称相同，则将值注入到pojo类中对应的属性
     * @param user
     * @return
     */
    @RequestMapping("/reg")
    public JSONResult<Void> reg(User user) {

        userService.registerUser(user); //不需要处理异常，异常都被拦截到BaseController中进行统一处理
        return new JSONResult<>(OK);
    }

    /**
     * 登录方法
     * 接收数据方式2：请求处理方法的参数列表设置为非pojo类型来接收前端的数据
     * SpringBoot会将前端的url地址中的参数名和方法的参数名进行比较，如果这两个名称相同，则自动完成值的依赖注入
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public JSONResult<User> login(String username, String password, HttpSession session) {
        User loginUser = userService.login(username, password);

        // 向session对象中完成数据的绑定
        session.setAttribute("uid", loginUser.getUid());
        session.setAttribute("username", username);

        // 获取session中绑定的数据
        // System.out.println(getUidFromSession(session));
        // System.out.println(getUsernameFromSession(session));

        return new JSONResult<>(OK, loginUser);
    }


    /**
     * 修改密码
     * @param oldPwd
     * @param newPwd
     * @param session
     * @return
     */
    @PostMapping("/change_password")
    //@RequestParam("oldPassword") 获取请求中参数名为oldPassword的值，赋给oldPwd
    public JSONResult<Void> changePassword(@RequestParam("oldPassword") String oldPwd,
                                           @RequestParam("newPassword") String newPwd,
                                           HttpSession session) {
        String username = getUsernameFromSession(session);
        Integer uid = getUidFromSession(session);
        userService.changePassword(username, uid, oldPwd, newPwd);
        return new JSONResult<Void>(OK);
    }

    /**
     * 通过用户 id查用户信息
     * @param session
     * @return
     */
    @RequestMapping("/get_by_uid")
    public JSONResult<User> getUserByUid(HttpSession session) {
        Integer uid = getUidFromSession(session);
        User user = userService.getUserByUid(uid);
        return new JSONResult<User>(OK, user);
    }

    /**
     * 修改用户信息
     * @param user
     * @param session
     * @return
     */
    @RequestMapping("/change_info")
    public JSONResult<Void> changeInfo(User user, HttpSession session) {
        // user对象有四部分数据：username、phone、email、gender
        // uid需要再次封装到user对象中
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changeUserInfo(username, uid, user);
        return new JSONResult<>(OK);
    }


    /**
     * 上传头像的路径，首先需要规划可能产生的异常(见controller.ex)
     * 在配置文件中设置上传文件的最大值，这里UserController 中使用final设置的AVATAR_MAX_SIZE是在不超出配置文件中大小的前提下，才能生效
     * 如，配置文件中上传文件的单个大小默认为1M，即使在UserController中设置为10M，也会出现异常
     * 在UserController中设置可上传的文件类型(不可变集合表示)
     * @param session
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @RequestMapping("/upload_avatar")
    // upload.html 中是使用name="file"标识上传的文件,所以使用注解@RequestParam("file")标识
    public JSONResult<String> uploadAvatar(HttpSession session,
                                           @RequestParam("file") MultipartFile multipartFile) {
        // 判断文件是否为空
        if (multipartFile.isEmpty()) {
            throw new FileEmptyException("文件为空！");
        }

        //判断文件类型是否是规定的后缀类型
        if (!AVATAR_TYPE.contains(multipartFile.getContentType())) {
            throw new FileTypeException("文件类型不匹配！");
        }

        // 判断文件大小是否正常
        if (multipartFile.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("文件大小超出限制！");
        }


        // 获取upload目录的绝对路径
        String uploadPath = session.getServletContext().getRealPath("upload");
        // 将路径字符串包装为一个File对象，用户判断是否存在，不存在则创建
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }


        // 测试数据
        //System.out.println(session.getServletContext()); // org.apache.catalina.core.ApplicationContextFacade@6f455d72
        System.out.println(session.getServletContext().getRealPath("upload")); // C:/Users/枫叶/AppData/Local/Temp/tomcat-docbase.8080.8388240518364662720/upload
        System.out.println(multipartFile.getContentType()); //如果上传的是jpg格式的图片 image/jpeg


        //uuid生成图片名称
        String originalFilename = multipartFile.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        String filename = uuid.concat(suffix);


        File dest = new File(uploadDir, filename); //在upload目录下创建一个名为filename的空文件
        // 将multipartFile中的数据写入到dest中，前提是：两个文件后缀应相同
        try {
            multipartFile.transferTo(dest);
        } catch (FileStateException e) {
            throw new FileStateException("文件状态异常！");
        } catch (IOException e) {
            throw new FileUploadIOException("文件读写异常！");
        }


        //调用业务层修改头像的操作
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        String avatar = "/upload/" +  filename; //只需要保存到项目文件夹下
        userService.changeAvatar(avatar, username, uid);

        // 返回用户头像的路径给前端，将来用作头像展示使用
        return new JSONResult<String>(OK, avatar);
    }
}
