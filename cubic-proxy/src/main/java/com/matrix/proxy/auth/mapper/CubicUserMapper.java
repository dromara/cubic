/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.matrix.proxy.auth.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.matrix.proxy.auth.entity.CubicUser;

/**
 * Mapper 接口
 *
 * @author Chill
 */
public interface CubicUserMapper extends BaseMapper<CubicUser> {

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    CubicUser selectByUsername(String username);
}
