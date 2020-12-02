-- ----------------------------
--  Table structure for `basic_information`
-- ----------------------------
DROP TABLE IF EXISTS `basic_information`;
CREATE TABLE `basic_information`
(
    `id`             int(11)                           NOT NULL AUTO_INCREMENT COMMENT '唯一主键',
    `app_id`         varchar(100) CHARACTER SET latin1 NOT NULL COMMENT '应用标识',
    `instance_name`  varchar(150) CHARACTER SET latin1 DEFAULT NULL COMMENT '实例名称',
    `instance_id`    varchar(100) CHARACTER SET latin1 DEFAULT NULL COMMENT '应用名称',
    `ip`             varchar(30) CHARACTER SET latin1  DEFAULT NULL COMMENT '地址IP',
    `progress`       varchar(10) CHARACTER SET latin1  DEFAULT NULL COMMENT '进程号',
    `host`           varchar(30) CHARACTER SET latin1  DEFAULT NULL COMMENT 'host',
    `os`             varchar(10) CHARACTER SET latin1  DEFAULT NULL COMMENT 'os',
    `language`       varchar(10) CHARACTER SET latin1  DEFAULT NULL COMMENT '语言',
    `version`        varchar(5) CHARACTER SET latin1   DEFAULT NULL COMMENT '状态',
    `last_heartbeat` datetime                          DEFAULT NULL COMMENT '最后心跳',
    `start_date`     datetime                          DEFAULT NULL COMMENT '启动时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4;


-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `username`    varchar(50)  NOT NULL COMMENT '用户名',
    `secret`      varchar(255) NOT NULL COMMENT '秘钥',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4;

