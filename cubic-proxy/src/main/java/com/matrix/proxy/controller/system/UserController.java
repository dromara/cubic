package com.matrix.proxy.controller.system;

import com.cubic.proxy.common.module.DataResult;
import com.matrix.proxy.module.CubicUserDto;
import com.matrix.proxy.service.system.UserService;
import com.matrix.proxy.vo.CubicUserVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author luqiang
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 列表
     *
     * @param query
     */
    @GetMapping("/list")
    public DataResult list(CubicUserVo query) {
        return userService.list(query);
    }

    /**
     * 创建用户
     *
     * @param userDto
     */
    @PostMapping("/create")
    public void create(@RequestBody CubicUserDto userDto) {

        userService.create(userDto);
    }

    /**
     * 更新用户
     *
     * @param userDto
     */
    @PostMapping("/update")
    public void update(@RequestBody CubicUserDto userDto) {

        userService.update(userDto);
    }

    /**
     * 查看用户
     *
     * @param id
     */
    @GetMapping("/view")
    public CubicUserDto view(@RequestParam Integer id) {

        return userService.view(id);
    }

    /**
     * 删除用户
     *
     * @param userDto
     */
    @PostMapping("/delete")
    public void delete(@RequestBody CubicUserDto userDto) {

        userService.delete(userDto);
    }

    /**
     * 用户状态修改
     *
     * @param userDto
     */
    @PostMapping("/updateStatus")
    public void updateStatus(CubicUserDto userDto) {

        userService.updateStatus(userDto);
    }

    /**
     * 用户信息
     *
     * @param
     */
    @RequestMapping("/info")
    public Map<String, Object> info() {

        Map<String, Object> info = new HashMap<>();
        info.put("roles", new String[]{"admin"});
        info.put("introduction", " am a super administrator");
        info.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        info.put("name", "Super Admin");

        return info;
    }

}
