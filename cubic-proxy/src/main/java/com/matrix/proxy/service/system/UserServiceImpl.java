package com.matrix.proxy.service.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.matrix.proxy.mapper.CubicUserMapper;
import com.matrix.proxy.module.CubicUser;
import com.matrix.proxy.module.CubicUserDto;
import com.matrix.proxy.vo.CubicUserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName UserServiceImpl
 * @Author QIANGLU
 * @Date 2022/8/19 19:47
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private CubicUserMapper cubicUserMapper;

    /**
     * 列表
     *
     * @param query
     */
    @Override
    public List<CubicUserDto> list(CubicUserVo query) {

        Page<CubicUser> page = new Page<>(query.getPageNum(),query.getPageSize());

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.orderByDesc("create_time");
        if(StringUtils.isNoneBlank(query.getUsername())){
            wrapper.eq("username",query.getUsername());
        }

        IPage<CubicUser> datas = cubicUserMapper.selectPage(page,wrapper);

        if(CollectionUtils.isEmpty(datas.getRecords())){
            return new ArrayList<>();
        }
       List<CubicUserDto> result = new ArrayList<>();
        BeanCopier copier =BeanCopier.create(CubicUser.class,CubicUserDto.class,false);
        datas.getRecords().forEach(user ->{
            CubicUserDto dto = new CubicUserDto();
            copier.copy(user,dto,null);
            result.add(dto);
        });

        return result;
    }

    /**
     * 创建用户
     *
     * @param userDto
     */
    @Override
    public void create(CubicUserDto userDto) {

        CubicUser user = new CubicUser();
        BeanUtils.copyProperties(userDto, user);
        cubicUserMapper.insert(user);
    }

    /**
     * 更新用户
     *
     * @param userDto
     */
    @Override
    public void update(CubicUserDto userDto) {

        CubicUser user = new CubicUser();
        BeanUtils.copyProperties(userDto, user);
        cubicUserMapper.insert(user);
    }

    /**
     * 查看用户
     *
     * @param id
     */
    @Override
    public CubicUserDto view(Integer id) {

        CubicUser user = cubicUserMapper.selectById(id);

        CubicUserDto userDto = new CubicUserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

    /**
     * 删除用户
     *
     * @param userDto
     */
    @Override
    public void delete(CubicUserDto userDto) {

        cubicUserMapper.deleteById(userDto.getId());
    }

    /**
     * 用户状态修改
     *
     * @param userDto
     */
    @Override
    public void updateStatus(CubicUserDto userDto) {

        CubicUser user = new CubicUser();
        user.setId(userDto.getId());
        user.setStatus(userDto.getStatus());
        cubicUserMapper.updateById(user);
    }

}
