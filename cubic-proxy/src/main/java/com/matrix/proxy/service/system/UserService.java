package com.matrix.proxy.service.system;

import com.matrix.proxy.module.CubicUserDto;
import com.matrix.proxy.vo.CubicUserVo;

import java.util.List;

/**
 * @ClassName UserService
 * @Author QIANGLU
 * @Date 2022/8/19 19:47
 * @Version 1.0
 */
public interface UserService {

    /**
     * 创建用户
     *
     * @param userVo
     */
    List<CubicUserDto> list(CubicUserVo userVo);

    /**
     * 创建用户
     *
     * @param userDto
     */
    void create(CubicUserDto userDto);

    /**
     * 更新用户
     *
     * @param userDto
     */
    void update(CubicUserDto userDto);

    /**
     * 查看用户
     *
     * @param id
     */
    CubicUserDto view(Integer id);

    /**
     * 删除用户
     *
     * @param userDto
     */
    void delete(CubicUserDto userDto);

    /**
     * 用户状态修改
     *
     * @param userDto
     */
    void updateStatus(CubicUserDto userDto);

}
