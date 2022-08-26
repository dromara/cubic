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
package com.matrix.proxy.module;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 实体类
 *
 * @author luqiang
 */
@Data
@TableName("cubic_user")
public class CubicUser {

    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 用户名
     */
    private String username;

    /**
     * 秘钥
     */
    private String secret;

    /**
     * 是否管理员 0 不是 1是
     */
    @TableField("is_admin")
    private Integer isAdmin;

    /**
     * 状态 1 正常 0禁用
     */
    @TableField("status")
    private Integer status;


    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;


}
