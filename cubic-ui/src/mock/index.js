/**
 * Created by lk on 18/4/28.
 */

import Mock from "mockjs";

import login from "./login";
import authAdmin from "./authAdmin";
import authPermissionRule from "./authPermissionRule";
import authRole from "./authRole";
import upload from "./upload";
import fileResourceTag from "./fileResourceTag";
import fileResource from "./fileResource";
import adSite from "./adSite";
import ad from "./ad";

// 登录相关
Mock.mock(/\/admin\/auth\/login\/out/, "post", login.out);
Mock.mock(/\/admin\/auth\/login\/password/, "post", login.password);
Mock.mock(/\/admin\/auth\/login\/index/, "post", login.index);
Mock.mock(/\/admin\/auth\/login\/userInfo/, "get", login.userInfo);
// 管理员相关
Mock.mock(/\/admin\/auth\/admin\/index/, "get", authAdmin.index);
Mock.mock(/\/admin\/auth\/admin\/roleList/, "get", authAdmin.roleList);
Mock.mock(/\/admin\/auth\/admin\/save/, "post", authAdmin.save);
Mock.mock(/\/admin\/auth\/admin\/edit/, "post", authAdmin.edit);
Mock.mock(/\/admin\/auth\/admin\/delete/, "post", authAdmin.del);
// 权限相关
Mock.mock(
    /\/admin\/auth\/permission_rule\/index/,
    "get",
    authPermissionRule.index
);
Mock.mock(
    /\/admin\/auth\/permission_rule\/save/,
    "post",
    authPermissionRule.save
);
Mock.mock(
    /\/admin\/auth\/permission_rule\/edit/,
    "post",
    authPermissionRule.edit
);
Mock.mock(
    /\/admin\/auth\/permission_rule\/delete/,
    "post",
    authPermissionRule.del
);
// 角色相关
Mock.mock(/\/admin\/auth\/role\/index/, "get", authRole.index);
Mock.mock(/\/admin\/auth\/role\/save/, "post", authRole.save);
Mock.mock(/\/admin\/auth\/role\/edit/, "post", authRole.edit);
Mock.mock(/\/admin\/auth\/role\/delete/, "post", authRole.del);
Mock.mock(/\/admin\/auth\/role\/authList/, "get", authRole.authList);
Mock.mock(/\/admin\/auth\/role\/auth/, "post", authRole.auth);

/**
 * 上传相关
 */
// 获取文件列表
Mock.mock(/admin\/file\/upload\/qiuNiuUpToken/, "get", upload.qiuNiuUpToken);
// 上传文件
Mock.mock(/admin\/file\/upload\/createFile/, "post", upload.createFile);

/**
 * 资源分组相关
 */
// 获取资源分组列表
Mock.mock(/admin\/file\/resource_tag\/index/, "get", fileResourceTag.index);
// 新建资源分组
Mock.mock(/admin\/file\/resource_tag\/add/, "post", fileResourceTag.add);
/**
 * 资源相关
 */
// 获取资源列表
Mock.mock(/admin\/file\/resource\/index/, "get", fileResource.index);
// 上传资源
Mock.mock(/admin\/file\/resource\/add/, "post", fileResource.add);

// 广告位相关
Mock.mock(/\/admin\/ad\/site\/index/, "get", adSite.index);
Mock.mock(/\/admin\/ad\/site\/adList/, "post", adSite.adList);
Mock.mock(/\/admin\/ad\/site\/save/, "post", adSite.save);
Mock.mock(/\/admin\/ad\/site\/edit/, "post", adSite.edit);
Mock.mock(/\/admin\/ad\/site\/delete/, "post", adSite.del);
// 广告相关
Mock.mock(/\/admin\/ad\/ad\/index/, "get", ad.index);
Mock.mock(/\/admin\/ad\/ad\/save/, "post", ad.save);
Mock.mock(/\/admin\/ad\/ad\/edit/, "post", ad.edit);
Mock.mock(/\/admin\/ad\/ad\/delete/, "post", ad.del);

export default Mock;
