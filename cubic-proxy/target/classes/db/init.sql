-- ----------------------------
--  Table structure for `information`
-- ----------------------------
DROP TABLE IF EXISTS `information`;
CREATE TABLE `information`
(
    `id`             int(11)                           NOT NULL AUTO_INCREMENT COMMENT '唯一主键',
    `app_id`         varchar(100) CHARACTER SET latin1 NOT NULL COMMENT '应用标识',
    `instance_name`  varchar(150) CHARACTER SET latin1 DEFAULT NULL COMMENT '实例名称',
    `instance_id`    varchar(100) CHARACTER SET latin1 DEFAULT NULL COMMENT '应用名称',
    `ip`             varchar(30) CHARACTER SET latin1  DEFAULT NULL COMMENT '地址IP',
    `progress`       varchar(10) CHARACTER SET latin1  DEFAULT NULL COMMENT '进程号',
    `host`           varchar(30) CHARACTER SET latin1  DEFAULT NULL COMMENT 'host',
    `os`             varchar(10) CHARACTER SET latin1  DEFAULT NULL COMMENT 'os',
    `os_arch`        varchar(20)                       DEFAULT NULL COMMENT '系统架构',
    `os_version`     varchar(128)                      DEFAULT NULL COMMENT '系统版本',
    `language`       varchar(10) CHARACTER SET latin1  DEFAULT NULL COMMENT '语言',
    `version`        varchar(5) CHARACTER SET latin1   DEFAULT NULL COMMENT '状态',
    `last_heartbeat` datetime                          DEFAULT NULL COMMENT '最后心跳',
    `start_date`     datetime                          DEFAULT NULL COMMENT '启动时间',
    `jdk_version`    varchar(20)                       DEFAULT NULL COMMENT 'JDK版本',
    `jdk_dir`        varchar(200)                      DEFAULT NULL COMMENT 'jdk路径',
    `user_dir`       varchar(200)                      DEFAULT NULL COMMENT '用户路径',
    `init_memory`    int(11)                           DEFAULT NULL COMMENT '初始内存',
    `max_memory`     int(11)                           DEFAULT NULL COMMENT '最大内存',
    `processor_num`  int(11)                           DEFAULT NULL COMMENT '内核数',
    `arguments`      text COMMENT '启动参数',
    `jars`           text COMMENT '依赖jar',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_app_id` (`app_id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `username`    varchar(50)  NOT NULL COMMENT '用户名',
    `secret`      varchar(255) NOT NULL COMMENT '秘钥',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user`
VALUES (1, 'matrix', '$2a$10$oRzuT/fvUlO6Eh7RtUwNiuEm7vJcymtgC53AnkS/km9E8GUBjMZ8S', '2020-11-25 12:08:51');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;


-- ----------------------------
-- 依赖信息表
-- ----------------------------
DROP TABLE IF EXISTS `rely_information`;
CREATE TABLE `rely_information`
(
    `id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '应用名称',
    `app_id`       varchar(64) COLLATE utf8mb4_unicode_ci  DEFAULT NULL COMMENT '实例唯一id',
    `jar_name`     varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'jar包名称',
    `service_name` varchar(50) COLLATE utf8mb4_unicode_ci  DEFAULT NULL COMMENT '应用名称',
    `create_date`  datetime                                DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `uuid` (`app_id`) USING BTREE COMMENT 'uuid'
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- ----------------------------
-- app线程栈信息表
-- ----------------------------
DROP TABLE IF EXISTS `thread_dump`;
CREATE TABLE `thread_dump` (
       `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '唯一主键',
       `app_id` varchar(100) NOT NULL COMMENT '应用标识',
       `instance_name` varchar(150) DEFAULT NULL COMMENT '实例名称',
       `instance_id` varchar(100) DEFAULT NULL COMMENT '应用名称',
       `thread_dump` longtext COMMENT '线程栈信息',
       `create_time` datetime DEFAULT NULL COMMENT '采集时间',
       PRIMARY KEY (`id`),
       KEY `idx_instance_name_date` (`instance_name`,`create_time`),
       KEY `idx_app_id` (`app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
