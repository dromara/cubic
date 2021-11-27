/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : localhost:3306
 Source Schema         : cubic

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 01/06/2021 23:16:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cubic_information
-- ----------------------------
DROP TABLE IF EXISTS `cubic_information`;
CREATE TABLE `cubic_information`
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
) ENGINE = InnoDB
  AUTO_INCREMENT = 32
  DEFAULT CHARSET = utf8mb4 COMMENT ='实例注册信息表';

-- ----------------------------
-- Table structure for cubic_rely_information
-- ----------------------------
DROP TABLE IF EXISTS `cubic_rely_information`;
CREATE TABLE `cubic_rely_information`
(
    `id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '应用名称',
    `app_id`       varchar(64) COLLATE utf8mb4_unicode_ci  DEFAULT NULL COMMENT '实例唯一id',
    `jar_name`     varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'jar包名称',
    `service_name` varchar(50) COLLATE utf8mb4_unicode_ci  DEFAULT NULL COMMENT '应用名称',
    `create_date`  datetime                                DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `uuid` (`app_id`) USING BTREE COMMENT 'uuid'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='实例依赖信息表';

-- ----------------------------
-- Table structure for cubic_thread_dump
-- ----------------------------
DROP TABLE IF EXISTS `cubic_thread_dump`;
CREATE TABLE `cubic_thread_dump`
(
    `id`            bigint(19)   NOT NULL AUTO_INCREMENT COMMENT '唯一主键',
    `app_id`        varchar(100) NOT NULL COMMENT '应用标识',
    `instance_name` varchar(150) DEFAULT NULL COMMENT '实例名称',
    `instance_id`   varchar(100) DEFAULT NULL COMMENT '应用名称',
    `thread_dump`   longtext COMMENT '线程栈信息',
    `create_time`   datetime     DEFAULT NULL COMMENT '采集时间',
    PRIMARY KEY (`id`),
    KEY `idx_instance_name_date` (`instance_name`, `create_time`),
    KEY `idx_app_id` (`app_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 54
  DEFAULT CHARSET = utf8mb4 COMMENT ='线程栈数据表';

-- ----------------------------
-- Table structure for cubic_thread_pool
-- ----------------------------
DROP TABLE IF EXISTS `cubic_thread_pool`;
CREATE TABLE `cubic_thread_pool`
(
    `id`                 bigint(19)                                                    NOT NULL AUTO_INCREMENT COMMENT '唯一主键',
    `instance_id`        varchar(100) DEFAULT NULL COMMENT '应用名称',
    `instance_name`      varchar(150) DEFAULT NULL COMMENT '实例名称',
    `thread_pool_key`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '线程池key',
    `thread_pool_params` longtext COMMENT '线程池参数',
    `create_time`        datetime     DEFAULT NULL COMMENT '采集时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4057
  DEFAULT CHARSET = utf8mb4 COMMENT ='线程池数据表';

-- ----------------------------
-- Table structure for cubic_user
-- ----------------------------
DROP TABLE IF EXISTS `cubic_user`;
CREATE TABLE `cubic_user`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `username`    varchar(50)  NOT NULL COMMENT '用户名',
    `secret`      varchar(255) NOT NULL COMMENT '秘钥',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户权限表';

SET FOREIGN_KEY_CHECKS = 1;


INSERT INTO `user` (`id`, `username`, `secret`, `create_time`)
VALUES
(1, 'matrix', '$2a$10$oRzuT/fvUlO6Eh7RtUwNiuEm7vJcymtgC53AnkS/km9E8GUBjMZ8S', '2020-11-25 12:08:51');
