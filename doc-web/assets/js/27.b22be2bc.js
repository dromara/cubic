(window.webpackJsonp=window.webpackJsonp||[]).push([[27],{355:function(e,r,a){"use strict";a.r(r);var t=a(7),l=Object(t.a)({},(function(){var e=this,r=e._self._c;return r("ContentSlotsDistributor",{attrs:{"slot-key":e.$parent.slotKey}},[r("h2",{attrs:{id:"v2-0-3"}},[r("a",{staticClass:"header-anchor",attrs:{href:"#v2-0-3"}},[e._v("#")]),e._v(" v2.0.3")]),e._v(" "),r("blockquote",[r("ol",[r("li",[e._v("修复在动态mapper中DataBase.getOne()方法拿不到动态mapper的异常问题")]),e._v(" "),r("li",[e._v("适配JDK17异常抛出，便于用户自定义配置Jvm参数")])])]),e._v(" "),r("h2",{attrs:{id:"v2-0-2"}},[r("a",{staticClass:"header-anchor",attrs:{href:"#v2-0-2"}},[e._v("#")]),e._v(" v2.0.2")]),e._v(" "),r("blockquote",[r("ol",[r("li",[e._v("升级mybatis-plus到3.5.4")]),e._v(" "),r("li",[e._v("适配JDK17")])])]),e._v(" "),r("h2",{attrs:{id:"v2-0-0-alpha-002"}},[r("a",{staticClass:"header-anchor",attrs:{href:"#v2-0-0-alpha-002"}},[e._v("#")]),e._v(" v2.0.0-alpha+002")]),e._v(" "),r("blockquote",[r("ol",[r("li",[e._v("BeanHelper.copyProperties支持lombok的@Accessors(chain = true)")]),e._v(" "),r("li",[e._v("调整SerPred#isEqual方法参数为单个")]),e._v(" "),r("li",[e._v("解决SimpleName一样的实体类导致只存在一个Mapper的问题，感谢@huang-up")])])]),e._v(" "),r("h2",{attrs:{id:"v2-0-0-alpha-001"}},[r("a",{staticClass:"header-anchor",attrs:{href:"#v2-0-0-alpha-001"}},[e._v("#")]),e._v(" v2.0.0-alpha+001")]),e._v(" "),r("blockquote",[r("ol",[r("li",[e._v("添加safe-mode模式，进行一定程度的sql注入拦截处理例如QueryCondition、SqTenantLineInnerInterceptor等——感谢 @王丛")]),e._v(" "),r("li",[e._v("进行代码规范优化——感谢@mingwang")]),e._v(" "),r("li",[e._v("添加BoolHelper.isTruthy/BoolHelper.isFalsy")]),e._v(" "),r("li",[e._v("修复SerArgsPred.isEqual的问题")]),e._v(" "),r("li",[e._v("升级mybatis-plus到3.5.3.2")])])]),e._v(" "),r("h2",{attrs:{id:"v1-9-1"}},[r("a",{staticClass:"header-anchor",attrs:{href:"#v1-9-1"}},[e._v("#")]),e._v(" v1.9.1")]),e._v(" "),r("blockquote",[r("p",[e._v("AbstractJsonFieldHandler的parse、toJson从protected改为了public ,兼容前置版本")])]),e._v(" "),r("h2",{attrs:{id:"v1-9-0"}},[r("a",{staticClass:"header-anchor",attrs:{href:"#v1-9-0"}},[e._v("#")]),e._v(" v1.9.0")]),e._v(" "),r("blockquote",[r("ol",[r("li",[e._v("废弃Database.lambdaQuery(Serializable,SFunction)、Database.lambdaQuery(Collection, SFunction)，移到WrapperHelper")]),e._v(" "),r("li",[e._v("修复StreamScannerRegistrar扫描包时basePackages默认值设置不合适导致的卡顿问题 感谢@kirito2020")]),e._v(" "),r("li",[e._v("基于QueryCondition对LambdaQueryWrapper进行typeHandler的一定量适配 感谢@Casonhqc")]),e._v(" "),r("li",[e._v("废弃ReflectHelper.explain 感谢@zslhome")]),e._v(" "),r("li",[e._v("TableName支持自定义注解，见"),r("a",{attrs:{href:"https://gitee.com/dromara/stream-query/issues/I7BSNV",target:"_blank",rel:"noopener noreferrer"}},[e._v("I7BSNV"),r("OutboundLink")],1),e._v(" 感谢@weaseal")]),e._v(" "),r("li",[e._v("新增Stream.of(students).nonNull(Student::getName)方法 感谢@fibonacciXue")]),e._v(" "),r("li",[e._v("添加LambdaHelper.getGetterSetterMap和LambdaHelper.getPropertyGetterSetterMap")]),e._v(" "),r("li",[e._v("添加BeanHelper.copyProperties(source, target) 感谢 @Eliauk")])])]),e._v(" "),r("blockquote",[r("p",[e._v("注意！AbstractJsonFieldHandler的parse、toJson从protected改为了public，会尽快发布v1.9.1修订")])]),e._v(" "),r("p",[r("strong",[e._v("完整的更新日志")]),e._v(": https://gitee.com/dromara/stream-query/commits/v1.9.0")]),e._v(" "),r("h2",{attrs:{id:"v1-8-0"}},[r("a",{staticClass:"header-anchor",attrs:{href:"#v1-8-0"}},[e._v("#")]),e._v(" v1.8.0")]),e._v(" "),r("blockquote",[r("ol",[r("li",[e._v("HighlightHelper修改，处理方式更改为使用区间合并，添加按给定单词做高亮处理的方法——感谢 @kirito @Eliauk")]),e._v(" "),r("li",[e._v("新增HighlightHelper.highlight(String text, UnaryOperator<> highlightOperator, Collection<> words)方法，用于查找并高亮——感谢 @kirito @Eliauk\n使用方式："),r("a",{attrs:{href:"https://gitee.com/dromara/stream-query/blob/main/stream-core/src/test/java/org/dromara/streamquery/stream/core/business/highlight/HighlightHelperTest.java",target:"_blank",rel:"noopener noreferrer"}},[e._v("HighlightHelperTest"),r("OutboundLink")],1)]),e._v(" "),r("li",[e._v("Maps新增isEmpty、isNotEmpty")]),e._v(" "),r("li",[e._v("新增SerCons.entryCons、SerFunc.entryFunc、SerPred.entryPred方法，对Map.Entry进行lambda操作时进行转换适配\n使用方式："),r("a",{attrs:{href:"https://gitee.com/dromara/stream-query/blob/main/stream-core/src/test/java/org/dromara/streamquery/stream/core/lambda/function/SerConsTest.java",target:"_blank",rel:"noopener noreferrer"}},[e._v("SerConsTest"),r("OutboundLink")],1),e._v(" "),r("a",{attrs:{href:"https://gitee.com/dromara/stream-query/blob/main/stream-core/src/test/java/org/dromara/streamquery/stream/core/lambda/function/SerFuncTest.java",target:"_blank",rel:"noopener noreferrer"}},[e._v("SerFuncTest"),r("OutboundLink")],1),e._v(" "),r("a",{attrs:{href:"https://gitee.com/dromara/stream-query/blob/main/stream-core/src/test/java/org/dromara/streamquery/stream/core/lambda/function/SerPredTest.java",target:"_blank",rel:"noopener noreferrer"}},[e._v("SerPredTest"),r("OutboundLink")],1)]),e._v(" "),r("li",[e._v("新增Steam.of(map)，代替Steam.of(map.entries)")]),e._v(" "),r("li",[e._v("优化SaveOneSql、UpdateOneSql构造方法")]),e._v(" "),r("li",[e._v("废弃Database.select，使用WrapperHelper.select代替")]),e._v(" "),r("li",[e._v("Database.updateFewSql支持typeHandler，见："),r("a",{attrs:{href:"https://gitee.com/dromara/stream-query/blob/main/stream-plugin/stream-plugin-mybatis-plus/src/test/java/org/dromara/streamquery/stream/plugin/mybatisplus/JsonFieldHandlerTest.java",target:"_blank",rel:"noopener noreferrer"}},[e._v("JsonFieldHandlerTest"),r("OutboundLink")],1)])])]),e._v(" "),r("h2",{attrs:{id:"v1-7-0-alpha"}},[r("a",{staticClass:"header-anchor",attrs:{href:"#v1-7-0-alpha"}},[e._v("#")]),e._v(" v1.7.0-alpha")]),e._v(" "),r("blockquote",[r("ol",[r("li",[e._v("TreeHelper支持获取层级节点")]),e._v(" "),r("li",[e._v("TreeHelper可以不用强制传入childrenSetter，废弃旧版of api")]),e._v(" "),r("li",[e._v("添加LambdaHelper.getGetter、LambdaHelper.getSetter")]),e._v(" "),r("li",[e._v("添加Maps.computeIfAbsent，解决"),r("a",{attrs:{href:"https://bugs.openjdk.org/browse/JDK-8161372",target:"_blank",rel:"noopener noreferrer"}},[e._v("jdk8下"),r("code",[e._v("ConcurrentHashMap#computeIfAbsent")]),e._v("死循环问题"),r("OutboundLink")],1),e._v("，并替换现有的computeIfAbsent为该函数")]),e._v(" "),r("li",[e._v("Maps新增一些函数：\nmerge -> 合并两个Map得到一个新的Map，如果key相同，使用mergeFunction处理value\nfilter -> 传入策略，过滤map\nflatten -> 将具有多个级别的嵌套Map平展为单级Map使用指定分隔符从原始键值连接")]),e._v(" "),r("li",[e._v("添加JreEnum，可以用于判断一些java版本")]),e._v(" "),r("li",[e._v("抽取stream-dependencies模块用于管理依赖")]),e._v(" "),r("li",[e._v("处理了动态mapper扫描实体逻辑bug")])])]),e._v(" "),r("h2",{attrs:{id:"v1-6-0-alpha"}},[r("a",{staticClass:"header-anchor",attrs:{href:"#v1-6-0-alpha"}},[e._v("#")]),e._v(" v1.6.0-alpha")]),e._v(" "),r("h3",{attrs:{id:"本次更新新增的方法"}},[r("a",{staticClass:"header-anchor",attrs:{href:"#本次更新新增的方法"}},[e._v("#")]),e._v(" 本次更新新增的方法")]),e._v(" "),r("blockquote",[r("ol",[r("li",[e._v("TreeHelper.toTree(list)")]),e._v(" "),r("li",[e._v("WrapperHelper.multi(wrapper,dataList,biConsumer)\n向下兼容并修改了入参的方法")]),e._v(" "),r("li",[e._v("Maps.oneToManyToOne函数入参从List拓展为Collection")])])]),e._v(" "),r("h3",{attrs:{id:"废弃的方法"}},[r("a",{staticClass:"header-anchor",attrs:{href:"#废弃的方法"}},[e._v("#")]),e._v(" 废弃的方法")]),e._v(" "),r("blockquote",[r("ol",[r("li",[e._v("废弃了WrapperHelper.multiOr，该方法也许将在(v2.0)移除，取而代之使用WrapperHelper.multi代替，这是因为需要考虑到灵活控制"),r("code",[e._v("or")]),e._v("条件是否拼接\n该版本有TreeHelper的兼容性问题，建议使用了TreeHelper的用户不要使用该版本，会尽快发布新版本向下兼容")])])]),e._v(" "),r("h2",{attrs:{id:"v1-5-0-alpha"}},[r("a",{staticClass:"header-anchor",attrs:{href:"#v1-5-0-alpha"}},[e._v("#")]),e._v(" v1.5.0-alpha")]),e._v(" "),r("h3",{attrs:{id:"特性"}},[r("a",{staticClass:"header-anchor",attrs:{href:"#特性"}},[e._v("#")]),e._v(" 特性：")]),e._v(" "),r("blockquote",[r("ol",[r("li",[e._v("支持了几种配置动态mapper的方式如配置文件、注解硬编码等"),r("a",{attrs:{href:"https://gitee.com/dromara/stream-query/pulls/304",target:"_blank",rel:"noopener noreferrer"}},[e._v("pulls/304"),r("OutboundLink")],1),e._v(" "),r("a",{attrs:{href:"https://gitee.com/dromara/stream-query/pulls/305",target:"_blank",rel:"noopener noreferrer"}},[e._v("pulls/305"),r("OutboundLink")],1)]),e._v(" "),r("li",[e._v("新增JsonFieldHandler，封装typeHandler，使其在序列化/反序列化时可以拿到字段信息")]),e._v(" "),r("li",[e._v("新增了几个Sets/Lists/Maps等类的函数")]),e._v(" "),r("li",[e._v("拆分了WrapperHelper")])])])])}),[],!1,null,null,null);r.default=l.exports}}]);